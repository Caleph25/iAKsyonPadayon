package com.example.login_signup;

public class ReportHistoryListData {
    private int reportId;
    private int fk_reportCategoryid;
    private int fk_mobileAccountid;
    private String reportsName;
    private String description;
    private String status;
    private String lat;
    private String lng;
    private String timedate;
    public ReportHistoryListData(int reportId, int fk_reportCategoryid, int fk_mobileAccountid,
                                 String reportName, String description, String status, String lat, String lng, String timedate){
        this.reportId = reportId;
        this.fk_reportCategoryid = fk_reportCategoryid;
        this.fk_mobileAccountid = fk_mobileAccountid;
        this.reportsName = reportName;
        this.description = description;
        this.status = status;
        this.lat = lat;
        this.lng = lng;
        this.timedate = timedate;
    }
    public int getReportId(){
        return reportId;
    }
    public void setReportId(int reportId){
        this.reportId = reportId;
    }
    public int getFk_reportCategoryid(){
        return fk_reportCategoryid;
    }
    public void setFk_reportCategoryid(int fk_reportCategoryid){
        this.fk_reportCategoryid = fk_reportCategoryid;
    }
    public int getFk_mobileAccountid(){
        return fk_mobileAccountid;
    }
    public void setFk_mobileAccountid(int fk_mobileAccountid){
        this.fk_mobileAccountid = fk_mobileAccountid;
    }
    public String getReportsName(){
        return reportsName;
    }
    public void setReportName(String reportName){
        this.reportsName = reportName;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getLat(){
        return lat;
    }
    public void setLat(String lat){
        this.lat = lat;
    }
    public String getLng(){
        return lng;
    }
    public void setLng(String lng){
        this.lng = lng;
    }
    public String getTimedate(){
        return timedate;
    }
    public void setTimedate(String timedate){
        this.timedate = timedate;
    }
}
