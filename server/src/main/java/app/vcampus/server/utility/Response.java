package app.vcampus.server.utility;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * Response class.
 */
@Data
@RequiredArgsConstructor
public class Response {
    UUID id;
    @NonNull String status;
    @NonNull String message;

    Object data;
    transient Session session = null;

    /**
     * Common responses.
     */
    public static class Common {
        public static Response ok() {
            return new Response("success", "OK");
        }

        public static Response ok(Object data) {
            Response response = new Response("success", "OK");
            response.setData(data);
            return response;
        }

        public static Response error(String message) {
            return new Response("error", message);
        }

        public static Response permissionDenied() {
            return new Response("error", "Permission denied");
        }

        public static Response badRequest() {
            return new Response("error", "Bad request");
        }

        public static Response notFound() {
            return new Response("error", "Not found");
        }

        public static Response internalError() {
            return new Response("error", "Internal error");
        }
    }
}
