package app.vcampus.server.entity;

import com.google.gson.Gson;

public interface IEntity {
    Gson gson = new Gson();

    static <T extends IEntity> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    default String toJson() {
        return gson.toJson(this);
    }
}
