package com.example.login_signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class addImageRes {
    private String reportName;
    private String reportDescription;
    private String reportImagePath;

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
    public String getReportImagePath(){
        return reportImagePath;
    }
    public void setReportImagePath(String reportImagePath) {
        this.reportImagePath = reportImagePath;
    }
}
