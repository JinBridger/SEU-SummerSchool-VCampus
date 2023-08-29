package app.vcampus.server.entity;

import app.vcampus.server.enums.PoliticalStatus;
import app.vcampus.server.enums.Status;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;

public class StoreItem {
    public UUID uuid;

    public String name;

    public Integer price;

    public String barcode;

    public Integer stock;

    public String description;

}
