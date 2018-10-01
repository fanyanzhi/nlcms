package cn.gov.nlc.pojo;

public class Usernormalword {
    private Integer id;

    private String username;

    private String hotword;

    private Integer seacount;

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
}