package app.vcampus.server.utility;

import lombok.Data;

import java.util.Map;

@Data
public class Request {
    String uri;
    Map<String, String> params;

    Session session;
}
