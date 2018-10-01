package cn.gov.nlc.pojo;

public class Olcchotword {
    private Integer id;

    private String hotword;

    private Integer seacount;

    private Integer sort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHotword() {
        return hotword;
    }

    public void setHotword(String hotword) {
        this.hotword = hotword == null ? null : hotword.trim();
    }

    public Integer getSeacount() {
        return seacount;
    }

    public void setSeacount(Integer seacount) {
        this.seacount = seacount;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}