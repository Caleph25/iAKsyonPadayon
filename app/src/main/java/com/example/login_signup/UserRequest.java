package com.example.login_signup;

public class UserRequest {
    private String firstname;
    private String middlename;
    private String lastname;
    private String contacno;
    private String email;
    private String gender;
    private String birthday;
    private String IMEI;

    private String username;
    private String password;
    private int IsDeactivated;


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getContacno() {
        return contacno;
    }

    public void setContacno(String contacno) {
        this.contacno = contacno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsDeactivated() {
        return IsDeactivated;
    }

    public void setIsDeactivated(Integer isDeactivated) {
        IsDeactivated = isDeactivated;
    }
}
