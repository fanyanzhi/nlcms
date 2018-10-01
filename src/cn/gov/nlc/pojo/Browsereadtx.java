package cn.gov.nlc.pojo;

import java.util.Date;

public class Browsereadtx {
    private Integer id;

    private String username;

    private String magazineid;

    private Date time;

    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getMagazineid() {
        return magazineid;
    }

    public void setMagazineid(String magazineid) {
        this.magazineid = magazineid == null ? null : magazineid.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }
}