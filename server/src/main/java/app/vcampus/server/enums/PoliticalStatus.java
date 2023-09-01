package app.vcampus.server.enums;

import lombok.Getter;

@Getter
public enum PoliticalStatus implements LabelledEnum {
    CommunistPartyOfChina("中国共产党党员"),

    ProbationaryPartyMember("中国共产党预备党员"),

    CommunistYouthLeagueMember("中国共产主义青年团团员"),

    Masses("群众"),

    MDCMember("民主党派");

    private String label;

    PoliticalStatus(String label) {
        this.label = label;
    }

}
