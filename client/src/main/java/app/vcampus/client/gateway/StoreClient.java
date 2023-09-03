package app.vcampus.client.gateway;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import app.vcampus.client.net.NettyHandler;
import app.vcampus.server.entity.StoreItem;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;


public class StoreClient {
    public static StoreItem addItem(NettyHandler handler, String uuid, String itemName, String price, String barcode, String stock, String description) {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Response> response = new AtomicReference();
        Request request = new Request();
        request.setUri("store/addItem");
        request.setParams(Map.of("uuid", uuid, "itemName", itemName, "price", price, "barcode", barcode, "stock", stock, "description", description));
        handler.sendRequest(request, (res) -> {
            response.set(res);
            System.out.println(res);
            latch.countDown();
        });

        try {
            latch.await();
        } catch (InterruptedException var16) {
            var16.printStackTrace();
            return null;
        }

        if ((response.get()).getStatus().equals("success")) {
            Map<String, String> data = (Map)((Map)((Response)response.get()).getData()).get("storeItem");
            StoreItem storeItem=StoreItem.fromMap(data);
            return storeItem;
        } else {
            return null;
        }
    }



    public static StoreItem searchItem(NettyHandler handler, String itemName) {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Response> response = new AtomicReference();
        Request request = new Request();
        request.setUri("storeItem/searchItem");
        request.setParams(Map.of("itemName", itemName));
        handler.sendRequest(request, (res) -> {
            response.set(res);
            System.out.println(res);
            latch.countDown();
        });

        try {
            latch.await();
        } catch (InterruptedException var6) {
            var6.printStackTrace();
            return null;
        }

        if ((response.get()).getStatus().equals("success")) {
            Map<String, String> data = (Map)(response.get()).getData();
            return StoreItem.fromMap(data);
        } else {
            return null;
        }
    }

    public static StoreItem updateItem(NettyHandler handler,String uuid,String itemName,String price,String barcode,String stock,String description){
        CountDownLatch latch=new CountDownLatch(1);
        AtomicReference<Response> response=new AtomicReference();
        Request request=new Request();
        request.setUri("storeItem/updateItem");
        request.setParams(Map.of("uuid",uuid,"itemName",itemName,"price",price,"barcode",barcode,"stock",stock,"description",description));
        handler.sendRequest(request,(res)->{
            response.set(res);
            System.out.println(res);
            latch.countDown();
        });

        try{
            latch.await();
        }catch (InterruptedException var6){
            var6.printStackTrace();
            return null;
        }

        if((response.get()).getStatus().equals("success")){
            Map<String,String> data=(Map)(response.get()).getData();
            return StoreItem.fromMap(data);
        }else{
            return null;
        }
    }
}
