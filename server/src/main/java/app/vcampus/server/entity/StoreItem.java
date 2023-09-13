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

}
