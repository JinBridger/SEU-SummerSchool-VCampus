package app.vcampus.server.enums;

import lombok.Getter;

@Getter
public enum Gender {
    male("男"),
    female("女"),
    unspecified("未知");

    private String label;

    Gender(String label) {
        this.label = label;
    }
}
