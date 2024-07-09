package com.hris.HRIS.dto;

public class ActionRequest {

    private String id;
    private boolean action;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }
}
