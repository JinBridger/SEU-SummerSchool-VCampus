package app.vcampus.server.utility;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class Request {
    UUID id;

    String uri;
    Map<String, String> params;
    Session session;
}
