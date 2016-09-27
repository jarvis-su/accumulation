package com.acs.fileChecker.db.beans;

public class TblVariable {
    private String name;
    private String value;
    private String varTypeId;
    private String type;
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getVarTypeId() {
        return varTypeId;
    }

    public void setVarTypeId(String varTypeId) {
        this.varTypeId = varTypeId;
    }
}
