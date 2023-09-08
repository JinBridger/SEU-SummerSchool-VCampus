package app.vcampus.server.enums;

import lombok.Getter;

@Getter
public enum TransactionType implements LabelledEnum {
    deposit("充值", 0xff508e54),
    payment("支出", 0xffce5442);

    private final String label;
    private final Integer color;

    TransactionType(String label, Integer color) {
        this.label = label;
        this.color = color;
    }
}
