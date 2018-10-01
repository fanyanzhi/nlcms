package cn.gov.nlc.pojo;

import java.util.Date;

public class Picture {
    private Integer id;

    private String wjid;

    private String src;

    private String url;

    private String name;

    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWjid() {
        return wjid;
    }

    public void setWjid(String wjid) {
        this.wjid = wjid == null ? null : wjid.trim();
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src == null ? null : src.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}