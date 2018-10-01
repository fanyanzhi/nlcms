package cn.gov.nlc.pojo;

import java.util.Date;

public class Nlctemplate {
    private Integer id;

    private String name;

    private Date starttime;

    private Date endtime;

    private String datepic;

    private String poempic;

    private String backpic;

    private String mottopic;

    private String separatorpic;

    private String poemsreturnpic;

    private String translatepic;

    private Byte isdefault;

    private String status;

    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getDatepic() {
        return datepic;
    }

    public void setDatepic(String datepic) {
        this.datepic = datepic == null ? null : datepic.trim();
    }

    public String getPoempic() {
        return poempic;
    }

    public void setPoempic(String poempic) {
        this.poempic = poempic == null ? null : poempic.trim();
    }

    public String getBackpic() {
        return backpic;
    }

    public void setBackpic(String backpic) {
        this.backpic = backpic == null ? null : backpic.trim();
    }

    public String getMottopic() {
        return mottopic;
    }

    public void setMottopic(String mottopic) {
        this.mottopic = mottopic == null ? null : mottopic.trim();
    }

    public String getSeparatorpic() {
        return separatorpic;
    }

    public void setSeparatorpic(String separatorpic) {
        this.separatorpic = separatorpic == null ? null : separatorpic.trim();
    }

    public String getPoemsreturnpic() {
        return poemsreturnpic;
    }

    public void setPoemsreturnpic(String poemsreturnpic) {
        this.poemsreturnpic = poemsreturnpic == null ? null : poemsreturnpic.trim();
    }

    public String getTranslatepic() {
        return translatepic;
    }

    public void setTranslatepic(String translatepic) {
        this.translatepic = translatepic == null ? null : translatepic.trim();
    }

    public Byte getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Byte isdefault) {
        this.isdefault = isdefault;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}