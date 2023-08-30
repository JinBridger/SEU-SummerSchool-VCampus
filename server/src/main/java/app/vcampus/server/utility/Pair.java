package app.vcampus.server.utility;

import lombok.Data;

@Data
public class Pair<F, S> {
    F first;
    S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}
