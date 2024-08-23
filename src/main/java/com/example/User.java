package com.example;

import java.sql.Date;

public class User {
	private int userid;
    private String username;
    private String password;
    private int adminflag;
    private String mail;
    private Date registered_date;
    private Date updated_date;
    private Date last_login_date;
    private boolean deleteflag;
    
    public User(int userid,String username, String password, int adminflag, String mail,java.sql.Date registered_date, java.sql.Date updated_date, java.sql.Date last_login_date) {
    	this.userid = userid;
        this.username = username;
        this.password = password;
        this.adminflag = adminflag;
        this.mail = mail;
        this.registered_date = registered_date;
        this.updated_date = updated_date;
        this.last_login_date = last_login_date;
        
    }
    
    public User(int userid,String username, String password, int adminflag, String mail,java.sql.Date registered_date, java.sql.Date updated_date, java.sql.Date last_login_date, boolean deleteflag) {
    	this.userid = userid;
        this.username = username;
        this.password = password;
        this.adminflag = adminflag;
        this.mail = mail;
        this.registered_date = registered_date;
        this.updated_date = updated_date;
        this.last_login_date = last_login_date;
        this.deleteflag = deleteflag;
        
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
    
    public String getMail() {
        return mail;
    }

    public void setMaile(String mail) {
        this.mail = mail;
    }
    
    public Date getRegistered_date() {
        return registered_date;
    }

    public void setRegistered_date(Date registered_date) {
        this.registered_date = registered_date;
    }
    
    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }
    
    public Date getLast_login_date() {
        return last_login_date;
    }

    public void setLast_login_date(Date last_login_date) {
        this.last_login_date = last_login_date;
    }
    
    public boolean getdeleteflag() {
        return deleteflag;
    }

    public void setdeleteflag(boolean deleteflag) {
        this.deleteflag = deleteflag;
    }
}
