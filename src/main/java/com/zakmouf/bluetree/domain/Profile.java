package com.zakmouf.bluetree.domain;

public class Profile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;
    
    private Boolean isDefault;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
    
    public Boolean getIsDefault() {
	return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
	this.isDefault = isDefault;
    }

    @Override
    public String toString() {
	return msg("[{0},{1},{2}]", id, name, isDefault);
    }

}
