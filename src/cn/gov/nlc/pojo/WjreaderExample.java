package cn.gov.nlc.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class WjreaderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WjreaderExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andPidIsNull() {
            addCriterion("pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(String value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(String value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(String value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(String value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(String value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(String value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLike(String value) {
            addCriterion("pid like", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotLike(String value) {
            addCriterion("pid not like", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<String> values) {
            addCriterion("pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<String> values) {
            addCriterion("pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(String value1, String value2) {
            addCriterion("pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(String value1, String value2) {
            addCriterion("pid not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andWjyearIsNull() {
            addCriterion("wjyear is null");
            return (Criteria) this;
        }

        public Criteria andWjyearIsNotNull() {
            addCriterion("wjyear is not null");
            return (Criteria) this;
        }

        public Criteria andWjyearEqualTo(String value) {
            addCriterion("wjyear =", value, "wjyear");
            return (Criteria) this;
        }

        public Criteria andWjyearNotEqualTo(String value) {
            addCriterion("wjyear <>", value, "wjyear");
            return (Criteria) this;
        }

        public Criteria andWjyearGreaterThan(String value) {
            addCriterion("wjyear >", value, "wjyear");
            return (Criteria) this;
        }

        public Criteria andWjyearGreaterThanOrEqualTo(String value) {
            addCriterion("wjyear >=", value, "wjyear");
            return (Criteria) this;
        }

        public Criteria andWjyearLessThan(String value) {
            addCriterion("wjyear <", value, "wjyear");
            return (Criteria) this;
        }

        public Criteria andWjyearLessThanOrEqualTo(String value) {
            addCriterion("wjyear <=", value, "wjyear");
            return (Criteria) this;
        }

        public Criteria andWjyearLike(String value) {
            addCriterion("wjyear like", value, "wjyear");
            return (Criteria) this;
        }

        public Criteria andWjyearNotLike(String value) {
            addCriterion("wjyear not like", value, "wjyear");
            return (Criteria) this;
        }

        public Criteria andWjyearIn(List<String> values) {
            addCriterion("wjyear in", values, "wjyear");
            return (Criteria) this;
        }

        public Criteria andWjyearNotIn(List<String> values) {
            addCriterion("wjyear not in", values, "wjyear");
            return (Criteria) this;
        }

        public Criteria andWjyearBetween(String value1, String value2) {
            addCriterion("wjyear between", value1, value2, "wjyear");
            return (Criteria) this;
        }

        public Criteria andWjyearNotBetween(String value1, String value2) {
            addCriterion("wjyear not between", value1, value2, "wjyear");
            return (Criteria) this;
        }

        public Criteria andWjmonthIsNull() {
            addCriterion("wjmonth is null");
            return (Criteria) this;
        }

        public Criteria andWjmonthIsNotNull() {
            addCriterion("wjmonth is not null");
            return (Criteria) this;
        }

        public Criteria andWjmonthEqualTo(String value) {
            addCriterion("wjmonth =", value, "wjmonth");
            return (Criteria) this;
        }

        public Criteria andWjmonthNotEqualTo(String value) {
            addCriterion("wjmonth <>", value, "wjmonth");
            return (Criteria) this;
        }

        public Criteria andWjmonthGreaterThan(String value) {
            addCriterion("wjmonth >", value, "wjmonth");
            return (Criteria) this;
        }

        public Criteria andWjmonthGreaterThanOrEqualTo(String value) {
            addCriterion("wjmonth >=", value, "wjmonth");
            return (Criteria) this;
        }

        public Criteria andWjmonthLessThan(String value) {
            addCriterion("wjmonth <", value, "wjmonth");
            return (Criteria) this;
        }

        public Criteria andWjmonthLessThanOrEqualTo(String value) {
            addCriterion("wjmonth <=", value, "wjmonth");
            return (Criteria) this;
        }

        public Criteria andWjmonthLike(String value) {
            addCriterion("wjmonth like", value, "wjmonth");
            return (Criteria) this;
        }

        public Criteria andWjmonthNotLike(String value) {
            addCriterion("wjmonth not like", value, "wjmonth");
            return (Criteria) this;
        }

        public Criteria andWjmonthIn(List<String> values) {
            addCriterion("wjmonth in", values, "wjmonth");
            return (Criteria) this;
        }

        public Criteria andWjmonthNotIn(List<String> values) {
            addCriterion("wjmonth not in", values, "wjmonth");
            return (Criteria) this;
        }

        public Criteria andWjmonthBetween(String value1, String value2) {
            addCriterion("wjmonth between", value1, value2, "wjmonth");
            return (Criteria) this;
        }

        public Criteria andWjmonthNotBetween(String value1, String value2) {
            addCriterion("wjmonth not between", value1, value2, "wjmonth");
            return (Criteria) this;
        }

        public Criteria andWjdateIsNull() {
            addCriterion("wjdate is null");
            return (Criteria) this;
        }

        public Criteria andWjdateIsNotNull() {
            addCriterion("wjdate is not null");
            return (Criteria) this;
        }

        public Criteria andWjdateEqualTo(Date value) {
            addCriterionForJDBCDate("wjdate =", value, "wjdate");
            return (Criteria) this;
        }

        public Criteria andWjdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("wjdate <>", value, "wjdate");
            return (Criteria) this;
        }

        public Criteria andWjdateGreaterThan(Date value) {
            addCriterionForJDBCDate("wjdate >", value, "wjdate");
            return (Criteria) this;
        }

        public Criteria andWjdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("wjdate >=", value, "wjdate");
            return (Criteria) this;
        }

        public Criteria andWjdateLessThan(Date value) {
            addCriterionForJDBCDate("wjdate <", value, "wjdate");
            return (Criteria) this;
        }

        public Criteria andWjdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("wjdate <=", value, "wjdate");
            return (Criteria) this;
        }

        public Criteria andWjdateIn(List<Date> values) {
            addCriterionForJDBCDate("wjdate in", values, "wjdate");
            return (Criteria) this;
        }

        public Criteria andWjdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("wjdate not in", values, "wjdate");
            return (Criteria) this;
        }

        public Criteria andWjdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("wjdate between", value1, value2, "wjdate");
            return (Criteria) this;
        }

        public Criteria andWjdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("wjdate not between", value1, value2, "wjdate");
            return (Criteria) this;
        }

        public Criteria andShijuIsNull() {
            addCriterion("shiju is null");
            return (Criteria) this;
        }

        public Criteria andShijuIsNotNull() {
            addCriterion("shiju is not null");
            return (Criteria) this;
        }

        public Criteria andShijuEqualTo(String value) {
            addCriterion("shiju =", value, "shiju");
            return (Criteria) this;
        }

        public Criteria andShijuNotEqualTo(String value) {
            addCriterion("shiju <>", value, "shiju");
            return (Criteria) this;
        }

        public Criteria andShijuGreaterThan(String value) {
            addCriterion("shiju >", value, "shiju");
            return (Criteria) this;
        }

        public Criteria andShijuGreaterThanOrEqualTo(String value) {
            addCriterion("shiju >=", value, "shiju");
            return (Criteria) this;
        }

        public Criteria andShijuLessThan(String value) {
            addCriterion("shiju <", value, "shiju");
            return (Criteria) this;
        }

        public Criteria andShijuLessThanOrEqualTo(String value) {
            addCriterion("shiju <=", value, "shiju");
            return (Criteria) this;
        }

        public Criteria andShijuLike(String value) {
            addCriterion("shiju like", value, "shiju");
            return (Criteria) this;
        }

        public Criteria andShijuNotLike(String value) {
            addCriterion("shiju not like", value, "shiju");
            return (Criteria) this;
        }

        public Criteria andShijuIn(List<String> values) {
            addCriterion("shiju in", values, "shiju");
            return (Criteria) this;
        }

        public Criteria andShijuNotIn(List<String> values) {
            addCriterion("shiju not in", values, "shiju");
            return (Criteria) this;
        }

        public Criteria andShijuBetween(String value1, String value2) {
            addCriterion("shiju between", value1, value2, "shiju");
            return (Criteria) this;
        }

        public Criteria andShijuNotBetween(String value1, String value2) {
            addCriterion("shiju not between", value1, value2, "shiju");
            return (Criteria) this;
        }

        public Criteria andSjsourceIsNull() {
            addCriterion("sjsource is null");
            return (Criteria) this;
        }

        public Criteria andSjsourceIsNotNull() {
            addCriterion("sjsource is not null");
            return (Criteria) this;
        }

        public Criteria andSjsourceEqualTo(String value) {
            addCriterion("sjsource =", value, "sjsource");
            return (Criteria) this;
        }

        public Criteria andSjsourceNotEqualTo(String value) {
            addCriterion("sjsource <>", value, "sjsource");
            return (Criteria) this;
        }

        public Criteria andSjsourceGreaterThan(String value) {
            addCriterion("sjsource >", value, "sjsource");
            return (Criteria) this;
        }

        public Criteria andSjsourceGreaterThanOrEqualTo(String value) {
            addCriterion("sjsource >=", value, "sjsource");
            return (Criteria) this;
        }

        public Criteria andSjsourceLessThan(String value) {
            addCriterion("sjsource <", value, "sjsource");
            return (Criteria) this;
        }

        public Criteria andSjsourceLessThanOrEqualTo(String value) {
            addCriterion("sjsource <=", value, "sjsource");
            return (Criteria) this;
        }

        public Criteria andSjsourceLike(String value) {
            addCriterion("sjsource like", value, "sjsource");
            return (Criteria) this;
        }

        public Criteria andSjsourceNotLike(String value) {
            addCriterion("sjsource not like", value, "sjsource");
            return (Criteria) this;
        }

        public Criteria andSjsourceIn(List<String> values) {
            addCriterion("sjsource in", values, "sjsource");
            return (Criteria) this;
        }

        public Criteria andSjsourceNotIn(List<String> values) {
            addCriterion("sjsource not in", values, "sjsource");
            return (Criteria) this;
        }

        public Criteria andSjsourceBetween(String value1, String value2) {
            addCriterion("sjsource between", value1, value2, "sjsource");
            return (Criteria) this;
        }

        public Criteria andSjsourceNotBetween(String value1, String value2) {
            addCriterion("sjsource not between", value1, value2, "sjsource");
            return (Criteria) this;
        }

        public Criteria andSjyiwenIsNull() {
            addCriterion("sjyiwen is null");
            return (Criteria) this;
        }

        public Criteria andSjyiwenIsNotNull() {
            addCriterion("sjyiwen is not null");
            return (Criteria) this;
        }

        public Criteria andSjyiwenEqualTo(String value) {
            addCriterion("sjyiwen =", value, "sjyiwen");
            return (Criteria) this;
        }

        public Criteria andSjyiwenNotEqualTo(String value) {
            addCriterion("sjyiwen <>", value, "sjyiwen");
            return (Criteria) this;
        }

        public Criteria andSjyiwenGreaterThan(String value) {
            addCriterion("sjyiwen >", value, "sjyiwen");
            return (Criteria) this;
        }

        public Criteria andSjyiwenGreaterThanOrEqualTo(String value) {
            addCriterion("sjyiwen >=", value, "sjyiwen");
            return (Criteria) this;
        }

        public Criteria andSjyiwenLessThan(String value) {
            addCriterion("sjyiwen <", value, "sjyiwen");
            return (Criteria) this;
        }

        public Criteria andSjyiwenLessThanOrEqualTo(String value) {
            addCriterion("sjyiwen <=", value, "sjyiwen");
            return (Criteria) this;
        }

        public Criteria andSjyiwenLike(String value) {
            addCriterion("sjyiwen like", value, "sjyiwen");
            return (Criteria) this;
        }

        public Criteria andSjyiwenNotLike(String value) {
            addCriterion("sjyiwen not like", value, "sjyiwen");
            return (Criteria) this;
        }

        public Criteria andSjyiwenIn(List<String> values) {
            addCriterion("sjyiwen in", values, "sjyiwen");
            return (Criteria) this;
        }

        public Criteria andSjyiwenNotIn(List<String> values) {
            addCriterion("sjyiwen not in", values, "sjyiwen");
            return (Criteria) this;
        }

        public Criteria andSjyiwenBetween(String value1, String value2) {
            addCriterion("sjyiwen between", value1, value2, "sjyiwen");
            return (Criteria) this;
        }

        public Criteria andSjyiwenNotBetween(String value1, String value2) {
            addCriterion("sjyiwen not between", value1, value2, "sjyiwen");
            return (Criteria) this;
        }

        public Criteria andSjurlIsNull() {
            addCriterion("sjurl is null");
            return (Criteria) this;
        }

        public Criteria andSjurlIsNotNull() {
            addCriterion("sjurl is not null");
            return (Criteria) this;
        }

        public Criteria andSjurlEqualTo(String value) {
            addCriterion("sjurl =", value, "sjurl");
            return (Criteria) this;
        }

        public Criteria andSjurlNotEqualTo(String value) {
            addCriterion("sjurl <>", value, "sjurl");
            return (Criteria) this;
        }

        public Criteria andSjurlGreaterThan(String value) {
            addCriterion("sjurl >", value, "sjurl");
            return (Criteria) this;
        }

        public Criteria andSjurlGreaterThanOrEqualTo(String value) {
            addCriterion("sjurl >=", value, "sjurl");
            return (Criteria) this;
        }

        public Criteria andSjurlLessThan(String value) {
            addCriterion("sjurl <", value, "sjurl");
            return (Criteria) this;
        }

        public Criteria andSjurlLessThanOrEqualTo(String value) {
            addCriterion("sjurl <=", value, "sjurl");
            return (Criteria) this;
        }

        public Criteria andSjurlLike(String value) {
            addCriterion("sjurl like", value, "sjurl");
            return (Criteria) this;
        }

        public Criteria andSjurlNotLike(String value) {
            addCriterion("sjurl not like", value, "sjurl");
            return (Criteria) this;
        }

        public Criteria andSjurlIn(List<String> values) {
            addCriterion("sjurl in", values, "sjurl");
            return (Criteria) this;
        }

        public Criteria andSjurlNotIn(List<String> values) {
            addCriterion("sjurl not in", values, "sjurl");
            return (Criteria) this;
        }

        public Criteria andSjurlBetween(String value1, String value2) {
            addCriterion("sjurl between", value1, value2, "sjurl");
            return (Criteria) this;
        }

        public Criteria andSjurlNotBetween(String value1, String value2) {
            addCriterion("sjurl not between", value1, value2, "sjurl");
            return (Criteria) this;
        }

        public Criteria andQuanshiIsNull() {
            addCriterion("quanshi is null");
            return (Criteria) this;
        }

        public Criteria andQuanshiIsNotNull() {
            addCriterion("quanshi is not null");
            return (Criteria) this;
        }

        public Criteria andQuanshiEqualTo(String value) {
            addCriterion("quanshi =", value, "quanshi");
            return (Criteria) this;
        }

        public Criteria andQuanshiNotEqualTo(String value) {
            addCriterion("quanshi <>", value, "quanshi");
            return (Criteria) this;
        }

        public Criteria andQuanshiGreaterThan(String value) {
            addCriterion("quanshi >", value, "quanshi");
            return (Criteria) this;
        }

        public Criteria andQuanshiGreaterThanOrEqualTo(String value) {
            addCriterion("quanshi >=", value, "quanshi");
            return (Criteria) this;
        }

        public Criteria andQuanshiLessThan(String value) {
            addCriterion("quanshi <", value, "quanshi");
            return (Criteria) this;
        }

        public Criteria andQuanshiLessThanOrEqualTo(String value) {
            addCriterion("quanshi <=", value, "quanshi");
            return (Criteria) this;
        }

        public Criteria andQuanshiLike(String value) {
            addCriterion("quanshi like", value, "quanshi");
            return (Criteria) this;
        }

        public Criteria andQuanshiNotLike(String value) {
            addCriterion("quanshi not like", value, "quanshi");
            return (Criteria) this;
        }

        public Criteria andQuanshiIn(List<String> values) {
            addCriterion("quanshi in", values, "quanshi");
            return (Criteria) this;
        }

        public Criteria andQuanshiNotIn(List<String> values) {
            addCriterion("quanshi not in", values, "quanshi");
            return (Criteria) this;
        }

        public Criteria andQuanshiBetween(String value1, String value2) {
            addCriterion("quanshi between", value1, value2, "quanshi");
            return (Criteria) this;
        }

        public Criteria andQuanshiNotBetween(String value1, String value2) {
            addCriterion("quanshi not between", value1, value2, "quanshi");
            return (Criteria) this;
        }

        public Criteria andGeyanIsNull() {
            addCriterion("geyan is null");
            return (Criteria) this;
        }

        public Criteria andGeyanIsNotNull() {
            addCriterion("geyan is not null");
            return (Criteria) this;
        }

        public Criteria andGeyanEqualTo(String value) {
            addCriterion("geyan =", value, "geyan");
            return (Criteria) this;
        }

        public Criteria andGeyanNotEqualTo(String value) {
            addCriterion("geyan <>", value, "geyan");
            return (Criteria) this;
        }

        public Criteria andGeyanGreaterThan(String value) {
            addCriterion("geyan >", value, "geyan");
            return (Criteria) this;
        }

        public Criteria andGeyanGreaterThanOrEqualTo(String value) {
            addCriterion("geyan >=", value, "geyan");
            return (Criteria) this;
        }

        public Criteria andGeyanLessThan(String value) {
            addCriterion("geyan <", value, "geyan");
            return (Criteria) this;
        }

        public Criteria andGeyanLessThanOrEqualTo(String value) {
            addCriterion("geyan <=", value, "geyan");
            return (Criteria) this;
        }

        public Criteria andGeyanLike(String value) {
            addCriterion("geyan like", value, "geyan");
            return (Criteria) this;
        }

        public Criteria andGeyanNotLike(String value) {
            addCriterion("geyan not like", value, "geyan");
            return (Criteria) this;
        }

        public Criteria andGeyanIn(List<String> values) {
            addCriterion("geyan in", values, "geyan");
            return (Criteria) this;
        }

        public Criteria andGeyanNotIn(List<String> values) {
            addCriterion("geyan not in", values, "geyan");
            return (Criteria) this;
        }

        public Criteria andGeyanBetween(String value1, String value2) {
            addCriterion("geyan between", value1, value2, "geyan");
            return (Criteria) this;
        }

        public Criteria andGeyanNotBetween(String value1, String value2) {
            addCriterion("geyan not between", value1, value2, "geyan");
            return (Criteria) this;
        }

        public Criteria andGysourceIsNull() {
            addCriterion("gysource is null");
            return (Criteria) this;
        }

        public Criteria andGysourceIsNotNull() {
            addCriterion("gysource is not null");
            return (Criteria) this;
        }

        public Criteria andGysourceEqualTo(String value) {
            addCriterion("gysource =", value, "gysource");
            return (Criteria) this;
        }

        public Criteria andGysourceNotEqualTo(String value) {
            addCriterion("gysource <>", value, "gysource");
            return (Criteria) this;
        }

        public Criteria andGysourceGreaterThan(String value) {
            addCriterion("gysource >", value, "gysource");
            return (Criteria) this;
        }

        public Criteria andGysourceGreaterThanOrEqualTo(String value) {
            addCriterion("gysource >=", value, "gysource");
            return (Criteria) this;
        }

        public Criteria andGysourceLessThan(String value) {
            addCriterion("gysource <", value, "gysource");
            return (Criteria) this;
        }

        public Criteria andGysourceLessThanOrEqualTo(String value) {
            addCriterion("gysource <=", value, "gysource");
            return (Criteria) this;
        }

        public Criteria andGysourceLike(String value) {
            addCriterion("gysource like", value, "gysource");
            return (Criteria) this;
        }

        public Criteria andGysourceNotLike(String value) {
            addCriterion("gysource not like", value, "gysource");
            return (Criteria) this;
        }

        public Criteria andGysourceIn(List<String> values) {
            addCriterion("gysource in", values, "gysource");
            return (Criteria) this;
        }

        public Criteria andGysourceNotIn(List<String> values) {
            addCriterion("gysource not in", values, "gysource");
            return (Criteria) this;
        }

        public Criteria andGysourceBetween(String value1, String value2) {
            addCriterion("gysource between", value1, value2, "gysource");
            return (Criteria) this;
        }

        public Criteria andGysourceNotBetween(String value1, String value2) {
            addCriterion("gysource not between", value1, value2, "gysource");
            return (Criteria) this;
        }

        public Criteria andGyyiwenIsNull() {
            addCriterion("gyyiwen is null");
            return (Criteria) this;
        }

        public Criteria andGyyiwenIsNotNull() {
            addCriterion("gyyiwen is not null");
            return (Criteria) this;
        }

        public Criteria andGyyiwenEqualTo(String value) {
            addCriterion("gyyiwen =", value, "gyyiwen");
            return (Criteria) this;
        }

        public Criteria andGyyiwenNotEqualTo(String value) {
            addCriterion("gyyiwen <>", value, "gyyiwen");
            return (Criteria) this;
        }

        public Criteria andGyyiwenGreaterThan(String value) {
            addCriterion("gyyiwen >", value, "gyyiwen");
            return (Criteria) this;
        }

        public Criteria andGyyiwenGreaterThanOrEqualTo(String value) {
            addCriterion("gyyiwen >=", value, "gyyiwen");
            return (Criteria) this;
        }

        public Criteria andGyyiwenLessThan(String value) {
            addCriterion("gyyiwen <", value, "gyyiwen");
            return (Criteria) this;
        }

        public Criteria andGyyiwenLessThanOrEqualTo(String value) {
            addCriterion("gyyiwen <=", value, "gyyiwen");
            return (Criteria) this;
        }

        public Criteria andGyyiwenLike(String value) {
            addCriterion("gyyiwen like", value, "gyyiwen");
            return (Criteria) this;
        }

        public Criteria andGyyiwenNotLike(String value) {
            addCriterion("gyyiwen not like", value, "gyyiwen");
            return (Criteria) this;
        }

        public Criteria andGyyiwenIn(List<String> values) {
            addCriterion("gyyiwen in", values, "gyyiwen");
            return (Criteria) this;
        }

        public Criteria andGyyiwenNotIn(List<String> values) {
            addCriterion("gyyiwen not in", values, "gyyiwen");
            return (Criteria) this;
        }

        public Criteria andGyyiwenBetween(String value1, String value2) {
            addCriterion("gyyiwen between", value1, value2, "gyyiwen");
            return (Criteria) this;
        }

        public Criteria andGyyiwenNotBetween(String value1, String value2) {
            addCriterion("gyyiwen not between", value1, value2, "gyyiwen");
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

        public Criteria andBkpubtimeIsNull() {
            addCriterion("bkpubtime is null");
            return (Criteria) this;
        }

        public Criteria andBkpubtimeIsNotNull() {
            addCriterion("bkpubtime is not null");
            return (Criteria) this;
        }

        public Criteria andBkpubtimeEqualTo(Date value) {
            addCriterion("bkpubtime =", value, "bkpubtime");
            return (Criteria) this;
        }

        public Criteria andBkpubtimeNotEqualTo(Date value) {
            addCriterion("bkpubtime <>", value, "bkpubtime");
            return (Criteria) this;
        }

        public Criteria andBkpubtimeGreaterThan(Date value) {
            addCriterion("bkpubtime >", value, "bkpubtime");
            return (Criteria) this;
        }

        public Criteria andBkpubtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("bkpubtime >=", value, "bkpubtime");
            return (Criteria) this;
        }

        public Criteria andBkpubtimeLessThan(Date value) {
            addCriterion("bkpubtime <", value, "bkpubtime");
            return (Criteria) this;
        }

        public Criteria andBkpubtimeLessThanOrEqualTo(Date value) {
            addCriterion("bkpubtime <=", value, "bkpubtime");
            return (Criteria) this;
        }

        public Criteria andBkpubtimeIn(List<Date> values) {
            addCriterion("bkpubtime in", values, "bkpubtime");
            return (Criteria) this;
        }

        public Criteria andBkpubtimeNotIn(List<Date> values) {
            addCriterion("bkpubtime not in", values, "bkpubtime");
            return (Criteria) this;
        }

        public Criteria andBkpubtimeBetween(Date value1, Date value2) {
            addCriterion("bkpubtime between", value1, value2, "bkpubtime");
            return (Criteria) this;
        }

        public Criteria andBkpubtimeNotBetween(Date value1, Date value2) {
            addCriterion("bkpubtime not between", value1, value2, "bkpubtime");
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