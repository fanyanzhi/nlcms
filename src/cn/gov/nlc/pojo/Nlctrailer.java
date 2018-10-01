package cn.gov.nlc.pojo;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cn.gov.nlc.base.converter.CustomDateSerializer;

public class Nlctrailer {
    private Integer id;

    private String trailerid;

    private String title;

    private String columnid;

    private String columnname;

    private String speaker;

    private String place;

    private String speakername;

    private Date starttime;

    private String source;

    private Date time;

    private Date endtime;

    private String status;

    private String guanqu;

    private String speaktime;

    private Integer praisecount;

    private Integer collectcount;

    private String pushmethod;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrailerid() {
        return trailerid;
    }

    public void setTrailerid(String trailerid) {
        this.trailerid = trailerid == null ? null : trailerid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getColumnid() {
        return columnid;
    }

    public void setColumnid(String columnid) {
        this.columnid = columnid == null ? null : columnid.trim();
    }

    public String getColumnname() {
        return columnname;
    }

    public void setColumnname(String columnname) {
        this.columnname = columnname == null ? null : columnname.trim();
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker == null ? null : speaker.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getSpeakername() {
        return speakername;
    }

    public void setSpeakername(String speakername) {
        this.speakername = speakername == null ? null : speakername.trim();
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getGuanqu() {
        return guanqu;
    }

    public void setGuanqu(String guanqu) {
        this.guanqu = guanqu == null ? null : guanqu.trim();
    }

    public String getSpeaktime() {
        return speaktime;
    }

    public void setSpeaktime(String speaktime) {
        this.speaktime = speaktime == null ? null : speaktime.trim();
    }

    public Integer getPraisecount() {
        return praisecount;
    }

    public void setPraisecount(Integer praisecount) {
        this.praisecount = praisecount;
    }

    public Integer getCollectcount() {
        return collectcount;
    }

    public void setCollectcount(Integer collectcount) {
        this.collectcount = collectcount;
    }

    public String getPushmethod() {
        return pushmethod;
    }

    public void setPushmethod(String pushmethod) {
        this.pushmethod = pushmethod == null ? null : pushmethod.trim();
    }
}