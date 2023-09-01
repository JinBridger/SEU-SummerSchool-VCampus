package app.vcampus.server.enums;

import lombok.Getter;

@Getter
public enum BookStatus implements LabelledEnum {
    outLoan("出借"),
    available("可借"),
    newly("新增"),
    reserved("预定");

    private String label;

    BookStatus(String label) {
        this.label = label;
    }

}
