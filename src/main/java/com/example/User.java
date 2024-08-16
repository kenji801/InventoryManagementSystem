package com.example;

public class User {
	private int userid;
    private String username;
    private String password;
    private int adminflag;
    
    public User(int userid,String username, String password, int adminflag) {
    	this.userid = userid;
        this.username = username;
        this.password = password;
        this.adminflag = adminflag;
    }
    

    public User(String username, String password, int adminflag) {
    	this.username = username;
        this.password = password;
        this.adminflag = adminflag;
	}

	// Getters and Setters
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getAdminflag() {
        return adminflag;
    }

    public void setAdminflag(int adminflag) {
        this.adminflag = adminflag;
    }
}
