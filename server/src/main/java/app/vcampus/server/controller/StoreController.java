package app.vcampus.server.controller;

import app.vcampus.server.entity.*;
import app.vcampus.server.enums.TransactionType;
import app.vcampus.server.utility.*;
import app.vcampus.server.utility.router.RouteMapping;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j

public class StoreController {
    @RouteMapping(uri = "store/buy", role = "shop_user")
    public Response buy(Request request, org.hibernate.Session database) {
        try {
            FinanceCard financeCard = database.get(FinanceCard.class, request.getSession().getCardNum());
            if (financeCard == null)
                return Response.Common.error("No such finance card");

            Type type = new TypeToken<List<Pair<UUID, Integer>>>() {
            }.getType();
            List<Pair<UUID, Integer>> items = new Gson().fromJson(request.getParams().get("items"), type);
            if (items == null)
                return Response.Common.error("Items cannot be empty");

            int totalPrice = 0;
            Transaction tx = database.beginTransaction();

            for (Pair<UUID, Integer> item : items) {
                StoreItem storeItem = database.get(StoreItem.class, item.getFirst());
                if (storeItem == null)
                    return Response.Common.error("No such item");
                totalPrice += storeItem.getPrice() * item.getSecond();
                if (storeItem.getStock() < item.getSecond())
                    return Response.Common.error("Stock cannot be less than amount");
                storeItem.setStock(storeItem.getStock() - item.getSecond());
                storeItem.setSalesVolume(storeItem.getSalesVolume() + item.getSecond());
                database.persist(storeItem);

                StoreTransaction storeTransaction = new StoreTransaction();
                storeTransaction.setUuid(UUID.randomUUID());
                storeTransaction.setItemUUID(storeItem.getUuid());
                storeTransaction.setItemPrice(storeItem.getPrice());
                storeTransaction.setAmount(item.getSecond());
                storeTransaction.setCardNumber(financeCard.getCardNumber());
                storeTransaction.setTime(new Date());
                storeTransaction.setRemark("");
                database.persist(storeTransaction);
            }
            if (financeCard.getBalance() < totalPrice)
                return Response.Common.error("Balance is not enough");
            financeCard.setBalance(financeCard.getBalance() - totalPrice);
            database.persist(financeCard);

            CardTransaction cardTransaction = new CardTransaction();
            cardTransaction.setUuid(UUID.randomUUID());
            cardTransaction.setCardNumber(financeCard.getCardNumber());
            cardTransaction.setAmount(totalPrice);
            cardTransaction.setTime(new Date());
            cardTransaction.setDescription("商店消费");
            cardTransaction.setType(TransactionType.payment);
            database.persist(cardTransaction);

            tx.commit();

            return Response.Common.ok();
        } catch (Exception e) {
            return Response.Common.error("Failed to buy item");
        }
    }

    @RouteMapping(uri = "store/user/getAllTransactions", role = "shop_user")
    public Response getAllTransactions(Request request, org.hibernate.Session database) {
        try {
            List<StoreTransaction> allTransactions = Database.loadAllData(StoreTransaction.class, database);
            return Response.Common.ok(allTransactions.stream().map(StoreTransaction::toJson).collect(Collectors.toList()));
        } catch (Exception e) {
            log.warn("Failed to get transaction records", e);
            return Response.Common.error("Failed to get transaction records");
        }
    }

    @RouteMapping(uri = "store/staff/getTodaySales", role = "shop_staff")
    public Response getTodaySales(Request request, org.hibernate.Session database) {
        try {
            List<StoreTransaction> allTransactions = Database.loadAllData(StoreTransaction.class, database);
            int salesVolume = 0;
            for (StoreTransaction storeTransaction : allTransactions) {
                if (DateUtility.fromDate(storeTransaction.getTime()).equals(DateUtility.fromDate(new Date())))
                    salesVolume += storeTransaction.getAmount() * storeTransaction.getItemPrice();
            }

            return Response.Common.ok(Map.of("salesVolume", Integer.toString(salesVolume)));
        } catch (Exception e) {
            log.warn("Failed to get transaction records", e);
            return Response.Common.error("Failed to get transaction records");
        }
    }

