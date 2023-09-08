package app.vcampus.server.entity;

import app.vcampus.server.enums.CardStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Entity
@Data
@Table(name = "finance_card")
@Slf4j
public class FinanceCard implements IEntity {
    @Id
    public Integer cardNumber = 0;

    public Integer balance = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public CardStatus status = CardStatus.normal;
}
