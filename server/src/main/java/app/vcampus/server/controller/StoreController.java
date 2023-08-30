package app.vcampus.server.controller;

import app.vcampus.server.entity.StoreItem;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;

@Slf4j

public class StoreController {
    @RouteMapping(uri = "storeItem/searchItem")
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

//    @RouteMapping(uri="storeItem/selectItem")
//    public Response selectItem(Request request,org.hibernate.Session database){
//
//    }

    @RouteMapping(uri = "storeItem/addItem")
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

    @RouteMapping(uri = "storeItem/deleteItem")
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

    @RouteMapping(uri = "storeItem/updateItem")
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
        storeItem.setStock(newStoreItem.getStock());
        storeItem.setBarcode(newStoreItem.getBarcode());
        storeItem.setDescription(newStoreItem.getDescription());
        database.persist(storeItem);
        tx.commit();
        return Response.Common.ok();
    }

}
