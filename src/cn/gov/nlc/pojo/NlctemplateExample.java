package cn.gov.nlc.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NlctemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NlctemplateExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
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

        public Criteria andDatepicIsNull() {
            addCriterion("datepic is null");
            return (Criteria) this;
        }

        public Criteria andDatepicIsNotNull() {
            addCriterion("datepic is not null");
            return (Criteria) this;
        }

        public Criteria andDatepicEqualTo(String value) {
            addCriterion("datepic =", value, "datepic");
            return (Criteria) this;
        }

        public Criteria andDatepicNotEqualTo(String value) {
            addCriterion("datepic <>", value, "datepic");
            return (Criteria) this;
        }

        public Criteria andDatepicGreaterThan(String value) {
            addCriterion("datepic >", value, "datepic");
            return (Criteria) this;
        }

        public Criteria andDatepicGreaterThanOrEqualTo(String value) {
            addCriterion("datepic >=", value, "datepic");
            return (Criteria) this;
        }

        public Criteria andDatepicLessThan(String value) {
            addCriterion("datepic <", value, "datepic");
            return (Criteria) this;
        }

        public Criteria andDatepicLessThanOrEqualTo(String value) {
            addCriterion("datepic <=", value, "datepic");
            return (Criteria) this;
        }

        public Criteria andDatepicLike(String value) {
            addCriterion("datepic like", value, "datepic");
            return (Criteria) this;
        }

        public Criteria andDatepicNotLike(String value) {
            addCriterion("datepic not like", value, "datepic");
            return (Criteria) this;
        }

        public Criteria andDatepicIn(List<String> values) {
            addCriterion("datepic in", values, "datepic");
            return (Criteria) this;
        }

        public Criteria andDatepicNotIn(List<String> values) {
            addCriterion("datepic not in", values, "datepic");
            return (Criteria) this;
        }

        public Criteria andDatepicBetween(String value1, String value2) {
            addCriterion("datepic between", value1, value2, "datepic");
            return (Criteria) this;
        }

        public Criteria andDatepicNotBetween(String value1, String value2) {
            addCriterion("datepic not between", value1, value2, "datepic");
            return (Criteria) this;
        }

        public Criteria andPoempicIsNull() {
            addCriterion("poempic is null");
            return (Criteria) this;
        }

        public Criteria andPoempicIsNotNull() {
            addCriterion("poempic is not null");
            return (Criteria) this;
        }

        public Criteria andPoempicEqualTo(String value) {
            addCriterion("poempic =", value, "poempic");
            return (Criteria) this;
        }

        public Criteria andPoempicNotEqualTo(String value) {
            addCriterion("poempic <>", value, "poempic");
            return (Criteria) this;
        }

        public Criteria andPoempicGreaterThan(String value) {
            addCriterion("poempic >", value, "poempic");
            return (Criteria) this;
        }

        public Criteria andPoempicGreaterThanOrEqualTo(String value) {
            addCriterion("poempic >=", value, "poempic");
            return (Criteria) this;
        }

        public Criteria andPoempicLessThan(String value) {
            addCriterion("poempic <", value, "poempic");
            return (Criteria) this;
        }

        public Criteria andPoempicLessThanOrEqualTo(String value) {
            addCriterion("poempic <=", value, "poempic");
            return (Criteria) this;
        }

        public Criteria andPoempicLike(String value) {
            addCriterion("poempic like", value, "poempic");
            return (Criteria) this;
        }

        public Criteria andPoempicNotLike(String value) {
            addCriterion("poempic not like", value, "poempic");
            return (Criteria) this;
        }

        public Criteria andPoempicIn(List<String> values) {
            addCriterion("poempic in", values, "poempic");
            return (Criteria) this;
        }

        public Criteria andPoempicNotIn(List<String> values) {
            addCriterion("poempic not in", values, "poempic");
            return (Criteria) this;
        }

        public Criteria andPoempicBetween(String value1, String value2) {
            addCriterion("poempic between", value1, value2, "poempic");
            return (Criteria) this;
        }

        public Criteria andPoempicNotBetween(String value1, String value2) {
            addCriterion("poempic not between", value1, value2, "poempic");
            return (Criteria) this;
        }

        public Criteria andBackpicIsNull() {
            addCriterion("backpic is null");
            return (Criteria) this;
        }

        public Criteria andBackpicIsNotNull() {
            addCriterion("backpic is not null");
            return (Criteria) this;
        }

        public Criteria andBackpicEqualTo(String value) {
            addCriterion("backpic =", value, "backpic");
            return (Criteria) this;
        }

        public Criteria andBackpicNotEqualTo(String value) {
            addCriterion("backpic <>", value, "backpic");
            return (Criteria) this;
        }

        public Criteria andBackpicGreaterThan(String value) {
            addCriterion("backpic >", value, "backpic");
            return (Criteria) this;
        }

        public Criteria andBackpicGreaterThanOrEqualTo(String value) {
            addCriterion("backpic >=", value, "backpic");
            return (Criteria) this;
        }

        public Criteria andBackpicLessThan(String value) {
            addCriterion("backpic <", value, "backpic");
            return (Criteria) this;
        }

        public Criteria andBackpicLessThanOrEqualTo(String value) {
            addCriterion("backpic <=", value, "backpic");
            return (Criteria) this;
        }

        public Criteria andBackpicLike(String value) {
            addCriterion("backpic like", value, "backpic");
            return (Criteria) this;
        }

        public Criteria andBackpicNotLike(String value) {
            addCriterion("backpic not like", value, "backpic");
            return (Criteria) this;
        }

        public Criteria andBackpicIn(List<String> values) {
            addCriterion("backpic in", values, "backpic");
            return (Criteria) this;
        }

        public Criteria andBackpicNotIn(List<String> values) {
            addCriterion("backpic not in", values, "backpic");
            return (Criteria) this;
        }

        public Criteria andBackpicBetween(String value1, String value2) {
            addCriterion("backpic between", value1, value2, "backpic");
            return (Criteria) this;
        }

        public Criteria andBackpicNotBetween(String value1, String value2) {
            addCriterion("backpic not between", value1, value2, "backpic");
            return (Criteria) this;
        }

        public Criteria andMottopicIsNull() {
            addCriterion("mottopic is null");
            return (Criteria) this;
        }

        public Criteria andMottopicIsNotNull() {
            addCriterion("mottopic is not null");
            return (Criteria) this;
        }

        public Criteria andMottopicEqualTo(String value) {
            addCriterion("mottopic =", value, "mottopic");
            return (Criteria) this;
        }

        public Criteria andMottopicNotEqualTo(String value) {
            addCriterion("mottopic <>", value, "mottopic");
            return (Criteria) this;
        }

        public Criteria andMottopicGreaterThan(String value) {
            addCriterion("mottopic >", value, "mottopic");
            return (Criteria) this;
        }

        public Criteria andMottopicGreaterThanOrEqualTo(String value) {
            addCriterion("mottopic >=", value, "mottopic");
            return (Criteria) this;
        }

        public Criteria andMottopicLessThan(String value) {
            addCriterion("mottopic <", value, "mottopic");
            return (Criteria) this;
        }

        public Criteria andMottopicLessThanOrEqualTo(String value) {
            addCriterion("mottopic <=", value, "mottopic");
            return (Criteria) this;
        }

        public Criteria andMottopicLike(String value) {
            addCriterion("mottopic like", value, "mottopic");
            return (Criteria) this;
        }

        public Criteria andMottopicNotLike(String value) {
            addCriterion("mottopic not like", value, "mottopic");
            return (Criteria) this;
        }

        public Criteria andMottopicIn(List<String> values) {
            addCriterion("mottopic in", values, "mottopic");
            return (Criteria) this;
        }

        public Criteria andMottopicNotIn(List<String> values) {
            addCriterion("mottopic not in", values, "mottopic");
            return (Criteria) this;
        }

        public Criteria andMottopicBetween(String value1, String value2) {
            addCriterion("mottopic between", value1, value2, "mottopic");
            return (Criteria) this;
        }

        public Criteria andMottopicNotBetween(String value1, String value2) {
            addCriterion("mottopic not between", value1, value2, "mottopic");
            return (Criteria) this;
        }

        public Criteria andSeparatorpicIsNull() {
            addCriterion("separatorpic is null");
            return (Criteria) this;
        }

        public Criteria andSeparatorpicIsNotNull() {
            addCriterion("separatorpic is not null");
            return (Criteria) this;
        }

        public Criteria andSeparatorpicEqualTo(String value) {
            addCriterion("separatorpic =", value, "separatorpic");
            return (Criteria) this;
        }

        public Criteria andSeparatorpicNotEqualTo(String value) {
            addCriterion("separatorpic <>", value, "separatorpic");
            return (Criteria) this;
        }

        public Criteria andSeparatorpicGreaterThan(String value) {
            addCriterion("separatorpic >", value, "separatorpic");
            return (Criteria) this;
        }

        public Criteria andSeparatorpicGreaterThanOrEqualTo(String value) {
            addCriterion("separatorpic >=", value, "separatorpic");
            return (Criteria) this;
        }

        public Criteria andSeparatorpicLessThan(String value) {
            addCriterion("separatorpic <", value, "separatorpic");
            return (Criteria) this;
        }

        public Criteria andSeparatorpicLessThanOrEqualTo(String value) {
            addCriterion("separatorpic <=", value, "separatorpic");
            return (Criteria) this;
        }

        public Criteria andSeparatorpicLike(String value) {
            addCriterion("separatorpic like", value, "separatorpic");
            return (Criteria) this;
        }

        public Criteria andSeparatorpicNotLike(String value) {
            addCriterion("separatorpic not like", value, "separatorpic");
            return (Criteria) this;
        }

        public Criteria andSeparatorpicIn(List<String> values) {
            addCriterion("separatorpic in", values, "separatorpic");
            return (Criteria) this;
        }

        public Criteria andSeparatorpicNotIn(List<String> values) {
            addCriterion("separatorpic not in", values, "separatorpic");
            return (Criteria) this;
        }

        public Criteria andSeparatorpicBetween(String value1, String value2) {
            addCriterion("separatorpic between", value1, value2, "separatorpic");
            return (Criteria) this;
        }

        public Criteria andSeparatorpicNotBetween(String value1, String value2) {
            addCriterion("separatorpic not between", value1, value2, "separatorpic");
            return (Criteria) this;
        }

        public Criteria andPoemsreturnpicIsNull() {
            addCriterion("poemsreturnpic is null");
            return (Criteria) this;
        }

        public Criteria andPoemsreturnpicIsNotNull() {
            addCriterion("poemsreturnpic is not null");
            return (Criteria) this;
        }

        public Criteria andPoemsreturnpicEqualTo(String value) {
            addCriterion("poemsreturnpic =", value, "poemsreturnpic");
            return (Criteria) this;
        }

        public Criteria andPoemsreturnpicNotEqualTo(String value) {
            addCriterion("poemsreturnpic <>", value, "poemsreturnpic");
            return (Criteria) this;
        }

        public Criteria andPoemsreturnpicGreaterThan(String value) {
            addCriterion("poemsreturnpic >", value, "poemsreturnpic");
            return (Criteria) this;
        }

        public Criteria andPoemsreturnpicGreaterThanOrEqualTo(String value) {
            addCriterion("poemsreturnpic >=", value, "poemsreturnpic");
            return (Criteria) this;
        }

        public Criteria andPoemsreturnpicLessThan(String value) {
            addCriterion("poemsreturnpic <", value, "poemsreturnpic");
            return (Criteria) this;
        }

        public Criteria andPoemsreturnpicLessThanOrEqualTo(String value) {
            addCriterion("poemsreturnpic <=", value, "poemsreturnpic");
            return (Criteria) this;
        }

        public Criteria andPoemsreturnpicLike(String value) {
            addCriterion("poemsreturnpic like", value, "poemsreturnpic");
            return (Criteria) this;
        }

        public Criteria andPoemsreturnpicNotLike(String value) {
            addCriterion("poemsreturnpic not like", value, "poemsreturnpic");
            return (Criteria) this;
        }

        public Criteria andPoemsreturnpicIn(List<String> values) {
            addCriterion("poemsreturnpic in", values, "poemsreturnpic");
            return (Criteria) this;
        }

        public Criteria andPoemsreturnpicNotIn(List<String> values) {
            addCriterion("poemsreturnpic not in", values, "poemsreturnpic");
            return (Criteria) this;
        }

        public Criteria andPoemsreturnpicBetween(String value1, String value2) {
            addCriterion("poemsreturnpic between", value1, value2, "poemsreturnpic");
            return (Criteria) this;
        }

        public Criteria andPoemsreturnpicNotBetween(String value1, String value2) {
            addCriterion("poemsreturnpic not between", value1, value2, "poemsreturnpic");
            return (Criteria) this;
        }

        public Criteria andTranslatepicIsNull() {
            addCriterion("translatepic is null");
            return (Criteria) this;
        }

        public Criteria andTranslatepicIsNotNull() {
            addCriterion("translatepic is not null");
            return (Criteria) this;
        }

        public Criteria andTranslatepicEqualTo(String value) {
            addCriterion("translatepic =", value, "translatepic");
            return (Criteria) this;
        }

        public Criteria andTranslatepicNotEqualTo(String value) {
            addCriterion("translatepic <>", value, "translatepic");
            return (Criteria) this;
        }

        public Criteria andTranslatepicGreaterThan(String value) {
            addCriterion("translatepic >", value, "translatepic");
            return (Criteria) this;
        }

        public Criteria andTranslatepicGreaterThanOrEqualTo(String value) {
            addCriterion("translatepic >=", value, "translatepic");
            return (Criteria) this;
        }

        public Criteria andTranslatepicLessThan(String value) {
            addCriterion("translatepic <", value, "translatepic");
            return (Criteria) this;
        }

        public Criteria andTranslatepicLessThanOrEqualTo(String value) {
            addCriterion("translatepic <=", value, "translatepic");
            return (Criteria) this;
        }

        public Criteria andTranslatepicLike(String value) {
            addCriterion("translatepic like", value, "translatepic");
            return (Criteria) this;
        }

        public Criteria andTranslatepicNotLike(String value) {
            addCriterion("translatepic not like", value, "translatepic");
            return (Criteria) this;
        }

        public Criteria andTranslatepicIn(List<String> values) {
            addCriterion("translatepic in", values, "translatepic");
            return (Criteria) this;
        }

        public Criteria andTranslatepicNotIn(List<String> values) {
            addCriterion("translatepic not in", values, "translatepic");
            return (Criteria) this;
        }

        public Criteria andTranslatepicBetween(String value1, String value2) {
            addCriterion("translatepic between", value1, value2, "translatepic");
            return (Criteria) this;
        }

        public Criteria andTranslatepicNotBetween(String value1, String value2) {
            addCriterion("translatepic not between", value1, value2, "translatepic");
            return (Criteria) this;
        }

        public Criteria andIsdefaultIsNull() {
            addCriterion("isdefault is null");
            return (Criteria) this;
        }

        public Criteria andIsdefaultIsNotNull() {
            addCriterion("isdefault is not null");
            return (Criteria) this;
        }

        public Criteria andIsdefaultEqualTo(Byte value) {
            addCriterion("isdefault =", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultNotEqualTo(Byte value) {
            addCriterion("isdefault <>", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultGreaterThan(Byte value) {
            addCriterion("isdefault >", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultGreaterThanOrEqualTo(Byte value) {
            addCriterion("isdefault >=", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultLessThan(Byte value) {
            addCriterion("isdefault <", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultLessThanOrEqualTo(Byte value) {
            addCriterion("isdefault <=", value, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultIn(List<Byte> values) {
            addCriterion("isdefault in", values, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultNotIn(List<Byte> values) {
            addCriterion("isdefault not in", values, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultBetween(Byte value1, Byte value2) {
            addCriterion("isdefault between", value1, value2, "isdefault");
            return (Criteria) this;
        }

        public Criteria andIsdefaultNotBetween(Byte value1, Byte value2) {
            addCriterion("isdefault not between", value1, value2, "isdefault");
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