package app.vcampus.server.utility;

public class Utility {
    public static Response ok() {
        return new Response("success", "OK");
    }

    public static Response permissionDenied() {
        return new Response("error", "Permission denied");
    }

    public static Response notFound() {
        return new Response("error", "Not found");
    }

    public static Response internalError() {
        return new Response("error", "Internal error");
    }
}
