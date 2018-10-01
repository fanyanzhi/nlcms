package cn.gov.nlc.pojo;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cn.gov.nlc.base.converter.CustomDateSerializer;

public class Wjreader {
    private Integer id;

    private String pid;

    private String wjyear;

    private String wjmonth;

    private Date wjdate;

    private String shiju;

    private String sjsource;

    private String sjyiwen;

    private String sjurl;

    private String quanshi;

    private String geyan;

    private String gysource;

    private String gyyiwen;

    private String source;

    private String status;

    private Date bkpubtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getWjyear() {
        return wjyear;
    }

    public void setWjyear(String wjyear) {
        this.wjyear = wjyear == null ? null : wjyear.trim();
    }

    public String getWjmonth() {
        return wjmonth;
    }

    public void setWjmonth(String wjmonth) {
        this.wjmonth = wjmonth == null ? null : wjmonth.trim();
    }

    public Date getWjdate() {
        return wjdate;
    }

    public void setWjdate(Date wjdate) {
        this.wjdate = wjdate;
    }

    public String getShiju() {
        return shiju;
    }

    public void setShiju(String shiju) {
        this.shiju = shiju == null ? null : shiju.trim();
    }

    public String getSjsource() {
        return sjsource;
    }

    public void setSjsource(String sjsource) {
        this.sjsource = sjsource == null ? null : sjsource.trim();
    }

    public String getSjyiwen() {
        return sjyiwen;
    }

    public void setSjyiwen(String sjyiwen) {
        this.sjyiwen = sjyiwen == null ? null : sjyiwen.trim();
    }

    public String getSjurl() {
        return sjurl;
    }

    public void setSjurl(String sjurl) {
        this.sjurl = sjurl == null ? null : sjurl.trim();
    }

    public String getQuanshi() {
        return quanshi;
    }

    public void setQuanshi(String quanshi) {
        this.quanshi = quanshi == null ? null : quanshi.trim();
    }

    public String getGeyan() {
        return geyan;
    }

    public void setGeyan(String geyan) {
        this.geyan = geyan == null ? null : geyan.trim();
    }

    public String getGysource() {
        return gysource;
    }

    public void setGysource(String gysource) {
        this.gysource = gysource == null ? null : gysource.trim();
    }

    public String getGyyiwen() {
        return gyyiwen;
    }

    public void setGyyiwen(String gyyiwen) {
        this.gyyiwen = gyyiwen == null ? null : gyyiwen.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getBkpubtime() {
        return bkpubtime;
    }

    public void setBkpubtime(Date bkpubtime) {
        this.bkpubtime = bkpubtime;
    }
}