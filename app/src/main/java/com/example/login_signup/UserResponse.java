package com.example.login_signup;

public class UserResponse {

    private int id;
    private String firstname;
    private String middlename;
    private String lastname;
    private String contacno;
    private String email;
    private String gender;
    private String birthday;
    private String IMEI;

    private int fk_MobileUserId;
    private String username;
    private String password;
    private String IsDeactivated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
    public int get_tfk_MobileUserId(){
        return fk_MobileUserId;
    }
    public void set_fk_MobileUserId(int fk_MobileUserId){
        this.fk_MobileUserId = fk_MobileUserId;
    }
    public String get_username(){
        return username;
    }
    public void set_username(String username){
        this.username = username;
    }
    public String get_password(){
        return password;
    }
    public void set_password(String password){
        this.password = password;
    }
    public String get_IsDeactivated(){
        return IsDeactivated;
    }
    public void set_IsDeactivated(String IsDeactivated){
        this.IsDeactivated = IsDeactivated;
    }
}
