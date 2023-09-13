package app.vcampus.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

/**
 * Class StoreTransaction
 * record store item's basic information during transaction
 * for the store module, when user select one kind of store item, a store transaction is generated
 * used for calculating the total expenditure and tracking sales statistics
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

}
