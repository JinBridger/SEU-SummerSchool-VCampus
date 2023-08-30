package app.vcampus.server.entity;

import app.vcampus.server.enums.PoliticalStatus;
import app.vcampus.server.enums.Status;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;

@Entity
@Data
@Table(name = "storeitem")
@Slf4j
public class StoreItem {
    @Column(nullable = false)
    public UUID uuid;

    @Column(nullable = false)
    public String itemName;

    @Column(nullable = false)
    public Integer price;

    @Column(nullable = false)
    public String barcode;

    @Column(nullable = false)
    public Integer stock;

    public String description;

    public static StoreItem fromMap(Map<String,String> data){
        try{
            StoreItem storeItem=new StoreItem();

            storeItem.setUuid(UUID.fromString(data.get("uuid")));
            storeItem.setItemName(data.get("itemName"));
            storeItem.setPrice(Integer.parseInt(data.get("price")));
            storeItem.setBarcode(data.get("barcode"));
            storeItem.setStock(Integer.parseInt(data.get("stock")));
            storeItem.setDescription(data.get("description"));

            return storeItem;
        }catch (Exception e){
            log.warn("Failed to parse storeitem from map:{}",data,e);
            return null;
        }
    }

    public Map<String,String> toMap(){
        return Map.ofEntries(
                Map.entry("uuid",getUuid().toString()),
                Map.entry("itemName",getItemName()),
                Map.entry("price",getPrice().toString()),
                Map.entry("barcode",getBarcode()),
                Map.entry("stock",getStock().toString()),
                Map.entry("description",getDescription())
        );
    }

}
