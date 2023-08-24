package app.vcampus.server.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response {
    @NonNull String status;
    @NonNull String message;

    Object data;
    transient Session session = null;
}
