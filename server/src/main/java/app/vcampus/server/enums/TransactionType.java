package app.vcampus.server.enums;

public enum TransactionType implements LabelledEnum {
    deposit("充值"),
    payment("支出");

    private final String label;

    TransactionType(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
