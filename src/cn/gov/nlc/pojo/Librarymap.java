package cn.gov.nlc.pojo;

import java.util.Date;

public class Librarymap {
    private Integer id;

    private String src;

    private String src2;

    private String src3;

    private String src4;

    private String src5;

    private String src6;

    private String src7;

    private String src8;

    private String name;

    private Date time;

    private Integer seq;

    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src == null ? null : src.trim();
    }

    public String getSrc2() {
        return src2;
    }

    public void setSrc2(String src2) {
        this.src2 = src2 == null ? null : src2.trim();
    }

    public String getSrc3() {
        return src3;
    }

    public void setSrc3(String src3) {
        this.src3 = src3 == null ? null : src3.trim();
    }

    public String getSrc4() {
        return src4;
    }

    public void setSrc4(String src4) {
        this.src4 = src4 == null ? null : src4.trim();
    }

    public String getSrc5() {
        return src5;
    }

    public void setSrc5(String src5) {
        this.src5 = src5 == null ? null : src5.trim();
    }

    public String getSrc6() {
        return src6;
    }

    public void setSrc6(String src6) {
        this.src6 = src6 == null ? null : src6.trim();
    }

    public String getSrc7() {
        return src7;
    }

    public void setSrc7(String src7) {
        this.src7 = src7 == null ? null : src7.trim();
    }

    public String getSrc8() {
        return src8;
    }

    public void setSrc8(String src8) {
        this.src8 = src8 == null ? null : src8.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}