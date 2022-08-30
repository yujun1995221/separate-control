package com.free.decision.server.enums;

/**
 * 全自动决策渠道
 */
public enum DecisionChannelEnum {

    ZHI_MI(1, "指迷"), PEI_QI_FEN(2, "佩琪分");

    private int id;

    private String name;

    DecisionChannelEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DecisionChannelEnum getEnum(int id) {
        for (DecisionChannelEnum decisionChannelEnum : DecisionChannelEnum.values()) {
            if (decisionChannelEnum.getId()==id) {
                return decisionChannelEnum;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
