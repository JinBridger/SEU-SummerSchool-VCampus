package app.vcampus.server.entity;

import app.vcampus.server.enums.Status;
import app.vcampus.server.enums.Polistat;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "cardNumber")
    public Integer cardNumber;

    public String studentNumber;

    public Integer major;

    public Integer school;

    @Enumerated(EnumType.STRING)
    public Status status;

    public String birthPlace;

    @Enumerated(EnumType.STRING)
    public Polistat polistat;
}
