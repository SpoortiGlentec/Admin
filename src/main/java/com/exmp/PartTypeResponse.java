package com.exmp;



public class PartTypeResponse {
    private Long partTypeId;
    private String partTypeName;

    // Constructor
    public PartTypeResponse(Long partTypeId, String partTypeName) {
        this.partTypeId = partTypeId;
        this.partTypeName = partTypeName;
    }

    // Getters and Setters
    public Long getPartTypeId() {
        return partTypeId;
    }

    public void setPartTypeId(Long partTypeId) {
        this.partTypeId = partTypeId;
    }

    public String getPartTypeName() {
        return partTypeName;
    }

    public void setPartTypeName(String partTypeName) {
        this.partTypeName = partTypeName;
    }
}