    @RouteMapping(uri = "storeItem/searchItem")
    public Response searchItem(Request request, org.hibernate.Session database) {
        try {
            String keyword = request.getParams().get("keyword");
            if (keyword == null)
                return Response.Common.error("Keyword cannot be empty");
            List<StoreItem> items = Database.likeQuery(StoreItem.class,
                    new String[]{"uuid", "itemName", "price", "pictureLink", "barcode", "description"}, keyword, database);
//            return Response.Common.ok(items.stream().collect(Collectors.groupingBy(w -> w.itemName)).entrySet().stream().collect(Collectors.toMap(
//                    Map.Entry::getKey,
//                    e -> e.getValue().stream().map(StoreItem::toJson).collect(Collectors.toList())
//            )));
            return Response.Common.ok(Map.of("items", items.stream().map(StoreItem::toJson).collect(Collectors.toList())));
        } catch (Exception e) {
            return Response.Common.error("Failed to search item");
        }
    }

    @RouteMapping(uri = "storeItem/searchId")
    public Response searchId(Request request, org.hibernate.Session database) {
        try {
            String uuid = request.getParams().get("uuid");
            if (uuid == null)
                return Response.Common.error("UUID cannot be empty");
            UUID id = UUID.fromString(uuid);
            StoreItem storeItem = database.get(StoreItem.class, id);
            if (storeItem == null) {
                return Response.Common.error("missing item information");
            }
            return Response.Common.ok(storeItem.toJson());
        } catch (Exception e) {
            return Response.Common.error("Failed to search item");
        }
    }

    @RouteMapping(uri = "store/filter", role = "shop_user")
    public Response filter(Request request, org.hibernate.Session database) {
        try {
            List<StoreItem> allItems;
            allItems = Database.loadAllData(StoreItem.class, database);
            return Response.Common.ok(allItems.stream().map(StoreItem::toJson).collect(Collectors.toList()));
        } catch (Exception e) {
            log.warn("Failed to filter store items", e);
            return Response.Common.error("Failed to filter store items");
        }
    }

    @RouteMapping(uri = "storeItem/addItem", role = "shop_staff")
    public Response addItem(Request request, org.hibernate.Session database) {
        StoreItem newStoreItem = IEntity.fromJson(request.getParams().get("item"), StoreItem.class);
        newStoreItem.setUuid(UUID.randomUUID());
        if (newStoreItem == null) {
            return Response.Common.badRequest();
        }
        if(Objects.equals(newStoreItem.itemName, "")) {
            return Response.Common.badRequest();
        }
        if(newStoreItem.barcode == "") {
            return Response.Common.badRequest();
        }
        if(newStoreItem.price <= 0) {
            return Response.Common.badRequest();
        }
        if(newStoreItem.stock <= 0) {
            return Response.Common.badRequest();
        }
        if(newStoreItem.pictureLink == "") {
            return Response.Common.badRequest();
        }
        Transaction tx = database.beginTransaction();
        database.persist(newStoreItem);
        tx.commit();
        return Response.Common.ok();
    }

    @RouteMapping(uri = "storeItem/deleteItem", role = "admin")
    public Response deleteItem(Request request, org.hibernate.Session database) {
        String id = request.getParams().get("uuid");
        if (id == null) {
            return Response.Common.error("item name cannot be empty");
        }
        UUID uuid = UUID.fromString(id);
        StoreItem toDelete = database.get(StoreItem.class, uuid);
        if (toDelete == null)
            return Response.Common.error("No such item");
        Transaction tx = database.beginTransaction();
        database.remove(toDelete);
        tx.commit();
        //List<Pair<Integer,StoreItem>>
        return Response.Common.ok();
    }

