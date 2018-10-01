package cn.gov.nlc.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NlcnoticeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NlcnoticeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andNoticeidIsNull() {
            addCriterion("noticeid is null");
            return (Criteria) this;
        }

        public Criteria andNoticeidIsNotNull() {
            addCriterion("noticeid is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeidEqualTo(String value) {
            addCriterion("noticeid =", value, "noticeid");
            return (Criteria) this;
        }

        public Criteria andNoticeidNotEqualTo(String value) {
            addCriterion("noticeid <>", value, "noticeid");
            return (Criteria) this;
        }

        public Criteria andNoticeidGreaterThan(String value) {
            addCriterion("noticeid >", value, "noticeid");
            return (Criteria) this;
        }

        public Criteria andNoticeidGreaterThanOrEqualTo(String value) {
            addCriterion("noticeid >=", value, "noticeid");
            return (Criteria) this;
        }

        public Criteria andNoticeidLessThan(String value) {
            addCriterion("noticeid <", value, "noticeid");
            return (Criteria) this;
        }

        public Criteria andNoticeidLessThanOrEqualTo(String value) {
            addCriterion("noticeid <=", value, "noticeid");
            return (Criteria) this;
        }

        public Criteria andNoticeidLike(String value) {
            addCriterion("noticeid like", value, "noticeid");
            return (Criteria) this;
        }

        public Criteria andNoticeidNotLike(String value) {
            addCriterion("noticeid not like", value, "noticeid");
            return (Criteria) this;
        }

        public Criteria andNoticeidIn(List<String> values) {
            addCriterion("noticeid in", values, "noticeid");
            return (Criteria) this;
        }

        public Criteria andNoticeidNotIn(List<String> values) {
            addCriterion("noticeid not in", values, "noticeid");
            return (Criteria) this;
        }

        public Criteria andNoticeidBetween(String value1, String value2) {
            addCriterion("noticeid between", value1, value2, "noticeid");
            return (Criteria) this;
        }

        public Criteria andNoticeidNotBetween(String value1, String value2) {
            addCriterion("noticeid not between", value1, value2, "noticeid");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andSubPersonIsNull() {
            addCriterion("sub_person is null");
            return (Criteria) this;
        }

        public Criteria andSubPersonIsNotNull() {
            addCriterion("sub_person is not null");
            return (Criteria) this;
        }

        public Criteria andSubPersonEqualTo(String value) {
            addCriterion("sub_person =", value, "subPerson");
            return (Criteria) this;
        }

        public Criteria andSubPersonNotEqualTo(String value) {
            addCriterion("sub_person <>", value, "subPerson");
            return (Criteria) this;
        }

        public Criteria andSubPersonGreaterThan(String value) {
            addCriterion("sub_person >", value, "subPerson");
            return (Criteria) this;
        }

        public Criteria andSubPersonGreaterThanOrEqualTo(String value) {
            addCriterion("sub_person >=", value, "subPerson");
            return (Criteria) this;
        }

        public Criteria andSubPersonLessThan(String value) {
            addCriterion("sub_person <", value, "subPerson");
            return (Criteria) this;
        }

        public Criteria andSubPersonLessThanOrEqualTo(String value) {
            addCriterion("sub_person <=", value, "subPerson");
            return (Criteria) this;
        }

        public Criteria andSubPersonLike(String value) {
            addCriterion("sub_person like", value, "subPerson");
            return (Criteria) this;
        }

        public Criteria andSubPersonNotLike(String value) {
            addCriterion("sub_person not like", value, "subPerson");
            return (Criteria) this;
        }

        public Criteria andSubPersonIn(List<String> values) {
            addCriterion("sub_person in", values, "subPerson");
            return (Criteria) this;
        }

        public Criteria andSubPersonNotIn(List<String> values) {
            addCriterion("sub_person not in", values, "subPerson");
            return (Criteria) this;
        }

        public Criteria andSubPersonBetween(String value1, String value2) {
            addCriterion("sub_person between", value1, value2, "subPerson");
            return (Criteria) this;
        }

        public Criteria andSubPersonNotBetween(String value1, String value2) {
            addCriterion("sub_person not between", value1, value2, "subPerson");
            return (Criteria) this;
        }

        public Criteria andSubTimeIsNull() {
            addCriterion("sub_time is null");
            return (Criteria) this;
        }

        public Criteria andSubTimeIsNotNull() {
            addCriterion("sub_time is not null");
            return (Criteria) this;
        }

        public Criteria andSubTimeEqualTo(Date value) {
            addCriterion("sub_time =", value, "subTime");
            return (Criteria) this;
        }

        public Criteria andSubTimeNotEqualTo(Date value) {
            addCriterion("sub_time <>", value, "subTime");
            return (Criteria) this;
        }

        public Criteria andSubTimeGreaterThan(Date value) {
            addCriterion("sub_time >", value, "subTime");
            return (Criteria) this;
        }

        public Criteria andSubTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sub_time >=", value, "subTime");
            return (Criteria) this;
        }

        public Criteria andSubTimeLessThan(Date value) {
            addCriterion("sub_time <", value, "subTime");
            return (Criteria) this;
        }

        public Criteria andSubTimeLessThanOrEqualTo(Date value) {
            addCriterion("sub_time <=", value, "subTime");
            return (Criteria) this;
        }

        public Criteria andSubTimeIn(List<Date> values) {
            addCriterion("sub_time in", values, "subTime");
            return (Criteria) this;
        }

        public Criteria andSubTimeNotIn(List<Date> values) {
            addCriterion("sub_time not in", values, "subTime");
            return (Criteria) this;
        }

        public Criteria andSubTimeBetween(Date value1, Date value2) {
            addCriterion("sub_time between", value1, value2, "subTime");
            return (Criteria) this;
        }

        public Criteria andSubTimeNotBetween(Date value1, Date value2) {
            addCriterion("sub_time not between", value1, value2, "subTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeIsNull() {
            addCriterion("pub_time is null");
            return (Criteria) this;
        }

        public Criteria andPubTimeIsNotNull() {
            addCriterion("pub_time is not null");
            return (Criteria) this;
        }

        public Criteria andPubTimeEqualTo(Date value) {
            addCriterion("pub_time =", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeNotEqualTo(Date value) {
            addCriterion("pub_time <>", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeGreaterThan(Date value) {
            addCriterion("pub_time >", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pub_time >=", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeLessThan(Date value) {
            addCriterion("pub_time <", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeLessThanOrEqualTo(Date value) {
            addCriterion("pub_time <=", value, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeIn(List<Date> values) {
            addCriterion("pub_time in", values, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeNotIn(List<Date> values) {
            addCriterion("pub_time not in", values, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeBetween(Date value1, Date value2) {
            addCriterion("pub_time between", value1, value2, "pubTime");
            return (Criteria) this;
        }

        public Criteria andPubTimeNotBetween(Date value1, Date value2) {
            addCriterion("pub_time not between", value1, value2, "pubTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeIsNull() {
            addCriterion("upd_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdTimeIsNotNull() {
            addCriterion("upd_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdTimeEqualTo(Date value) {
            addCriterion("upd_time =", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeNotEqualTo(Date value) {
            addCriterion("upd_time <>", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeGreaterThan(Date value) {
            addCriterion("upd_time >", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("upd_time >=", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeLessThan(Date value) {
            addCriterion("upd_time <", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeLessThanOrEqualTo(Date value) {
            addCriterion("upd_time <=", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeIn(List<Date> values) {
            addCriterion("upd_time in", values, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeNotIn(List<Date> values) {
            addCriterion("upd_time not in", values, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeBetween(Date value1, Date value2) {
            addCriterion("upd_time between", value1, value2, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeNotBetween(Date value1, Date value2) {
            addCriterion("upd_time not between", value1, value2, "updTime");
            return (Criteria) this;
        }

        public Criteria andBkpbtimeIsNull() {
            addCriterion("bkpbtime is null");
            return (Criteria) this;
        }

        public Criteria andBkpbtimeIsNotNull() {
            addCriterion("bkpbtime is not null");
            return (Criteria) this;
        }

        public Criteria andBkpbtimeEqualTo(Date value) {
            addCriterion("bkpbtime =", value, "bkpbtime");
            return (Criteria) this;
        }

        public Criteria andBkpbtimeNotEqualTo(Date value) {
            addCriterion("bkpbtime <>", value, "bkpbtime");
            return (Criteria) this;
        }

        public Criteria andBkpbtimeGreaterThan(Date value) {
            addCriterion("bkpbtime >", value, "bkpbtime");
            return (Criteria) this;
        }

        public Criteria andBkpbtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("bkpbtime >=", value, "bkpbtime");
            return (Criteria) this;
        }

        public Criteria andBkpbtimeLessThan(Date value) {
            addCriterion("bkpbtime <", value, "bkpbtime");
            return (Criteria) this;
        }

        public Criteria andBkpbtimeLessThanOrEqualTo(Date value) {
            addCriterion("bkpbtime <=", value, "bkpbtime");
            return (Criteria) this;
        }

        public Criteria andBkpbtimeIn(List<Date> values) {
            addCriterion("bkpbtime in", values, "bkpbtime");
            return (Criteria) this;
        }

        public Criteria andBkpbtimeNotIn(List<Date> values) {
            addCriterion("bkpbtime not in", values, "bkpbtime");
            return (Criteria) this;
        }

        public Criteria andBkpbtimeBetween(Date value1, Date value2) {
            addCriterion("bkpbtime between", value1, value2, "bkpbtime");
            return (Criteria) this;
        }

        public Criteria andBkpbtimeNotBetween(Date value1, Date value2) {
            addCriterion("bkpbtime not between", value1, value2, "bkpbtime");
            return (Criteria) this;
        }

        public Criteria andSrcIsNull() {
            addCriterion("src is null");
            return (Criteria) this;
        }

        public Criteria andSrcIsNotNull() {
            addCriterion("src is not null");
            return (Criteria) this;
        }

        public Criteria andSrcEqualTo(String value) {
            addCriterion("src =", value, "src");
            return (Criteria) this;
        }

        public Criteria andSrcNotEqualTo(String value) {
            addCriterion("src <>", value, "src");
            return (Criteria) this;
        }

        public Criteria andSrcGreaterThan(String value) {
            addCriterion("src >", value, "src");
            return (Criteria) this;
        }

        public Criteria andSrcGreaterThanOrEqualTo(String value) {
            addCriterion("src >=", value, "src");
            return (Criteria) this;
        }

        public Criteria andSrcLessThan(String value) {
            addCriterion("src <", value, "src");
            return (Criteria) this;
        }

        public Criteria andSrcLessThanOrEqualTo(String value) {
            addCriterion("src <=", value, "src");
            return (Criteria) this;
        }

        public Criteria andSrcLike(String value) {
            addCriterion("src like", value, "src");
            return (Criteria) this;
        }

        public Criteria andSrcNotLike(String value) {
            addCriterion("src not like", value, "src");
            return (Criteria) this;
        }

        public Criteria andSrcIn(List<String> values) {
            addCriterion("src in", values, "src");
            return (Criteria) this;
        }

        public Criteria andSrcNotIn(List<String> values) {
            addCriterion("src not in", values, "src");
            return (Criteria) this;
        }

        public Criteria andSrcBetween(String value1, String value2) {
            addCriterion("src between", value1, value2, "src");
            return (Criteria) this;
        }

        public Criteria andSrcNotBetween(String value1, String value2) {
            addCriterion("src not between", value1, value2, "src");
            return (Criteria) this;
        }

        public Criteria andBoardIsNull() {
            addCriterion("board is null");
            return (Criteria) this;
        }

        public Criteria andBoardIsNotNull() {
            addCriterion("board is not null");
            return (Criteria) this;
        }

        public Criteria andBoardEqualTo(String value) {
            addCriterion("board =", value, "board");
            return (Criteria) this;
        }

        public Criteria andBoardNotEqualTo(String value) {
            addCriterion("board <>", value, "board");
            return (Criteria) this;
        }

        public Criteria andBoardGreaterThan(String value) {
            addCriterion("board >", value, "board");
            return (Criteria) this;
        }

        public Criteria andBoardGreaterThanOrEqualTo(String value) {
            addCriterion("board >=", value, "board");
            return (Criteria) this;
        }

        public Criteria andBoardLessThan(String value) {
            addCriterion("board <", value, "board");
            return (Criteria) this;
        }

        public Criteria andBoardLessThanOrEqualTo(String value) {
            addCriterion("board <=", value, "board");
            return (Criteria) this;
        }

        public Criteria andBoardLike(String value) {
            addCriterion("board like", value, "board");
            return (Criteria) this;
        }

        public Criteria andBoardNotLike(String value) {
            addCriterion("board not like", value, "board");
            return (Criteria) this;
        }

        public Criteria andBoardIn(List<String> values) {
            addCriterion("board in", values, "board");
            return (Criteria) this;
        }

        public Criteria andBoardNotIn(List<String> values) {
            addCriterion("board not in", values, "board");
            return (Criteria) this;
        }

        public Criteria andBoardBetween(String value1, String value2) {
            addCriterion("board between", value1, value2, "board");
            return (Criteria) this;
        }

        public Criteria andBoardNotBetween(String value1, String value2) {
            addCriterion("board not between", value1, value2, "board");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(String value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(String value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(String value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(String value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(String value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(String value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLike(String value) {
            addCriterion("source like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotLike(String value) {
            addCriterion("source not like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<String> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<String> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(String value1, String value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(String value1, String value2) {
            addCriterion("source not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andAnnxurlIsNull() {
            addCriterion("annxurl is null");
            return (Criteria) this;
        }

        public Criteria andAnnxurlIsNotNull() {
            addCriterion("annxurl is not null");
            return (Criteria) this;
        }

        public Criteria andAnnxurlEqualTo(String value) {
            addCriterion("annxurl =", value, "annxurl");
            return (Criteria) this;
        }

        public Criteria andAnnxurlNotEqualTo(String value) {
            addCriterion("annxurl <>", value, "annxurl");
            return (Criteria) this;
        }

        public Criteria andAnnxurlGreaterThan(String value) {
            addCriterion("annxurl >", value, "annxurl");
            return (Criteria) this;
        }

        public Criteria andAnnxurlGreaterThanOrEqualTo(String value) {
            addCriterion("annxurl >=", value, "annxurl");
            return (Criteria) this;
        }

        public Criteria andAnnxurlLessThan(String value) {
            addCriterion("annxurl <", value, "annxurl");
            return (Criteria) this;
        }

        public Criteria andAnnxurlLessThanOrEqualTo(String value) {
            addCriterion("annxurl <=", value, "annxurl");
            return (Criteria) this;
        }

        public Criteria andAnnxurlLike(String value) {
            addCriterion("annxurl like", value, "annxurl");
            return (Criteria) this;
        }

        public Criteria andAnnxurlNotLike(String value) {
            addCriterion("annxurl not like", value, "annxurl");
            return (Criteria) this;
        }

        public Criteria andAnnxurlIn(List<String> values) {
            addCriterion("annxurl in", values, "annxurl");
            return (Criteria) this;
        }

        public Criteria andAnnxurlNotIn(List<String> values) {
            addCriterion("annxurl not in", values, "annxurl");
            return (Criteria) this;
        }

        public Criteria andAnnxurlBetween(String value1, String value2) {
            addCriterion("annxurl between", value1, value2, "annxurl");
            return (Criteria) this;
        }

        public Criteria andAnnxurlNotBetween(String value1, String value2) {
            addCriterion("annxurl not between", value1, value2, "annxurl");
            return (Criteria) this;
        }

        public Criteria andTitlesrcIsNull() {
            addCriterion("titlesrc is null");
            return (Criteria) this;
        }

        public Criteria andTitlesrcIsNotNull() {
            addCriterion("titlesrc is not null");
            return (Criteria) this;
        }

        public Criteria andTitlesrcEqualTo(String value) {
            addCriterion("titlesrc =", value, "titlesrc");
            return (Criteria) this;
        }

        public Criteria andTitlesrcNotEqualTo(String value) {
            addCriterion("titlesrc <>", value, "titlesrc");
            return (Criteria) this;
        }

        public Criteria andTitlesrcGreaterThan(String value) {
            addCriterion("titlesrc >", value, "titlesrc");
            return (Criteria) this;
        }

        public Criteria andTitlesrcGreaterThanOrEqualTo(String value) {
            addCriterion("titlesrc >=", value, "titlesrc");
            return (Criteria) this;
        }

        public Criteria andTitlesrcLessThan(String value) {
            addCriterion("titlesrc <", value, "titlesrc");
            return (Criteria) this;
        }

        public Criteria andTitlesrcLessThanOrEqualTo(String value) {
            addCriterion("titlesrc <=", value, "titlesrc");
            return (Criteria) this;
        }

        public Criteria andTitlesrcLike(String value) {
            addCriterion("titlesrc like", value, "titlesrc");
            return (Criteria) this;
        }

        public Criteria andTitlesrcNotLike(String value) {
            addCriterion("titlesrc not like", value, "titlesrc");
            return (Criteria) this;
        }

        public Criteria andTitlesrcIn(List<String> values) {
            addCriterion("titlesrc in", values, "titlesrc");
            return (Criteria) this;
        }

        public Criteria andTitlesrcNotIn(List<String> values) {
            addCriterion("titlesrc not in", values, "titlesrc");
            return (Criteria) this;
        }

        public Criteria andTitlesrcBetween(String value1, String value2) {
            addCriterion("titlesrc between", value1, value2, "titlesrc");
            return (Criteria) this;
        }

        public Criteria andTitlesrcNotBetween(String value1, String value2) {
            addCriterion("titlesrc not between", value1, value2, "titlesrc");
            return (Criteria) this;
        }

        public Criteria andPraisecountIsNull() {
            addCriterion("praisecount is null");
            return (Criteria) this;
        }

        public Criteria andPraisecountIsNotNull() {
            addCriterion("praisecount is not null");
            return (Criteria) this;
        }

        public Criteria andPraisecountEqualTo(Integer value) {
            addCriterion("praisecount =", value, "praisecount");
            return (Criteria) this;
        }

        public Criteria andPraisecountNotEqualTo(Integer value) {
            addCriterion("praisecount <>", value, "praisecount");
            return (Criteria) this;
        }

        public Criteria andPraisecountGreaterThan(Integer value) {
            addCriterion("praisecount >", value, "praisecount");
            return (Criteria) this;
        }

        public Criteria andPraisecountGreaterThanOrEqualTo(Integer value) {
            addCriterion("praisecount >=", value, "praisecount");
            return (Criteria) this;
        }

        public Criteria andPraisecountLessThan(Integer value) {
            addCriterion("praisecount <", value, "praisecount");
            return (Criteria) this;
        }

        public Criteria andPraisecountLessThanOrEqualTo(Integer value) {
            addCriterion("praisecount <=", value, "praisecount");
            return (Criteria) this;
        }

        public Criteria andPraisecountIn(List<Integer> values) {
            addCriterion("praisecount in", values, "praisecount");
            return (Criteria) this;
        }

        public Criteria andPraisecountNotIn(List<Integer> values) {
            addCriterion("praisecount not in", values, "praisecount");
            return (Criteria) this;
        }

        public Criteria andPraisecountBetween(Integer value1, Integer value2) {
            addCriterion("praisecount between", value1, value2, "praisecount");
            return (Criteria) this;
        }

        public Criteria andPraisecountNotBetween(Integer value1, Integer value2) {
            addCriterion("praisecount not between", value1, value2, "praisecount");
            return (Criteria) this;
        }

        public Criteria andCollectcountIsNull() {
            addCriterion("collectcount is null");
            return (Criteria) this;
        }

        public Criteria andCollectcountIsNotNull() {
            addCriterion("collectcount is not null");
            return (Criteria) this;
        }

        public Criteria andCollectcountEqualTo(Integer value) {
            addCriterion("collectcount =", value, "collectcount");
            return (Criteria) this;
        }

        public Criteria andCollectcountNotEqualTo(Integer value) {
            addCriterion("collectcount <>", value, "collectcount");
            return (Criteria) this;
        }

        public Criteria andCollectcountGreaterThan(Integer value) {
            addCriterion("collectcount >", value, "collectcount");
            return (Criteria) this;
        }

        public Criteria andCollectcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("collectcount >=", value, "collectcount");
            return (Criteria) this;
        }

        public Criteria andCollectcountLessThan(Integer value) {
            addCriterion("collectcount <", value, "collectcount");
            return (Criteria) this;
        }

        public Criteria andCollectcountLessThanOrEqualTo(Integer value) {
            addCriterion("collectcount <=", value, "collectcount");
            return (Criteria) this;
        }

        public Criteria andCollectcountIn(List<Integer> values) {
            addCriterion("collectcount in", values, "collectcount");
            return (Criteria) this;
        }

        public Criteria andCollectcountNotIn(List<Integer> values) {
            addCriterion("collectcount not in", values, "collectcount");
            return (Criteria) this;
        }

        public Criteria andCollectcountBetween(Integer value1, Integer value2) {
            addCriterion("collectcount between", value1, value2, "collectcount");
            return (Criteria) this;
        }

        public Criteria andCollectcountNotBetween(Integer value1, Integer value2) {
            addCriterion("collectcount not between", value1, value2, "collectcount");
            return (Criteria) this;
        }

        public Criteria andPushmethodIsNull() {
            addCriterion("pushmethod is null");
            return (Criteria) this;
        }

        public Criteria andPushmethodIsNotNull() {
            addCriterion("pushmethod is not null");
            return (Criteria) this;
        }

        public Criteria andPushmethodEqualTo(String value) {
            addCriterion("pushmethod =", value, "pushmethod");
            return (Criteria) this;
        }

        public Criteria andPushmethodNotEqualTo(String value) {
            addCriterion("pushmethod <>", value, "pushmethod");
            return (Criteria) this;
        }

        public Criteria andPushmethodGreaterThan(String value) {
            addCriterion("pushmethod >", value, "pushmethod");
            return (Criteria) this;
        }

        public Criteria andPushmethodGreaterThanOrEqualTo(String value) {
            addCriterion("pushmethod >=", value, "pushmethod");
            return (Criteria) this;
        }

        public Criteria andPushmethodLessThan(String value) {
            addCriterion("pushmethod <", value, "pushmethod");
            return (Criteria) this;
        }

        public Criteria andPushmethodLessThanOrEqualTo(String value) {
            addCriterion("pushmethod <=", value, "pushmethod");
            return (Criteria) this;
        }

        public Criteria andPushmethodLike(String value) {
            addCriterion("pushmethod like", value, "pushmethod");
            return (Criteria) this;
        }

        public Criteria andPushmethodNotLike(String value) {
            addCriterion("pushmethod not like", value, "pushmethod");
            return (Criteria) this;
        }

        public Criteria andPushmethodIn(List<String> values) {
            addCriterion("pushmethod in", values, "pushmethod");
            return (Criteria) this;
        }

        public Criteria andPushmethodNotIn(List<String> values) {
            addCriterion("pushmethod not in", values, "pushmethod");
            return (Criteria) this;
        }

        public Criteria andPushmethodBetween(String value1, String value2) {
            addCriterion("pushmethod between", value1, value2, "pushmethod");
            return (Criteria) this;
        }

        public Criteria andPushmethodNotBetween(String value1, String value2) {
            addCriterion("pushmethod not between", value1, value2, "pushmethod");
            return (Criteria) this;
        }

        public Criteria andTopsIsNull() {
            addCriterion("tops is null");
            return (Criteria) this;
        }

        public Criteria andTopsIsNotNull() {
            addCriterion("tops is not null");
            return (Criteria) this;
        }

        public Criteria andTopsEqualTo(Integer value) {
            addCriterion("tops =", value, "tops");
            return (Criteria) this;
        }

        public Criteria andTopsNotEqualTo(Integer value) {
            addCriterion("tops <>", value, "tops");
            return (Criteria) this;
        }

        public Criteria andTopsGreaterThan(Integer value) {
            addCriterion("tops >", value, "tops");
            return (Criteria) this;
        }

        public Criteria andTopsGreaterThanOrEqualTo(Integer value) {
            addCriterion("tops >=", value, "tops");
            return (Criteria) this;
        }

        public Criteria andTopsLessThan(Integer value) {
            addCriterion("tops <", value, "tops");
            return (Criteria) this;
        }

        public Criteria andTopsLessThanOrEqualTo(Integer value) {
            addCriterion("tops <=", value, "tops");
            return (Criteria) this;
        }

        public Criteria andTopsIn(List<Integer> values) {
            addCriterion("tops in", values, "tops");
            return (Criteria) this;
        }

        public Criteria andTopsNotIn(List<Integer> values) {
            addCriterion("tops not in", values, "tops");
            return (Criteria) this;
        }

        public Criteria andTopsBetween(Integer value1, Integer value2) {
            addCriterion("tops between", value1, value2, "tops");
            return (Criteria) this;
        }

        public Criteria andTopsNotBetween(Integer value1, Integer value2) {
            addCriterion("tops not between", value1, value2, "tops");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}