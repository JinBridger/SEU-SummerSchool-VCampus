package app.vcampus.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Class StoreItem
 * record basic information of store items
 * for the store module, every record in the database can be shown and chosen
 *
 */
@Entity
@Data
@Table(name = "store_item")
@Slf4j
public class StoreItem implements IEntity {
    @Id
    public UUID uuid = UUID.randomUUID();

    @Column(nullable = false)
    public String itemName;

    @Column(nullable = false)
    public Integer price;

    @Column(nullable = false)
    public String pictureLink;

    @Column(nullable = false)
    public String barcode;

    @Column(nullable = false)
    public Integer stock;

    @Column(nullable = false)
    public Integer salesVolume = 0;

    @Column(columnDefinition = "TEXT")
    public String description;


//    public static StoreItem fromMap(Map<String, String> data) {
//        try {
//            StoreItem storeItem = new StoreItem();
//
//            storeItem.setUuid(UUID.fromString(data.get("uuid")));
//            storeItem.setItemName(data.get("itemName"));
//            storeItem.setPrice(Integer.parseInt(data.get("price")));
//            storeItem.setPictureLink(data.get("pictureLink"));
//            storeItem.setBarcode(data.get("barcode"));
//            storeItem.setStock(Integer.parseInt(data.get("stock")));
//            storeItem.setSalesVolume(Integer.parseInt(data.get("salesVolume")));
//            storeItem.setDescription(data.get("description"));
//
//            return storeItem;
//        } catch (Exception e) {
//            log.warn("Failed to parse storeitem from map:{}", data, e);
//            return null;
//        }
//    }
//
//    public Map<String, String> toMap() {
//        return Map.ofEntries(
//                Map.entry("uuid", getUuid().toString()),
//                Map.entry("itemName", getItemName()),
//                Map.entry("price", getPrice().toString()),
//                Map.entry("pictureLink",getPictureLink()),
//                Map.entry("barcode", getBarcode()),
//                Map.entry("stock", getStock().toString()),
//                Map.entry("salesVolume",getSalesVolume().toString()),
//                Map.entry("description", getDescription())
//        );
//    }

}