    @RouteMapping(uri = "storeTransaction/getRecords", role = "shop_staff")
    public Response getRecords(Request request, org.hibernate.Session database) {
        try {
            List<StoreTransaction> allRecords = Database.loadAllData(StoreTransaction.class, database);
            allRecords = allRecords.stream().peek(w -> {
                StoreItem storeItem = database.get(StoreItem.class, w.getItemUUID());
                w.setItem(storeItem);
            }).collect(Collectors.toList());
            allRecords.sort((o1, o2) -> o2.getTime().compareTo(o1.getTime()));
            return Response.Common.ok(allRecords.stream().collect(Collectors.groupingBy(w -> DateUtility.fromDate(w.time))).entrySet().stream().collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> e.getValue().stream().map(StoreTransaction::toJson).collect(Collectors.toList()))));
        } catch (Exception e) {
            log.warn("Failed to get transaction records", e);
            return Response.Common.error("Failed to get transaction records");
        }
    }

    @RouteMapping(uri = "storeTransaction/searchTransaction")
    public Response searchTransaction(Request request, org.hibernate.Session database) {
        try {
            String keyword = request.getParams().get("keyword");
            if (keyword == null)
                return Response.Common.error("Keyword cannot be empty");
            List<StoreTransaction> transactions = Database.likeQuery(StoreTransaction.class,
                    new String[]{"itemName", "itemPrice", "amount", "remark", "time"}, keyword, database);
            return Response.Common.ok(transactions.stream().collect(Collectors.groupingBy(w -> w.uuid)).entrySet().stream().collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> e.getValue().stream().map(StoreTransaction::toJson).collect(Collectors.toList())
            )));
        } catch (Exception e) {
            return Response.Common.error("Failed to search transaction record");
        }
    }

    @RouteMapping(uri = "storeItem/updateItem", role = "shop_staff")
    public Response updateItem(Request request, org.hibernate.Session database) {
        StoreItem newItem = IEntity.fromJson(request.getParams().get("storeItem"), StoreItem.class);

        Transaction tx = database.beginTransaction();
        database.merge(newItem);
        tx.commit();

        return Response.Common.ok();
    }

    @RouteMapping(uri = "storeTransaction/createTransaction")
    public Response createTransaction(Request request, org.hibernate.Session database) {
        UUID itemUUID = UUID.fromString(request.getParams().get("itemUUID"));
        Integer amount = Integer.valueOf(request.getParams().get("amount"));
        StoreItem storeItem = database.get(StoreItem.class, itemUUID);
        if (storeItem == null) {
            return Response.Common.error("No such store item");
        }

        StoreTransaction newStoreTransaction = new StoreTransaction();
        Transaction tx = database.beginTransaction();
        Integer oldStock = storeItem.getStock();
        if (oldStock < amount) {
            return Response.Common.error("Stock cannot be less than amount");
        }
        Integer oldSalesVolume = storeItem.getSalesVolume();
        storeItem.setSalesVolume(oldSalesVolume + amount);
        storeItem.setStock(oldStock - amount);
        newStoreTransaction.setUuid(UUID.randomUUID());
        newStoreTransaction.setItemUUID(storeItem.getUuid());
        newStoreTransaction.setItemPrice(storeItem.getPrice());
        Date currentTime = new Date();
        newStoreTransaction.setTime(currentTime);
        newStoreTransaction.setAmount(amount);
        newStoreTransaction.setCardNumber(0);
        newStoreTransaction.setRemark("");
        database.persist(newStoreTransaction);
        tx.commit();
        return Response.Common.ok();
    }

    //    @RouteMapping(uri = "store/filter", role = "admin")
//    public Response filter(Request request, org.hibernate.Session database) {
//        try {
//            List<StoreItem> allItems;
//            allItems = Database.loadAllData(StoreItem.class, database);
//            return Response.Common.ok(allItems.stream().map(StoreItem::toJson).collect(Collectors.toList()));
//        } catch (Exception e) {
//            log.warn("Failed to filter store items", e);
//            return Response.Common.error("Failed to filter store items");
//        }
//    }
    @RouteMapping(uri = "storeItem/getReport")
    public Response getReport(Request request, org.hibernate.Session database) {
        try {
            List<StoreItem> items;
            items = Database.loadAllData(StoreItem.class, database);
            return Response.Common.ok(items.stream().map(StoreItem::toJson).collect(Collectors.toList()));
        } catch (Exception e) {
            log.warn("Failed to get sales report", e);
            return Response.Common.error("Failed to get sales report");
        }
    }
