package app.vcampus.server.enums;

import lombok.Getter;

@Getter
public enum CardStatus implements LabelledEnum {
    normal("正常"),
    frozen("冻结"),
    lost("挂失");

    private final String label;

    CardStatus(String label) {
        this.label = label;
    }
}
