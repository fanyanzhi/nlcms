package cn.gov.nlc.pojo;

import java.util.Date;

public class Nlctrailerpraise {
    private Integer id;

    private String loginaccount;

    private String trailerid;

    private Date time;

    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginaccount() {
        return loginaccount;
    }

    public void setLoginaccount(String loginaccount) {
        this.loginaccount = loginaccount == null ? null : loginaccount.trim();
    }

    public String getTrailerid() {
        return trailerid;
    }

    public void setTrailerid(String trailerid) {
        this.trailerid = trailerid == null ? null : trailerid.trim();
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