//    @RouteMapping(uri="storeTransaction/addTransaction")
//    public Response addTransaction(Request request,org.hibernate.Session database){
//        try{
//            //List pairs=Database.loadAllData(List.class,database);
//            //List<Pair<Integer,StoreItem>> pairs=new ArrayList<>();
//
//            //pairs=Database.loadAllData(List<Pair<Integer,StoreItem>>.class,database);
//            List<Integer> amounts=pairs.stream().map(Pair::getFirst).collect(Collectors.toList());
//            List<StoreItem> items=pairs.stream().map(Pair::getSecond).collect(Collectors.toList());
////            List<Integer> amounts = pairs.stream()
////                    .map(Pair::getFirst) // 提取整数部分
////                    .collect(Collectors.toList());
////
////            List<StoreItem> storeItems = pairs.stream()
////                    .map(Pair::getSecond) // 提取StoreItem部分
////                    .collect(Collectors.toList());
//            if(amounts==null||items==null){
//                return Response.Common.badRequest();
//            }
//            Transaction tx=database.beginTransaction();
//            for(int i=0;i<items.size();i++){
//                StoreTransaction newStoreTransaction=new StoreTransaction();
//                StoreItem newStoreItem=items.get(i);
//                newStoreTransaction.setUuid(UUID.randomUUID());
//                newStoreTransaction.setUuid(newStoreItem.getUuid());
//                newStoreTransaction.setAmount(amounts.get(i));
//                newStoreTransaction.setItemPrice(newStoreItem.getPrice());
//                Date currentTime=new Date();
//                newStoreTransaction.setTime(currentTime);
//                newStoreTransaction.setRemark("");
//                newStoreTransaction.setCardNumber(123456);
//                database.persist(newStoreTransaction);
//            }
//            tx.commit();
//            return Response.Common.ok();
//        }catch (Exception e){
//            log.warn("Failed to add transaction record",e);
//            return Response.Common.error("Failed to add transaction record");
//        }
//    }
//    @RouteMapping(uri = "store/filter", role = "admin")
//    public Response filter(Request request, org.hibernate.Session database) {
//        try {
//            List<StoreItem> allItems;
//            allItems = Database.loadAllData(StoreItem.class, database);
//            return Response.Common.ok(allItems.stream().map(StoreItem::toJson).collect(Collectors.toList()));
//        } catch (Exception e) {
//            log.warn("Failed to filter store items", e);
//            return Response.Common.error("Failed to filter store items");
//        }
//    }


