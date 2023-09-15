package app.vcampus.server.controller;

import app.vcampus.server.entity.*;
import app.vcampus.server.enums.CardStatus;
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
    /**
     * for shop_user to buy store item
     * The constraints are buy's cardNumber != null, the item != null, the stock is enough,the balance is enough
     * @param request  from client with role and uri
     * @param database database
     * @return the response with ok or error
     */
    @RouteMapping(uri = "store/buy", role = "shop_user")
    public Response buy(Request request, org.hibernate.Session database) {
        try {
            FinanceCard financeCard = database.get(FinanceCard.class, request.getSession().getCardNum());
            if (financeCard == null)
                return Response.Common.error("No such finance card");

            if (financeCard.status != CardStatus.normal)
                return Response.Common.error("Card is not normal");

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

    /**
     * get all store transaction now
     * @param request  from client with role and uri
     * @param database database
     * @return return list<StoreTransaction>
     */
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

    /**
     * for shop_staff to get todaySales
     * @param request  from client with role and uri
     * @param database database
     * @returnit returns an “OK” response with a map containing a JSON string representing the salesVolume information
     */
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

    /**
     * for shop_staff to search store Item
     * The constraints is the keyword != null
     * @param request  from client with role and uri
     * @param database database
     * @return response with the searched Items
     */
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

    /**
     * for shop_staff to search store Item by itemID
     * The constraints is the uuid != null and storeItem != null
     * @param request  from client with role and uri
     * @param database database
     * @return response with the searched Items
     */
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

    /**
     * for shop_user to get all store items
     * @param request  from client with role and uri
     * @param database database
     * @return response with the all items
     */
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

    /**
     * for shop_staff to add store item
     * The constraints is the newStoreItem != null
     * @param request  from client with role and uri
     * @param database database
     * @return  response with ok or error
     */
    @RouteMapping(uri = "storeItem/addItem", role = "shop_staff")
    public Response addItem(Request request, org.hibernate.Session database) {
        StoreItem newStoreItem = IEntity.fromJson(request.getParams().get("item"), StoreItem.class);
        newStoreItem.setUuid(UUID.randomUUID());
        if (newStoreItem == null) {
            return Response.Common.badRequest();
        }
        if (Objects.equals(newStoreItem.itemName, "")) {
            return Response.Common.badRequest();
        }
        if (Objects.equals(newStoreItem.barcode, "")) {
            return Response.Common.badRequest();
        }
        if (newStoreItem.price <= 0) {
            return Response.Common.badRequest();
        }
        if (newStoreItem.stock <= 0) {
            return Response.Common.badRequest();
        }
        if (Objects.equals(newStoreItem.pictureLink, "")) {
            return Response.Common.badRequest();
        }
        Transaction tx = database.beginTransaction();
        database.persist(newStoreItem);
        tx.commit();
        return Response.Common.ok();
    }

    /**
     * for shop_staff to get transaction records
     * @param request  from client with role and uri
     * @param database datebase
     * @return   it returns an “OK” response with a map containing a list of JSON strings representing the transaction records grouped by date
     */
    @RouteMapping(uri = "storeTransaction/getRecords", role = "shop_user")
    public Response getRecords(Request request, org.hibernate.Session database) {
        try {
//            List<StoreTransaction> allRecords = Database.loadAllData(StoreTransaction.class, database);
            List<StoreTransaction> allRecords = Database.getWhereString(StoreTransaction.class, "cardNumber", Integer.toString(request.getSession().getCardNum()), database);
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
    /**
     * for shop_staff to search store transaction
     * @param request  from client with role and uri
     * @param database database
     * @return  it returns an “OK” response with a map containing a JSON string representing storeTransaction
     */
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

    /**
     * for shop_staff to update store item
     * @param request  from client with role and uri
     * @param database database
     * @return response with ok or error
     */
    @RouteMapping(uri = "storeItem/updateItem", role = "shop_staff")
    public Response updateItem(Request request, org.hibernate.Session database) {
        StoreItem newItem = IEntity.fromJson(request.getParams().get("storeItem"), StoreItem.class);

        Transaction tx = database.beginTransaction();
        database.merge(newItem);
        tx.commit();

        return Response.Common.ok();
    }
}

