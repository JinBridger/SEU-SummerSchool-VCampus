package app.vcampus.server.enums;

import lombok.Getter;

/**
 * enums for library action, however not used
 */
@Getter
public enum LibraryAction implements LabelledEnum {
    borrow("借出"),
    returnBook("还书");

    private final String label;

    LibraryAction(String label) {
        this.label = label;
    }

}
