package app.vcampus.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;

@Entity
@Data
@Table(name = "storereport")
@Slf4j
public class StoreReport {
    @Column(nullable = false)
    public UUID uuid;

    @Column(nullable = false)
    public UUID itemUUID;

    @Column(nullable = false)
    public Integer rank;

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
    public Integer salesVolume;

    public String description;

    public static StoreReport fromMap(Map<String, String> data) {
        try {
            StoreReport storeReport = new StoreReport();

            storeReport.setUuid(UUID.fromString(data.get("uuid")));
            storeReport.setItemUUID(UUID.fromString(data.get("itemUUID")));
            storeReport.setRank(Integer.parseInt(data.get("rank")));
            storeReport.setItemName(data.get("itemName"));
            storeReport.setPrice(Integer.parseInt(data.get("price")));
            storeReport.setPictureLink(data.get("pictureLink"));
            storeReport.setBarcode(data.get("barcode"));
            storeReport.setStock(Integer.parseInt(data.get("stock")));
            storeReport.setSalesVolume(Integer.parseInt(data.get("salesVolume")));
            storeReport.setDescription(data.get("description"));

            return storeReport;
        } catch (Exception e) {
            log.warn("Failed to parse storeitem from map:{}", data, e);
            return null;
        }
    }

    public Map<String, String> toMap() {
        return Map.ofEntries(
                Map.entry("uuid", getUuid().toString()),
                Map.entry("itemUUID", getItemUUID().toString()),
                Map.entry("rank", getRank().toString()),
                Map.entry("itemName", getItemName()),
                Map.entry("price", getPrice().toString()),
                Map.entry("pictureLink", getPictureLink()),
                Map.entry("barcode", getBarcode()),
                Map.entry("stock", getStock().toString()),
                Map.entry("salesVolume", getSalesVolume().toString()),
                Map.entry("description", getDescription())
        );
    }
}
