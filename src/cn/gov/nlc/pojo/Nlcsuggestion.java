package cn.gov.nlc.pojo;

import java.util.Date;

public class Nlcsuggestion {
    private Integer id;

    private String uid;

    private String question;

    private String type;

    private String picurl;

    private Date asktime;

    private String username;

    private String email;

    private String qq;

    private String phone;

    private String answer;

    private Date anstime;

    private String adminname;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl == null ? null : picurl.trim();
    }

    public Date getAsktime() {
        return asktime;
    }

    public void setAsktime(Date asktime) {
        this.asktime = asktime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public Date getAnstime() {
        return anstime;
    }

    public void setAnstime(Date anstime) {
        this.anstime = anstime;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname == null ? null : adminname.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	@Override
	public String toString() {
		return "Nlcsuggestion [id=" + id + ", uid=" + uid + ", question=" + question + ", type=" + type + ", picurl="
				+ picurl + ", asktime=" + asktime + ", username=" + username + ", email=" + email + ", qq=" + qq
				+ ", phone=" + phone + ", answer=" + answer + ", anstime=" + anstime + ", adminname=" + adminname
				+ ", status=" + status + "]";
	}
    
}