package com.zakmouf.bluetree.domain;

public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String login;

    private String password;

    private String mail;

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getMail() {
	return mail;
    }

    public void setMail(String mail) {
	this.mail = mail;
    }

    @Override
    public String toString() {
	return msg("[{0},{1}]", id, login);
    }

}
