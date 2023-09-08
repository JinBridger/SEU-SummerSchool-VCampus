package app.vcampus.client.gateway;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import app.vcampus.client.net.NettyHandler;
import app.vcampus.server.entity.*;
import app.vcampus.server.utility.Pair;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StoreClient {
    public static List<StoreItem> getAll(NettyHandler handler) {
        Request request = new Request();
        request.setUri("storeItem/filter");
        try {
            Response response = BaseClient.sendRequest(handler, request);

            if (response.getStatus().equals("success")) {
                List<String> raw_data = (List<String>) response.getData();
                List<StoreItem> data = new LinkedList<>();
                raw_data.forEach(json -> data.add(IEntity.fromJson(json, StoreItem.class)));
                return data;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return null;
        }
    }

    public static boolean addItem(NettyHandler handler, StoreItem newStoreItem) {
        Request request = new Request();
        request.setUri("storeItem/addItem");
        request.setParams(Map.of("item", newStoreItem.toJson()));
        try {
            Response response = BaseClient.sendRequest(handler, request);
            return response.getStatus().equals("success");
        } catch (InterruptedException e) {
            log.warn("Fail to add item", e);
            return false;
        }
    }

    public static boolean deleteItem(NettyHandler handler, String uuid) {
        Request request = new Request();
        request.setUri("storeItem/deleteItem");
        request.setParams(Map.of("uuid", uuid.toString()));
        try {
            Response response = BaseClient.sendRequest(handler, request);
            return response.getStatus().equals("success");
        } catch (InterruptedException e) {
            log.warn("Fail to delete item", e);
            return false;
        }
    }

    public static boolean updateItem(NettyHandler handler,StoreItem storeItem){
        Request request=new Request();
        request.setUri("storeItem/updateItem");
        request.setParams(Map.of(
                "storeItem",storeItem.toJson()
        ));
        try{
            Response response=BaseClient.sendRequest(handler,request);
            return response.getStatus().equals("success");
        }catch (InterruptedException e){
            log.warn("Fail to update item",e);
            return false;
        }
    }

    public static Map<String, List<StoreItem>> searchItem(NettyHandler handler, String keyword) {
        Request request = new Request();
        request.setUri("storeItem/searchItem");
        request.setParams(Map.of(
                "keyword", keyword
        ));
        try {
            Response response = BaseClient.sendRequest(handler, request);
            if (response.getStatus().equals("success")) {
                Map<String, List<String>> raw_data = (Map<String, List<String>>) response.getData();
                Map<String, List<StoreItem>> data = new HashMap<>();
                raw_data.forEach((key, value) -> data.put(key, value.stream().map(json -> IEntity.fromJson(json, StoreItem.class)).toList()));
                return data;
            } else {
                throw new RuntimeException("Failed to get item info");
            }
        } catch (InterruptedException e) {
            log.warn("Fail to get item info", e);
            return null;
        }
    }
    public static StoreItem searchId(NettyHandler handler, String uuid) {
        Request request = new Request();
        request.setUri("storeItem/searchId");
        request.setParams(Map.of("uuid", uuid));
        try {
            Response response = BaseClient.sendRequest(handler, request);
            if (response.getStatus().equals("success")) {
                StoreItem data=(StoreItem) response.getData();
                return data;
            } else {
                throw new RuntimeException("Failed to get item");
            }
        } catch (InterruptedException e) {
            log.warn("Fail to get item", e);
            return null;
        }
    }

    public static Map<String,List<StoreTransaction>> getTransaction(NettyHandler handler){
        Request request=new Request();
        request.setUri("storeTransaction/getRecords");
        try{
            Response response=BaseClient.sendRequest(handler,request);
            if(response.getStatus().equals("success")){
                Map<String,List<String>> raw_data=(Map<String, List<String>>) response.getData();
                Map<String,List<StoreTransaction>> data=new HashMap<>();
                raw_data.forEach((key,value)->data.put(key,value.stream().map(
                        json->IEntity.fromJson(json,StoreTransaction.class)).toList()));
                return data;
            }else{
                throw new RuntimeException("Failed to get item info");
            }
        }catch (Exception e){
            log.warn(String.valueOf(e));
            return null;
        }
    }
    public static Map<String,List<StoreTransaction>> searchTransaction(NettyHandler handler,String keyword){
        Request request=new Request();
        request.setUri("storeTransaction/searchTransaction");
        request.setParams(Map.of(
                "keyword",keyword
        ));
        try{
            Response response=BaseClient.sendRequest(handler,request);
            if(response.getStatus().equals("success")){
                Map<String,List<String>> raw_data=(Map<String,List<String>>) response.getData();
                Map<String,List<StoreTransaction>> data=new HashMap<>();
                raw_data.forEach((key,value)->data.put(key,value.stream().map(json->IEntity.fromJson(json,StoreTransaction.class)).toList()));
                return data;
            }else{
                throw new RuntimeException("Failed to get transaction info");
            }
        }catch (InterruptedException e){
            log.warn("Fail to get book info", e);
            return null;
        }
    }

    public static boolean createTransaction(NettyHandler handler,String itemUUID,String amount){
        Request request=new Request();
        request.setUri("storeTransaction/createTransaction");
        request.setParams(Map.of(
                "itemUUID",itemUUID,
                "amount",amount
        ));
        try{
            Response response=BaseClient.sendRequest(handler,request);
            return response.getStatus().equals("success");
        }catch (InterruptedException e){
            log.warn("Fail to create transaction",e);
            return false;
        }
    }

//    public static Map<String,List<StoreTransaction>> searchTransaction(NettyHandler handler,String keyword){
//        Request request=new Request();
//        request.setUri("storeTransaction/searchTransaction");
//        request.setParams(Map.of(
//                "keyword",keyword
//        ));
//        try{
//            Response response=BaseClient.sendRequest(handler,request);
//            if(response.getStatus().equals("success")){
//                Map<String,List<String>> raw_data=(Map<String,List<String>>) response.getData();
//                Map<String,List<StoreTransaction>> data=new HashMap<>();
//                raw_data.forEach((key,value)->data.put(key,value.stream().map(json->IEntity.fromJson(json,StoreTransaction.class)).toList()));
//                return data;
//            }else{
//                throw new RuntimeException("Failed to get transaction info");
//            }
//        }catch (InterruptedException e){
//            log.warn("Fail to get book info", e);
//            return null;
//        }
//    }
    public static List<StoreItem> getReport(NettyHandler handler){
        Request request=new Request();
        request.setUri("storeItem/getReport");
        try{
            Response response=BaseClient.sendRequest(handler,request);
            if(response.getStatus().equals("success")){
                List<String> raw_data=(List<String>) response.getData();
                List<StoreItem> data=new LinkedList<>();
                raw_data.forEach(json->data.add(IEntity.fromJson(json,StoreItem.class)));
                Collections.sort(data,(o1,o2)->o2.getSalesVolume()-o1.getSalesVolume());
                return data;
            }else{
                return null;
            }
        }catch (Exception e){
            log.warn(String.valueOf(e));
            return null;
        }
    }
//    public static boolean addTransaction(NettyHandler handler,List<Pair<Integer,StoreItem>> list){
//        Request request=new Request();
//        request.setUri("storeTransaction/addTransaction");
//
//    }

//    public static boolean deleteItem(NettyHandler handler,String itemName){
//        CountDownLatch latch=new CountDownLatch(1);
//        AtomicReference<Response> response=new AtomicReference();
//        Request request=new Request();
//        request.setUri("storeItem/deleteItem");
//        request.setParams(Map.of("itemName",itemName));
//        handler.sendRequest(request,(res)->{
//            response.set(res);
//            System.out.println(res);
//            latch.countDown();
//        });
//
//        try{
//            latch.await();
//        }catch (InterruptedException var6){
//            var6.printStackTrace();
//            return false;
//        }
//
//        if(response.get().getStatus().equals("success")){
//            return true;
//        }else{
//            return false;
//        }
//    }
//
//    public static StoreItem searchItem(NettyHandler handler, String itemName) {
//        CountDownLatch latch = new CountDownLatch(1);
//        AtomicReference<Response> response = new AtomicReference();
//        Request request = new Request();
//        request.setUri("storeItem/searchItem");
//        request.setParams(Map.of("itemName", itemName));
//        handler.sendRequest(request, (res) -> {
//            response.set(res);
//            System.out.println(res);
//            latch.countDown();
//        });
//
//        try {
//            latch.await();
//        } catch (InterruptedException var6) {
//            var6.printStackTrace();
//            return null;
//        }
//
//        if ((response.get()).getStatus().equals("success")) {
//            Map<String, String> data = (Map)(response.get()).getData();
//            return StoreItem.fromMap(data);
//        } else {
//            return null;
//        }
//    }
//
//    public static StoreItem updateItem(NettyHandler handler,String uuid,String itemName,String price,String barcode,String stock,String description){
//        CountDownLatch latch=new CountDownLatch(1);
//        AtomicReference<Response> response=new AtomicReference();
//        Request request=new Request();
//        request.setUri("storeItem/updateItem");
//        request.setParams(Map.of("uuid",uuid,"itemName",itemName,"price",price,"barcode",barcode,"stock",stock,"description",description));
//        handler.sendRequest(request,(res)->{
//            response.set(res);
//            System.out.println(res);
//            latch.countDown();
//        });
//
//        try{
//            latch.await();
//        }catch (InterruptedException var6){
//            var6.printStackTrace();
//            return null;
//        }
//
//        if((response.get()).getStatus().equals("success")){
//            Map<String,String> data=(Map)(response.get()).getData();
//            return StoreItem.fromMap(data);
//        }else{
//            return null;
//        }
//    }
//
//    public static StoreTransaction selectItem(NettyHandler handler,String itemName){
//        CountDownLatch latch=new CountDownLatch(1);
//        AtomicReference<Response> response=new AtomicReference();
//        Request request=new Request();
//        request.setUri("storeTransaction/selectItem");
//        request.setParams(Map.of("itemName",itemName));
//        handler.sendRequest(request,(res)->{
//            response.set(res);
//            System.out.println(res);
//            latch.countDown();
//        });
//
//        try{
//            latch.await();
//        }catch (InterruptedException var6){
//            var6.printStackTrace();
//            return null;
//        }
//        if((response.get()).getStatus().equals("success")){
//            Map<String,String> data=(Map)(response.get()).getData();
//            return StoreTransaction.fromMap(data);
//        }else{
//            return null;
//        }
//    }
//
}


