package app.vcampus.server.enums;

import lombok.Getter;

@Getter
public enum LibraryAction implements IEnum{
    borrow("借出"),
    returnBook("还书");

    private String label;

    LibraryAction(String label){
        this.label=label;
    }

}
