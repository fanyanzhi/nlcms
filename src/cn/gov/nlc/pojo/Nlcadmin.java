package cn.gov.nlc.pojo;

import java.io.Serializable;
import java.util.Date;

public class Nlcadmin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482160795805861785L;

	private Integer id;

    private String username;

    private String password;

    private Byte role;

    private String module;

    private String moduleid;

    private String staffname;

    private String staffdept;

    private String staffphone;

    private Date time;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Byte getRole() {
        return role;
    }

    public void setRole(Byte role) {
        this.role = role;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    public String getModuleid() {
        return moduleid;
    }

    public void setModuleid(String moduleid) {
        this.moduleid = moduleid == null ? null : moduleid.trim();
    }

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname == null ? null : staffname.trim();
    }

    public String getStaffdept() {
        return staffdept;
    }

    public void setStaffdept(String staffdept) {
        this.staffdept = staffdept == null ? null : staffdept.trim();
    }

    public String getStaffphone() {
        return staffphone;
    }

    public void setStaffphone(String staffphone) {
        this.staffphone = staffphone == null ? null : staffphone.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}