package com.zakmouf.bluetree.domain;

public class Profile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return msg("[{0},{1}]", id, name);
    }

}
