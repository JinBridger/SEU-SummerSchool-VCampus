package app.vcampus.server.enums;

import lombok.Getter;

@Getter
public enum StudentStatus implements LabelledEnum {
    inSchool("在籍"),
    graduated("毕业"),
    dropout("退学"),
    suspended("休学"),
    expelled("开除");

    private String label;

    StudentStatus(String label) {
        this.label = label;
    }
}
