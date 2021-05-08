package com.inferrix.lightsmart.PogoClasses;

import java.io.Serializable;

public class Project implements Serializable {
    private int projectId;
    private String projectNname;
    private String dateTime;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectNname() {
        return projectNname;
    }

    public void setProjectNname(String projectNname) {
        this.projectNname = projectNname;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
