package cn.gov.nlc.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Menu {
    private Integer mainid;

    private String texten;
    
    @JsonProperty("text")
    private String textcn;

    private Integer id;

    private Integer pid;

    private String checked;

    private String state;
    @JsonProperty("iconCls")
    private String iconcls;

    private Integer nodorder;

    private String url;

    private Date time;

    public Integer getMainid() {
        return mainid;
    }

    public void setMainid(Integer mainid) {
        this.mainid = mainid;
    }

    public String getTexten() {
        return texten;
    }

    public void setTexten(String texten) {
        this.texten = texten == null ? null : texten.trim();
    }

    public String getTextcn() {
        return textcn;
    }

    public void setTextcn(String textcn) {
        this.textcn = textcn == null ? null : textcn.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked == null ? null : checked.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getIconcls() {
        return iconcls;
    }

    public void setIconcls(String iconcls) {
        this.iconcls = iconcls == null ? null : iconcls.trim();
    }

    public Integer getNodorder() {
        return nodorder;
    }

    public void setNodorder(Integer nodorder) {
        this.nodorder = nodorder;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}