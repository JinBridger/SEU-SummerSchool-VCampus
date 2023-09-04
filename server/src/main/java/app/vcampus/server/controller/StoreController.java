package app.vcampus.server.controller;

import app.vcampus.server.entity.StoreItem;
import app.vcampus.server.entity.StoreTransaction;
import app.vcampus.server.entity.User;
import app.vcampus.server.utility.*;
import app.vcampus.server.utility.router.RouteMapping;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Store;
import org.hibernate.Transaction;

import javax.management.Query;
import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

@Slf4j

public class StoreController {
    @RouteMapping(uri = "storeItem/searchItem",role="admin")
    public Response searchItem(Request request, org.hibernate.Session database) {
        String itemName = request.getParams().get("itemName");
        if (itemName == null) {
            return Response.Common.error("item name cannot be empty");
        }
        StoreItem storeItem = database.get(StoreItem.class, itemName);
        if (storeItem == null) {
            return Response.Common.error("no such item name");
        }
        System.out.println(storeItem);
        return Response.Common.ok(storeItem.toMap());
    }

    @RouteMapping(uri = "storeItem/addItem",role="admin")
    public Response addItem(Request request, org.hibernate.Session database) {
        StoreItem newStoreItem = StoreItem.fromMap(request.getParams());
        if (newStoreItem == null) {
            return Response.Common.badRequest();
        }
        StoreItem storeItem = database.get(StoreItem.class, newStoreItem.getItemName());
        if (storeItem == null) {
            return Response.Common.error("Store item not found");
        }
        Transaction tx = database.beginTransaction();
        database.persist(newStoreItem);
        tx.commit();
        return Response.Common.ok();
    }

    @RouteMapping(uri = "storeItem/deleteItem",role="admin")
    public Response deleteItem(Request request, org.hibernate.Session database) {
        String itemName = request.getParams().get("itemName");
        if (itemName == null) {
            return Response.Common.error("item name cannot be empty");
        }
        StoreItem storeItem = database.get(StoreItem.class, itemName);
        if (storeItem == null) {
            return Response.Common.error("no such uuid");
        }
        Transaction tx = database.beginTransaction();
        database.remove(storeItem);
        tx.commit();

        return Response.Common.ok();
    }

    @RouteMapping(uri = "storeItem/updateItem",role="admin")
    public Response updateItem(Request request, org.hibernate.Session database) {
        StoreItem newStoreItem = StoreItem.fromMap(request.getParams());
        if (newStoreItem == null) {
            return Response.Common.badRequest();
        }
        StoreItem storeItem = database.get(StoreItem.class, newStoreItem.getItemName());
        if (storeItem == null) {
            return Response.Common.error("Incorrect item name");
        }

        Transaction tx = database.beginTransaction();
        storeItem.setItemName(newStoreItem.getItemName());
        storeItem.setPrice(newStoreItem.getPrice());
        storeItem.setPictureLink(newStoreItem.getPictureLink());
        storeItem.setBarcode(newStoreItem.getBarcode());
        storeItem.setStock(newStoreItem.getStock());
        storeItem.setDescription(newStoreItem.getDescription());
        database.persist(storeItem);
        tx.commit();
        return Response.Common.ok();
    }

    @RouteMapping(uri="storeTransaction/selectItem")
    public Response selectItem(Request request,org.hibernate.Session database){
        StoreItem selectStoreItem= StoreItem.fromMap(request.getParams());
        if(selectStoreItem==null){
            return Response.Common.badRequest();
        }

        Transaction tx=database.beginTransaction();
        Integer nowStock=selectStoreItem.getStock();
        Integer salesAmount=Integer.parseInt(request.getParams().get("amount"));
        if(salesAmount>nowStock){
            return Response.Common.error("Sale amount must not exceed stock");
        }
        selectStoreItem.setStock(nowStock-salesAmount);
        StoreTransaction newStoreTransaction=new StoreTransaction();
        newStoreTransaction.setUuid(UUID.randomUUID());
        newStoreTransaction.setItemUUID(selectStoreItem.getUuid());
        newStoreTransaction.setAmount(salesAmount);
        newStoreTransaction.setItemPrice(selectStoreItem.getPrice());
        newStoreTransaction.setCardNumber(Integer.parseInt(request.getParams().get("cardNumber")));
        newStoreTransaction.setTime(LocalDateTime.now());
        newStoreTransaction.setRemark(request.getParams().get("remark"));
        database.persist(newStoreTransaction);
        tx.commit();
        return Response.Common.ok();
    }

    @RouteMapping(uri="storeTransaction/pay")
    public Response pay(Request request,org.hibernate.Session database){
        try{
            List<StoreTransaction> allItems= Database.loadAllData(StoreTransaction.class,database);
            Integer totalPrice=0;
            for(StoreTransaction storeTransaction:allItems){
                totalPrice+=storeTransaction.getItemPrice()*storeTransaction.getAmount();
            }

            String cardNumber=request.getParams().get("cardNumber");
            String password=request.getParams().get("password");
            if(cardNumber==null||password==null){
                return Response.Common.badRequest();
            }
            User user=database.get(User.class,Integer.parseInt(cardNumber));
            if(user==null||!Password.verify(password,user.getPassword())){
                return Response.Common.error("Incorrect card number or password");
            }
            //扣钱
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.Common.ok();
    }

    /*@RouteMapping(uri="storeTransaction/getReport",role="admin")
    public Response getReport(Request request,org.hibernate.Session database){

        return Response.Common.ok();
    }*/
}
