package com.agendor.testeagendor.model;

import com.agendor.testeagendor.model.enums.AssignmentType;

public class IconToolbar {

    private AssignmentType type;
    private Integer qnt;

    public IconToolbar(AssignmentType type, Integer qnt){
        this.type = type;
        this.qnt = qnt;
    }

    public AssignmentType getType() {
        return type;
    }

    public void setType(AssignmentType type) {
        this.type = type;
    }

    public Integer getQnt() {
        return qnt;
    }

    public void setQnt(Integer qnt) {
        this.qnt = qnt;
    }
}
