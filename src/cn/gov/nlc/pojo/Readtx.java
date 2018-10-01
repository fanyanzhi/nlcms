package cn.gov.nlc.pojo;

public class Readtx {
    private Integer id;

    private String magazineid;

    private String title;

    private String issue;

    private String thumbnail;

    private String thumbnailsmall;

    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMagazineid() {
        return magazineid;
    }

    public void setMagazineid(String magazineid) {
        this.magazineid = magazineid == null ? null : magazineid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue == null ? null : issue.trim();
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail == null ? null : thumbnail.trim();
    }

    public String getThumbnailsmall() {
        return thumbnailsmall;
    }

    public void setThumbnailsmall(String thumbnailsmall) {
        this.thumbnailsmall = thumbnailsmall == null ? null : thumbnailsmall.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }
}