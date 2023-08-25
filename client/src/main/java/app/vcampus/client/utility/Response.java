package app.vcampus.client.utility;

import lombok.Data;

import java.util.Map;

@Data
public class Response {
    String status;
    String message;

    Map<String, String> data;

}
