package cn.gov.nlc.pojo;

public class Readerpicset {
    private Integer id;

    private String typeid;

    private String typename;

    private String status;

    private Integer sequ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid == null ? null : typeid.trim();
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getSequ() {
        return sequ;
    }

    public void setSequ(Integer sequ) {
        this.sequ = sequ;
    }
}