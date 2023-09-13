package app.vcampus.server.utility;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

/**
 * Request class.
 */
@Data
public class Request {
    UUID id = UUID.randomUUID();

    String uri;
    Map<String, String> params;

    Session session;
}
