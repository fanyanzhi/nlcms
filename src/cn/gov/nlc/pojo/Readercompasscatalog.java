package cn.gov.nlc.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Readercompasscatalog {
	@JsonIgnore
    private Integer id;

	@JsonProperty("id")
    private String cataloguuid;

	@JsonProperty("text")
    private String title;

    private String pid;

    @JsonProperty("iconCls")
    private String iconcls;

    private Integer cseq;

    private String checked;

    private String state;

    private Date createtime;

    private Date bkpubtime;

    private Date updatetime;

    private String status;

    private String isdir;

    private Integer leaforder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCataloguuid() {
        return cataloguuid;
    }

    public void setCataloguuid(String cataloguuid) {
        this.cataloguuid = cataloguuid == null ? null : cataloguuid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getIconcls() {
        return iconcls;
    }

    public void setIconcls(String iconcls) {
        this.iconcls = iconcls == null ? null : iconcls.trim();
    }

    public Integer getCseq() {
        return cseq;
    }

    public void setCseq(Integer cseq) {
        this.cseq = cseq;
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getBkpubtime() {
        return bkpubtime;
    }

    public void setBkpubtime(Date bkpubtime) {
        this.bkpubtime = bkpubtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getIsdir() {
        return isdir;
    }

    public void setIsdir(String isdir) {
        this.isdir = isdir == null ? null : isdir.trim();
    }

    public Integer getLeaforder() {
        return leaforder;
    }

    public void setLeaforder(Integer leaforder) {
        this.leaforder = leaforder;
    }
}