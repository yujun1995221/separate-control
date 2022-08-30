package com.free.decision.server.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 决策报告
 * @author Xingyf
 */
public class DecisionReportVO implements Serializable {

    private Integer decisionResult;

    private boolean auto;

    private List<String> hitRules;

    public Integer getDecisionResult() {
        return decisionResult;
    }

    public void setDecisionResult(Integer decisionResult) {
        this.decisionResult = decisionResult;
    }

    public List<String> getHitRules() {
        return hitRules;
    }

    public void setHitRules(List<String> hitRules) {
        this.hitRules = hitRules;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    @Override
    public String toString() {
        return "DecisionReportVO{" +
                "decisionResult=" + decisionResult +
                ", auto=" + auto +
                ", hitRules=" + hitRules +
                '}';
    }
}
