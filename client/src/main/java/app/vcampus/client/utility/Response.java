package app.vcampus.client.utility;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class Response {
    UUID id;
    String status;
    String message;

    Map<String, String> data;

}
