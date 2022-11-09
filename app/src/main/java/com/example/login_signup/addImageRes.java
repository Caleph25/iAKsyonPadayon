package com.example.login_signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class addImageRes {
    private String reportName;
    private String reportDescription;
    private Integer ReportCategoryId;
    private Integer MobileUserAccountId;

    public String getReportName(){
        return reportName;
    }
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportDescription(){
        return reportDescription;
    }
    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public Integer getReportCategoryId(){
        return ReportCategoryId;
    }
    public void setReportCategoryId(Integer ReportCategoryId) {
        this.ReportCategoryId = ReportCategoryId;
    }

    public Integer getMobileUserAccountId(){
        return MobileUserAccountId;
    }
    public void setMobileUserAccountId(Integer MobileUserAccountId) {
        this.MobileUserAccountId = MobileUserAccountId;
    }
}
