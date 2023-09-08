package app.vcampus.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Entity
@Data
@Table(name = "finance_card")
@Slf4j
public class FinanceCard implements IEntity {
    @Id
    public Integer cardNumber;

    public Integer balance;
}
