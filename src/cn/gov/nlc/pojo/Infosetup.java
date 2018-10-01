package cn.gov.nlc.pojo;

import java.util.Date;

public class Infosetup {
    private Integer id;

    private Integer typeid;

    private String typename;

    private String pushmethod;

    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }

    public String getPushmethod() {
        return pushmethod;
    }

    public void setPushmethod(String pushmethod) {
        this.pushmethod = pushmethod == null ? null : pushmethod.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}