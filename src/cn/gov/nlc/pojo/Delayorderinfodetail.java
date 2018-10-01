package cn.gov.nlc.pojo;

import java.util.Date;

public class Delayorderinfodetail {
    private Integer id;

    private String orderno;

    private String z31Sequence;

    private String book;

    private String amount;

    private Date dueDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getZ31Sequence() {
        return z31Sequence;
    }

    public void setZ31Sequence(String z31Sequence) {
        this.z31Sequence = z31Sequence == null ? null : z31Sequence.trim();
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book == null ? null : book.trim();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount == null ? null : amount.trim();
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}