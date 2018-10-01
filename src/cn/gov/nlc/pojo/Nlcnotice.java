package cn.gov.nlc.pojo;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cn.gov.nlc.base.converter.CustomDateSerializer;

public class Nlcnotice {
    private String noticeid;

    private String title;

    private String subPerson;

    private Date subTime;

    private Date pubTime;

    private Date updTime;

    private Date bkpbtime;

    private String src;

    private String board;

    private String status;

    private String source;

    private String annxurl;

    private String titlesrc;

    private Integer praisecount;

    private Integer collectcount;

    private String pushmethod;

    private Integer tops;

    private String content;

    public String getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(String noticeid) {
        this.noticeid = noticeid == null ? null : noticeid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSubPerson() {
        return subPerson;
    }

    public void setSubPerson(String subPerson) {
        this.subPerson = subPerson == null ? null : subPerson.trim();
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getSubTime() {
        return subTime;
    }

    public void setSubTime(Date subTime) {
        this.subTime = subTime;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getBkpbtime() {
        return bkpbtime;
    }

    public void setBkpbtime(Date bkpbtime) {
        this.bkpbtime = bkpbtime;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src == null ? null : src.trim();
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board == null ? null : board.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getAnnxurl() {
        return annxurl;
    }

    public void setAnnxurl(String annxurl) {
        this.annxurl = annxurl == null ? null : annxurl.trim();
    }

    public String getTitlesrc() {
        return titlesrc;
    }

    public void setTitlesrc(String titlesrc) {
        this.titlesrc = titlesrc == null ? null : titlesrc.trim();
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

    public Integer getTops() {
        return tops;
    }

    public void setTops(Integer tops) {
        this.tops = tops;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}