package app.vcampus.server.enums;

import lombok.Getter;

@Getter
public enum BookStatus implements LabelledEnum {
    available("可借"),
    lend("借出"),
    newly("新书（未上架）"),
    returned("已归还（未上架）");

    private final String label;

    BookStatus(String label) {
        this.label = label;
    }

}
