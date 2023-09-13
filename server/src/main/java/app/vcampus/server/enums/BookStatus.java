package app.vcampus.server.enums;

import lombok.Getter;

/**
 * book status enum, used to denote the book status
 */
@Getter
public enum BookStatus implements LabelledEnum {
    available("可借", 0xff508e54),
    lend("借出", 0xffd9b44a),
    newly("新书（未上架）", 0xffd9b44a),
    returned("归还（未上架）", 0xffd9b44a);

    private final String label;
    private final Integer color;

    BookStatus(String label, Integer color) {
        this.label = label;
        this.color = color;
    }

}
