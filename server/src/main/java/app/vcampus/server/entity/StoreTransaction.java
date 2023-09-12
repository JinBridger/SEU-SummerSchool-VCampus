package app.vcampus.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

/**
 * StoreTransaction类
 * 记录交易商品时的基本信息
 * 用于商店模块，用户每选择购买一种商品，就产生一条交易记录
 * 在结算总消费金额和统计销售情况时使用
 *
 */
@Entity
@Data
@Table(name = "store_transaction")
@Slf4j

public class StoreTransaction implements IEntity {
    @Id
    public UUID uuid;

    @Column(nullable = false)
    public UUID itemUUID;

    @Column(nullable = false)
    public Integer itemPrice;

    @Column(nullable = false)
    public Integer amount;

    @Column(nullable = false)
    public Integer cardNumber;

    @Column(nullable = false)
    public Date time;

    public String remark;

    @Transient
    public StoreItem item;

//    public static StoreTransaction fromMap(Map<String, String> data) {
//        try {
//            StoreTransaction storeTransaction = new StoreTransaction();
//
//            storeTransaction.setUuid(UUID.fromString(data.get("uuid")));
//            storeTransaction.setItemUUID(UUID.fromString(data.get("itemUUID")));
//            storeTransaction.setItemPrice(Integer.parseInt(data.get("itemPrice")));
//            storeTransaction.setAmount(Integer.parseInt(data.get("amount")));
//            storeTransaction.setCardNumber(Integer.parseInt("cardNumber"));
//            storeTransaction.setTime(LocalDateTime.parse(data.get("time")));
//            storeTransaction.setRemark(data.get("remark"));
//
//            return storeTransaction;
//        } catch (Exception e) {
//            log.warn("Failed to parse student from map:{}", data, e);
//            return null;
//        }
//    }
//
//    public Map<String, String> toMap() {
//        return Map.ofEntries(
//                Map.entry("uuid", getUuid().toString()),
//                Map.entry("itemUUID", getItemUUID().toString()),
//                Map.entry("itemPrice", getItemPrice().toString()),
//                Map.entry("amount", getAmount().toString()),
//                Map.entry("cardNumber", getCardNumber().toString()),
//                Map.entry("time", getTime().toString()),
//                Map.entry("remark", getRemark())
//        );
//    }
}