//    @RouteMapping(uri = "storeItem/deleteItem",role="admin")
//    public Response deleteItem(Request request, org.hibernate.Session database) {
//        String itemName = request.getParams().get("itemName");
//        if (itemName == null) {
//            return Response.Common.error("item name cannot be empty");
//        }
//        StoreItem storeItem = database.get(StoreItem.class, itemName);
//        if (storeItem == null) {
//            return Response.Common.error("no such uuid");
//        }
//        Transaction tx = database.beginTransaction();
//        database.remove(storeItem);
//        tx.commit();
//
//        return Response.Common.ok();
//    }
//
//    @RouteMapping(uri = "storeItem/updateItem",role="admin")
//    public Response updateItem(Request request, org.hibernate.Session database) {
//        StoreItem newStoreItem = StoreItem.fromMap(request.getParams());
//        if (newStoreItem == null) {
//            return Response.Common.badRequest();
//        }
//        StoreItem storeItem = database.get(StoreItem.class, newStoreItem.getItemName());
//        if (storeItem == null) {
//            return Response.Common.error("Incorrect item name");
//        }
//
//        Transaction tx = database.beginTransaction();
//        storeItem.setItemName(newStoreItem.getItemName());
//        storeItem.setPrice(newStoreItem.getPrice());
//        storeItem.setPictureLink(newStoreItem.getPictureLink());
//        storeItem.setBarcode(newStoreItem.getBarcode());
//        storeItem.setStock(newStoreItem.getStock());
//        storeItem.setDescription(newStoreItem.getDescription());
//        database.persist(storeItem);
//        tx.commit();
//        return Response.Common.ok();
//    }
//
//    @RouteMapping(uri="storeTransaction/selectItem")
//    public Response selectItem(Request request,org.hibernate.Session database){
//        StoreItem selectStoreItem= StoreItem.fromMap(request.getParams());
//        if(selectStoreItem==null){
//            return Response.Common.badRequest();
//        }
//
//        Transaction tx=database.beginTransaction();
//        Integer nowStock=selectStoreItem.getStock();
//        Integer salesVolume=Integer.parseInt(request.getParams().get("amount"));
//        if(salesVolume>nowStock){
//            return Response.Common.error("Sale amount must not exceed stock");
//        }
//        selectStoreItem.setStock(nowStock-salesVolume);
//        selectStoreItem.setSalesVolume(salesVolume);
//        StoreTransaction newStoreTransaction=new StoreTransaction();
//        newStoreTransaction.setUuid(UUID.randomUUID());
//        newStoreTransaction.setItemUUID(selectStoreItem.getUuid());
//        newStoreTransaction.setAmount(salesVolume);
//        newStoreTransaction.setItemPrice(selectStoreItem.getPrice());
//        newStoreTransaction.setCardNumber(Integer.parseInt(request.getParams().get("cardNumber")));
//        newStoreTransaction.setTime(LocalDateTime.now());
//        newStoreTransaction.setRemark(request.getParams().get("remark"));
//        database.persist(newStoreTransaction);
//        tx.commit();
//        return Response.Common.ok();
//    }
//
//    @RouteMapping(uri="storeTransaction/pay")
//    public Response pay(Request request,org.hibernate.Session database){
//        try{
//            Transaction tx=database.beginTransaction();
//            List<StoreTransaction> allItems= Database.loadAllData(StoreTransaction.class,database);
//            Integer totalPrice=0;
//            for(StoreTransaction storeTransaction:allItems){
//                totalPrice+=storeTransaction.getItemPrice()*storeTransaction.getAmount();
//            }
//
//            String cardNumber=request.getParams().get("cardNumber");
//            String password=request.getParams().get("password");
//            if(cardNumber==null||password==null){
//                return Response.Common.badRequest();
//            }
//            User user=database.get(User.class,Integer.parseInt(cardNumber));
//            if(user==null||!Password.verify(password,user.getPassword())){
//                return Response.Common.error("Incorrect card number or password");
//            }
//            //扣钱
//            tx.commit();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return Response.Common.ok();
//    }
//
//    @RouteMapping(uri="storeItem/getReport",role="admin")
//    public Response getReport(Request request,org.hibernate.Session database){
//        try{
//            List<StoreItem> allItems=Database.loadAllData(StoreItem.class,database);
//            if(allItems.isEmpty()){
//                return Response.Common.error("Empty report");
//            }
//            Collections.sort(allItems,(o1,o2)->o2.getSalesVolume()-o1.getSalesVolume());
//            Transaction tx = database.beginTransaction();
//            int i=1;
//            for(StoreItem itemInOrder:allItems){
//                StoreReport storeReport=new StoreReport();
//
//                storeReport.setUuid(UUID.randomUUID());
//                storeReport.setItemUUID(itemInOrder.getUuid());
//                storeReport.setRank(i);
//                storeReport.setItemName(itemInOrder.getItemName());
//                storeReport.setPrice(itemInOrder.getPrice());
//                storeReport.setPictureLink(itemInOrder.getPictureLink());
//                storeReport.setBarcode(itemInOrder.getBarcode());
//                storeReport.setStock(itemInOrder.getStock());
//                storeReport.setSalesVolume(itemInOrder.getSalesVolume());
//                storeReport.setDescription(itemInOrder.getDescription());
//                database.persist(storeReport);
//                i++;
//            }
//            tx.commit();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return Response.Common.ok();
//    }
}
