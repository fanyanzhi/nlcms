package cn.gov.nlc.pojo;

import java.util.Date;

public class Binduser {
    private Integer id;

    private String token;

    private String loginaccount;

    private String prikey;

    private String enpassword;

    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getLoginaccount() {
        return loginaccount;
    }

    public void setLoginaccount(String loginaccount) {
        this.loginaccount = loginaccount == null ? null : loginaccount.trim();
    }

    public String getPrikey() {
        return prikey;
    }

    public void setPrikey(String prikey) {
        this.prikey = prikey == null ? null : prikey.trim();
    }

    public String getEnpassword() {
        return enpassword;
    }

    public void setEnpassword(String enpassword) {
        this.enpassword = enpassword == null ? null : enpassword.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}