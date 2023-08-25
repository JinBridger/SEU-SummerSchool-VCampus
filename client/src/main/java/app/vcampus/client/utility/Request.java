package app.vcampus.client.utility;

import lombok.Data;

import java.util.Map;

@Data
public class Request {
    String uri;
    Map<String, String> params;
}
