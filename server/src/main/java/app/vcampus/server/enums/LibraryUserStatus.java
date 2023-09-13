package app.vcampus.server.enums;

import lombok.Getter;

/**
 * enums for library user status, however not used
 */
@Getter
public enum LibraryUserStatus {
    nice("信誉合格"),
    bad("信誉不合格");

    private String label;

    LibraryUserStatus(String label) {
        this.label = label;
    }
}
