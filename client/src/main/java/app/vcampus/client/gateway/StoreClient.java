package app.vcampus.client.gateway;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import app.vcampus.client.net.NettyHandler;
import app.vcampus.server.entity.IEntity;
import app.vcampus.server.entity.LibraryBook;
import app.vcampus.server.entity.StoreItem;
import app.vcampus.server.entity.StoreTransaction;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StoreClient {
    public static boolean addItem(NettyHandler handler, StoreItem newStoreItem) {
        Request request=new Request();
        request.setUri("storeItem/addItem");
        request.setParams(Map.of("item",newStoreItem.toJson()));
        try{
            Response response=BaseClient.sendRequest(handler,request);
            return response.getStatus().equals("success");
        }catch (InterruptedException e){
            log.warn("Fail to add item",e);
            return false;
        }
    }

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
    public static List<StoreItem> getAll(NettyHandler handler){
        Request request=new Request();
        request.setUri("storeItem/filter");
        try {
            Response response = BaseClient.sendRequest(handler, request);

            if(response.getStatus().equals("success")){
                List<String> raw_data = (List<String>) response.getData();
                List<StoreItem> data = new LinkedList<>();
                raw_data.forEach(json -> data.add(IEntity.fromJson(json, StoreItem.class)));
                return data;
            }else{
                return null;
            }
        } catch (Exception e){
            log.warn(String.valueOf(e));
            return null;
        }

    }

}
