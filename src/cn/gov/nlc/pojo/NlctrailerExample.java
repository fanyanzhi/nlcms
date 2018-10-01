package cn.gov.nlc.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NlctrailerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NlctrailerExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTraileridIsNull() {
            addCriterion("trailerid is null");
            return (Criteria) this;
        }

        public Criteria andTraileridIsNotNull() {
            addCriterion("trailerid is not null");
            return (Criteria) this;
        }

        public Criteria andTraileridEqualTo(String value) {
            addCriterion("trailerid =", value, "trailerid");
            return (Criteria) this;
        }

        public Criteria andTraileridNotEqualTo(String value) {
            addCriterion("trailerid <>", value, "trailerid");
            return (Criteria) this;
        }

        public Criteria andTraileridGreaterThan(String value) {
            addCriterion("trailerid >", value, "trailerid");
            return (Criteria) this;
        }

        public Criteria andTraileridGreaterThanOrEqualTo(String value) {
            addCriterion("trailerid >=", value, "trailerid");
            return (Criteria) this;
        }

        public Criteria andTraileridLessThan(String value) {
            addCriterion("trailerid <", value, "trailerid");
            return (Criteria) this;
        }

        public Criteria andTraileridLessThanOrEqualTo(String value) {
            addCriterion("trailerid <=", value, "trailerid");
            return (Criteria) this;
        }

        public Criteria andTraileridLike(String value) {
            addCriterion("trailerid like", value, "trailerid");
            return (Criteria) this;
        }

        public Criteria andTraileridNotLike(String value) {
            addCriterion("trailerid not like", value, "trailerid");
            return (Criteria) this;
        }

        public Criteria andTraileridIn(List<String> values) {
            addCriterion("trailerid in", values, "trailerid");
            return (Criteria) this;
        }

        public Criteria andTraileridNotIn(List<String> values) {
            addCriterion("trailerid not in", values, "trailerid");
            return (Criteria) this;
        }

        public Criteria andTraileridBetween(String value1, String value2) {
            addCriterion("trailerid between", value1, value2, "trailerid");
            return (Criteria) this;
        }

        public Criteria andTraileridNotBetween(String value1, String value2) {
            addCriterion("trailerid not between", value1, value2, "trailerid");
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

        public Criteria andColumnidIsNull() {
            addCriterion("columnid is null");
            return (Criteria) this;
        }

        public Criteria andColumnidIsNotNull() {
            addCriterion("columnid is not null");
            return (Criteria) this;
        }

        public Criteria andColumnidEqualTo(String value) {
            addCriterion("columnid =", value, "columnid");
            return (Criteria) this;
        }

        public Criteria andColumnidNotEqualTo(String value) {
            addCriterion("columnid <>", value, "columnid");
            return (Criteria) this;
        }

        public Criteria andColumnidGreaterThan(String value) {
            addCriterion("columnid >", value, "columnid");
            return (Criteria) this;
        }

        public Criteria andColumnidGreaterThanOrEqualTo(String value) {
            addCriterion("columnid >=", value, "columnid");
            return (Criteria) this;
        }

        public Criteria andColumnidLessThan(String value) {
            addCriterion("columnid <", value, "columnid");
            return (Criteria) this;
        }

        public Criteria andColumnidLessThanOrEqualTo(String value) {
            addCriterion("columnid <=", value, "columnid");
            return (Criteria) this;
        }

        public Criteria andColumnidLike(String value) {
            addCriterion("columnid like", value, "columnid");
            return (Criteria) this;
        }

        public Criteria andColumnidNotLike(String value) {
            addCriterion("columnid not like", value, "columnid");
            return (Criteria) this;
        }

        public Criteria andColumnidIn(List<String> values) {
            addCriterion("columnid in", values, "columnid");
            return (Criteria) this;
        }

        public Criteria andColumnidNotIn(List<String> values) {
            addCriterion("columnid not in", values, "columnid");
            return (Criteria) this;
        }

        public Criteria andColumnidBetween(String value1, String value2) {
            addCriterion("columnid between", value1, value2, "columnid");
            return (Criteria) this;
        }

        public Criteria andColumnidNotBetween(String value1, String value2) {
            addCriterion("columnid not between", value1, value2, "columnid");
            return (Criteria) this;
        }

        public Criteria andColumnnameIsNull() {
            addCriterion("columnname is null");
            return (Criteria) this;
        }

        public Criteria andColumnnameIsNotNull() {
            addCriterion("columnname is not null");
            return (Criteria) this;
        }

        public Criteria andColumnnameEqualTo(String value) {
            addCriterion("columnname =", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameNotEqualTo(String value) {
            addCriterion("columnname <>", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameGreaterThan(String value) {
            addCriterion("columnname >", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameGreaterThanOrEqualTo(String value) {
            addCriterion("columnname >=", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameLessThan(String value) {
            addCriterion("columnname <", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameLessThanOrEqualTo(String value) {
            addCriterion("columnname <=", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameLike(String value) {
            addCriterion("columnname like", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameNotLike(String value) {
            addCriterion("columnname not like", value, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameIn(List<String> values) {
            addCriterion("columnname in", values, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameNotIn(List<String> values) {
            addCriterion("columnname not in", values, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameBetween(String value1, String value2) {
            addCriterion("columnname between", value1, value2, "columnname");
            return (Criteria) this;
        }

        public Criteria andColumnnameNotBetween(String value1, String value2) {
            addCriterion("columnname not between", value1, value2, "columnname");
            return (Criteria) this;
        }

        public Criteria andSpeakerIsNull() {
            addCriterion("speaker is null");
            return (Criteria) this;
        }

        public Criteria andSpeakerIsNotNull() {
            addCriterion("speaker is not null");
            return (Criteria) this;
        }

        public Criteria andSpeakerEqualTo(String value) {
            addCriterion("speaker =", value, "speaker");
            return (Criteria) this;
        }

        public Criteria andSpeakerNotEqualTo(String value) {
            addCriterion("speaker <>", value, "speaker");
            return (Criteria) this;
        }

        public Criteria andSpeakerGreaterThan(String value) {
            addCriterion("speaker >", value, "speaker");
            return (Criteria) this;
        }

        public Criteria andSpeakerGreaterThanOrEqualTo(String value) {
            addCriterion("speaker >=", value, "speaker");
            return (Criteria) this;
        }

        public Criteria andSpeakerLessThan(String value) {
            addCriterion("speaker <", value, "speaker");
            return (Criteria) this;
        }

        public Criteria andSpeakerLessThanOrEqualTo(String value) {
            addCriterion("speaker <=", value, "speaker");
            return (Criteria) this;
        }

        public Criteria andSpeakerLike(String value) {
            addCriterion("speaker like", value, "speaker");
            return (Criteria) this;
        }

        public Criteria andSpeakerNotLike(String value) {
            addCriterion("speaker not like", value, "speaker");
            return (Criteria) this;
        }

        public Criteria andSpeakerIn(List<String> values) {
            addCriterion("speaker in", values, "speaker");
            return (Criteria) this;
        }

        public Criteria andSpeakerNotIn(List<String> values) {
            addCriterion("speaker not in", values, "speaker");
            return (Criteria) this;
        }

        public Criteria andSpeakerBetween(String value1, String value2) {
            addCriterion("speaker between", value1, value2, "speaker");
            return (Criteria) this;
        }

        public Criteria andSpeakerNotBetween(String value1, String value2) {
            addCriterion("speaker not between", value1, value2, "speaker");
            return (Criteria) this;
        }

        public Criteria andPlaceIsNull() {
            addCriterion("place is null");
            return (Criteria) this;
        }

        public Criteria andPlaceIsNotNull() {
            addCriterion("place is not null");
            return (Criteria) this;
        }

        public Criteria andPlaceEqualTo(String value) {
            addCriterion("place =", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceNotEqualTo(String value) {
            addCriterion("place <>", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceGreaterThan(String value) {
            addCriterion("place >", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("place >=", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceLessThan(String value) {
            addCriterion("place <", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceLessThanOrEqualTo(String value) {
            addCriterion("place <=", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceLike(String value) {
            addCriterion("place like", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceNotLike(String value) {
            addCriterion("place not like", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceIn(List<String> values) {
            addCriterion("place in", values, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceNotIn(List<String> values) {
            addCriterion("place not in", values, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceBetween(String value1, String value2) {
            addCriterion("place between", value1, value2, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceNotBetween(String value1, String value2) {
            addCriterion("place not between", value1, value2, "place");
            return (Criteria) this;
        }

        public Criteria andSpeakernameIsNull() {
            addCriterion("speakername is null");
            return (Criteria) this;
        }

        public Criteria andSpeakernameIsNotNull() {
            addCriterion("speakername is not null");
            return (Criteria) this;
        }

        public Criteria andSpeakernameEqualTo(String value) {
            addCriterion("speakername =", value, "speakername");
            return (Criteria) this;
        }

        public Criteria andSpeakernameNotEqualTo(String value) {
            addCriterion("speakername <>", value, "speakername");
            return (Criteria) this;
        }

        public Criteria andSpeakernameGreaterThan(String value) {
            addCriterion("speakername >", value, "speakername");
            return (Criteria) this;
        }

        public Criteria andSpeakernameGreaterThanOrEqualTo(String value) {
            addCriterion("speakername >=", value, "speakername");
            return (Criteria) this;
        }

        public Criteria andSpeakernameLessThan(String value) {
            addCriterion("speakername <", value, "speakername");
            return (Criteria) this;
        }

        public Criteria andSpeakernameLessThanOrEqualTo(String value) {
            addCriterion("speakername <=", value, "speakername");
            return (Criteria) this;
        }

        public Criteria andSpeakernameLike(String value) {
            addCriterion("speakername like", value, "speakername");
            return (Criteria) this;
        }

        public Criteria andSpeakernameNotLike(String value) {
            addCriterion("speakername not like", value, "speakername");
            return (Criteria) this;
        }

        public Criteria andSpeakernameIn(List<String> values) {
            addCriterion("speakername in", values, "speakername");
            return (Criteria) this;
        }

        public Criteria andSpeakernameNotIn(List<String> values) {
            addCriterion("speakername not in", values, "speakername");
            return (Criteria) this;
        }

        public Criteria andSpeakernameBetween(String value1, String value2) {
            addCriterion("speakername between", value1, value2, "speakername");
            return (Criteria) this;
        }

        public Criteria andSpeakernameNotBetween(String value1, String value2) {
            addCriterion("speakername not between", value1, value2, "speakername");
            return (Criteria) this;
        }

        public Criteria andStarttimeIsNull() {
            addCriterion("starttime is null");
            return (Criteria) this;
        }

        public Criteria andStarttimeIsNotNull() {
            addCriterion("starttime is not null");
            return (Criteria) this;
        }

        public Criteria andStarttimeEqualTo(Date value) {
            addCriterion("starttime =", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeNotEqualTo(Date value) {
            addCriterion("starttime <>", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeGreaterThan(Date value) {
            addCriterion("starttime >", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("starttime >=", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeLessThan(Date value) {
            addCriterion("starttime <", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeLessThanOrEqualTo(Date value) {
            addCriterion("starttime <=", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeIn(List<Date> values) {
            addCriterion("starttime in", values, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeNotIn(List<Date> values) {
            addCriterion("starttime not in", values, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeBetween(Date value1, Date value2) {
            addCriterion("starttime between", value1, value2, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeNotBetween(Date value1, Date value2) {
            addCriterion("starttime not between", value1, value2, "starttime");
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

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Date value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Date value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Date value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Date value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Date value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Date> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Date> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Date value1, Date value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Date value1, Date value2) {
            addCriterion("time not between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andEndtimeIsNull() {
            addCriterion("endtime is null");
            return (Criteria) this;
        }

        public Criteria andEndtimeIsNotNull() {
            addCriterion("endtime is not null");
            return (Criteria) this;
        }

        public Criteria andEndtimeEqualTo(Date value) {
            addCriterion("endtime =", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotEqualTo(Date value) {
            addCriterion("endtime <>", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeGreaterThan(Date value) {
            addCriterion("endtime >", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("endtime >=", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeLessThan(Date value) {
            addCriterion("endtime <", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeLessThanOrEqualTo(Date value) {
            addCriterion("endtime <=", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeIn(List<Date> values) {
            addCriterion("endtime in", values, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotIn(List<Date> values) {
            addCriterion("endtime not in", values, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeBetween(Date value1, Date value2) {
            addCriterion("endtime between", value1, value2, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotBetween(Date value1, Date value2) {
            addCriterion("endtime not between", value1, value2, "endtime");
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

        public Criteria andGuanquIsNull() {
            addCriterion("guanqu is null");
            return (Criteria) this;
        }

        public Criteria andGuanquIsNotNull() {
            addCriterion("guanqu is not null");
            return (Criteria) this;
        }

        public Criteria andGuanquEqualTo(String value) {
            addCriterion("guanqu =", value, "guanqu");
            return (Criteria) this;
        }

        public Criteria andGuanquNotEqualTo(String value) {
            addCriterion("guanqu <>", value, "guanqu");
            return (Criteria) this;
        }

        public Criteria andGuanquGreaterThan(String value) {
            addCriterion("guanqu >", value, "guanqu");
            return (Criteria) this;
        }

        public Criteria andGuanquGreaterThanOrEqualTo(String value) {
            addCriterion("guanqu >=", value, "guanqu");
            return (Criteria) this;
        }

        public Criteria andGuanquLessThan(String value) {
            addCriterion("guanqu <", value, "guanqu");
            return (Criteria) this;
        }

        public Criteria andGuanquLessThanOrEqualTo(String value) {
            addCriterion("guanqu <=", value, "guanqu");
            return (Criteria) this;
        }

        public Criteria andGuanquLike(String value) {
            addCriterion("guanqu like", value, "guanqu");
            return (Criteria) this;
        }

        public Criteria andGuanquNotLike(String value) {
            addCriterion("guanqu not like", value, "guanqu");
            return (Criteria) this;
        }

        public Criteria andGuanquIn(List<String> values) {
            addCriterion("guanqu in", values, "guanqu");
            return (Criteria) this;
        }

        public Criteria andGuanquNotIn(List<String> values) {
            addCriterion("guanqu not in", values, "guanqu");
            return (Criteria) this;
        }

        public Criteria andGuanquBetween(String value1, String value2) {
            addCriterion("guanqu between", value1, value2, "guanqu");
            return (Criteria) this;
        }

        public Criteria andGuanquNotBetween(String value1, String value2) {
            addCriterion("guanqu not between", value1, value2, "guanqu");
            return (Criteria) this;
        }

        public Criteria andSpeaktimeIsNull() {
            addCriterion("speaktime is null");
            return (Criteria) this;
        }

        public Criteria andSpeaktimeIsNotNull() {
            addCriterion("speaktime is not null");
            return (Criteria) this;
        }

        public Criteria andSpeaktimeEqualTo(String value) {
            addCriterion("speaktime =", value, "speaktime");
            return (Criteria) this;
        }

        public Criteria andSpeaktimeNotEqualTo(String value) {
            addCriterion("speaktime <>", value, "speaktime");
            return (Criteria) this;
        }

        public Criteria andSpeaktimeGreaterThan(String value) {
            addCriterion("speaktime >", value, "speaktime");
            return (Criteria) this;
        }

        public Criteria andSpeaktimeGreaterThanOrEqualTo(String value) {
            addCriterion("speaktime >=", value, "speaktime");
            return (Criteria) this;
        }

        public Criteria andSpeaktimeLessThan(String value) {
            addCriterion("speaktime <", value, "speaktime");
            return (Criteria) this;
        }

        public Criteria andSpeaktimeLessThanOrEqualTo(String value) {
            addCriterion("speaktime <=", value, "speaktime");
            return (Criteria) this;
        }

        public Criteria andSpeaktimeLike(String value) {
            addCriterion("speaktime like", value, "speaktime");
            return (Criteria) this;
        }

        public Criteria andSpeaktimeNotLike(String value) {
            addCriterion("speaktime not like", value, "speaktime");
            return (Criteria) this;
        }

        public Criteria andSpeaktimeIn(List<String> values) {
            addCriterion("speaktime in", values, "speaktime");
            return (Criteria) this;
        }

        public Criteria andSpeaktimeNotIn(List<String> values) {
            addCriterion("speaktime not in", values, "speaktime");
            return (Criteria) this;
        }

        public Criteria andSpeaktimeBetween(String value1, String value2) {
            addCriterion("speaktime between", value1, value2, "speaktime");
            return (Criteria) this;
        }

        public Criteria andSpeaktimeNotBetween(String value1, String value2) {
            addCriterion("speaktime not between", value1, value2, "speaktime");
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