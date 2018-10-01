package cn.gov.nlc.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NlcsubjectcatalogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NlcsubjectcatalogExample() {
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

        public Criteria andSubjectidIsNull() {
            addCriterion("subjectid is null");
            return (Criteria) this;
        }

        public Criteria andSubjectidIsNotNull() {
            addCriterion("subjectid is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectidEqualTo(String value) {
            addCriterion("subjectid =", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidNotEqualTo(String value) {
            addCriterion("subjectid <>", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidGreaterThan(String value) {
            addCriterion("subjectid >", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidGreaterThanOrEqualTo(String value) {
            addCriterion("subjectid >=", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidLessThan(String value) {
            addCriterion("subjectid <", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidLessThanOrEqualTo(String value) {
            addCriterion("subjectid <=", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidLike(String value) {
            addCriterion("subjectid like", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidNotLike(String value) {
            addCriterion("subjectid not like", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidIn(List<String> values) {
            addCriterion("subjectid in", values, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidNotIn(List<String> values) {
            addCriterion("subjectid not in", values, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidBetween(String value1, String value2) {
            addCriterion("subjectid between", value1, value2, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidNotBetween(String value1, String value2) {
            addCriterion("subjectid not between", value1, value2, "subjectid");
            return (Criteria) this;
        }

        public Criteria andCatalogidIsNull() {
            addCriterion("catalogid is null");
            return (Criteria) this;
        }

        public Criteria andCatalogidIsNotNull() {
            addCriterion("catalogid is not null");
            return (Criteria) this;
        }

        public Criteria andCatalogidEqualTo(String value) {
            addCriterion("catalogid =", value, "catalogid");
            return (Criteria) this;
        }

        public Criteria andCatalogidNotEqualTo(String value) {
            addCriterion("catalogid <>", value, "catalogid");
            return (Criteria) this;
        }

        public Criteria andCatalogidGreaterThan(String value) {
            addCriterion("catalogid >", value, "catalogid");
            return (Criteria) this;
        }

        public Criteria andCatalogidGreaterThanOrEqualTo(String value) {
            addCriterion("catalogid >=", value, "catalogid");
            return (Criteria) this;
        }

        public Criteria andCatalogidLessThan(String value) {
            addCriterion("catalogid <", value, "catalogid");
            return (Criteria) this;
        }

        public Criteria andCatalogidLessThanOrEqualTo(String value) {
            addCriterion("catalogid <=", value, "catalogid");
            return (Criteria) this;
        }

        public Criteria andCatalogidLike(String value) {
            addCriterion("catalogid like", value, "catalogid");
            return (Criteria) this;
        }

        public Criteria andCatalogidNotLike(String value) {
            addCriterion("catalogid not like", value, "catalogid");
            return (Criteria) this;
        }

        public Criteria andCatalogidIn(List<String> values) {
            addCriterion("catalogid in", values, "catalogid");
            return (Criteria) this;
        }

        public Criteria andCatalogidNotIn(List<String> values) {
            addCriterion("catalogid not in", values, "catalogid");
            return (Criteria) this;
        }

        public Criteria andCatalogidBetween(String value1, String value2) {
            addCriterion("catalogid between", value1, value2, "catalogid");
            return (Criteria) this;
        }

        public Criteria andCatalogidNotBetween(String value1, String value2) {
            addCriterion("catalogid not between", value1, value2, "catalogid");
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

        public Criteria andIconclsIsNull() {
            addCriterion("iconCls is null");
            return (Criteria) this;
        }

        public Criteria andIconclsIsNotNull() {
            addCriterion("iconCls is not null");
            return (Criteria) this;
        }

        public Criteria andIconclsEqualTo(String value) {
            addCriterion("iconCls =", value, "iconcls");
            return (Criteria) this;
        }

        public Criteria andIconclsNotEqualTo(String value) {
            addCriterion("iconCls <>", value, "iconcls");
            return (Criteria) this;
        }

        public Criteria andIconclsGreaterThan(String value) {
            addCriterion("iconCls >", value, "iconcls");
            return (Criteria) this;
        }

        public Criteria andIconclsGreaterThanOrEqualTo(String value) {
            addCriterion("iconCls >=", value, "iconcls");
            return (Criteria) this;
        }

        public Criteria andIconclsLessThan(String value) {
            addCriterion("iconCls <", value, "iconcls");
            return (Criteria) this;
        }

        public Criteria andIconclsLessThanOrEqualTo(String value) {
            addCriterion("iconCls <=", value, "iconcls");
            return (Criteria) this;
        }

        public Criteria andIconclsLike(String value) {
            addCriterion("iconCls like", value, "iconcls");
            return (Criteria) this;
        }

        public Criteria andIconclsNotLike(String value) {
            addCriterion("iconCls not like", value, "iconcls");
            return (Criteria) this;
        }

        public Criteria andIconclsIn(List<String> values) {
            addCriterion("iconCls in", values, "iconcls");
            return (Criteria) this;
        }

        public Criteria andIconclsNotIn(List<String> values) {
            addCriterion("iconCls not in", values, "iconcls");
            return (Criteria) this;
        }

        public Criteria andIconclsBetween(String value1, String value2) {
            addCriterion("iconCls between", value1, value2, "iconcls");
            return (Criteria) this;
        }

        public Criteria andIconclsNotBetween(String value1, String value2) {
            addCriterion("iconCls not between", value1, value2, "iconcls");
            return (Criteria) this;
        }

        public Criteria andCseqIsNull() {
            addCriterion("cseq is null");
            return (Criteria) this;
        }

        public Criteria andCseqIsNotNull() {
            addCriterion("cseq is not null");
            return (Criteria) this;
        }

        public Criteria andCseqEqualTo(Integer value) {
            addCriterion("cseq =", value, "cseq");
            return (Criteria) this;
        }

        public Criteria andCseqNotEqualTo(Integer value) {
            addCriterion("cseq <>", value, "cseq");
            return (Criteria) this;
        }

        public Criteria andCseqGreaterThan(Integer value) {
            addCriterion("cseq >", value, "cseq");
            return (Criteria) this;
        }

        public Criteria andCseqGreaterThanOrEqualTo(Integer value) {
            addCriterion("cseq >=", value, "cseq");
            return (Criteria) this;
        }

        public Criteria andCseqLessThan(Integer value) {
            addCriterion("cseq <", value, "cseq");
            return (Criteria) this;
        }

        public Criteria andCseqLessThanOrEqualTo(Integer value) {
            addCriterion("cseq <=", value, "cseq");
            return (Criteria) this;
        }

        public Criteria andCseqIn(List<Integer> values) {
            addCriterion("cseq in", values, "cseq");
            return (Criteria) this;
        }

        public Criteria andCseqNotIn(List<Integer> values) {
            addCriterion("cseq not in", values, "cseq");
            return (Criteria) this;
        }

        public Criteria andCseqBetween(Integer value1, Integer value2) {
            addCriterion("cseq between", value1, value2, "cseq");
            return (Criteria) this;
        }

        public Criteria andCseqNotBetween(Integer value1, Integer value2) {
            addCriterion("cseq not between", value1, value2, "cseq");
            return (Criteria) this;
        }

        public Criteria andCheckedIsNull() {
            addCriterion("checked is null");
            return (Criteria) this;
        }

        public Criteria andCheckedIsNotNull() {
            addCriterion("checked is not null");
            return (Criteria) this;
        }

        public Criteria andCheckedEqualTo(String value) {
            addCriterion("checked =", value, "checked");
            return (Criteria) this;
        }

        public Criteria andCheckedNotEqualTo(String value) {
            addCriterion("checked <>", value, "checked");
            return (Criteria) this;
        }

        public Criteria andCheckedGreaterThan(String value) {
            addCriterion("checked >", value, "checked");
            return (Criteria) this;
        }

        public Criteria andCheckedGreaterThanOrEqualTo(String value) {
            addCriterion("checked >=", value, "checked");
            return (Criteria) this;
        }

        public Criteria andCheckedLessThan(String value) {
            addCriterion("checked <", value, "checked");
            return (Criteria) this;
        }

        public Criteria andCheckedLessThanOrEqualTo(String value) {
            addCriterion("checked <=", value, "checked");
            return (Criteria) this;
        }

        public Criteria andCheckedLike(String value) {
            addCriterion("checked like", value, "checked");
            return (Criteria) this;
        }

        public Criteria andCheckedNotLike(String value) {
            addCriterion("checked not like", value, "checked");
            return (Criteria) this;
        }

        public Criteria andCheckedIn(List<String> values) {
            addCriterion("checked in", values, "checked");
            return (Criteria) this;
        }

        public Criteria andCheckedNotIn(List<String> values) {
            addCriterion("checked not in", values, "checked");
            return (Criteria) this;
        }

        public Criteria andCheckedBetween(String value1, String value2) {
            addCriterion("checked between", value1, value2, "checked");
            return (Criteria) this;
        }

        public Criteria andCheckedNotBetween(String value1, String value2) {
            addCriterion("checked not between", value1, value2, "checked");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("state like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("state not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andPubtimeIsNull() {
            addCriterion("pubtime is null");
            return (Criteria) this;
        }

        public Criteria andPubtimeIsNotNull() {
            addCriterion("pubtime is not null");
            return (Criteria) this;
        }

        public Criteria andPubtimeEqualTo(Date value) {
            addCriterion("pubtime =", value, "pubtime");
            return (Criteria) this;
        }

        public Criteria andPubtimeNotEqualTo(Date value) {
            addCriterion("pubtime <>", value, "pubtime");
            return (Criteria) this;
        }

        public Criteria andPubtimeGreaterThan(Date value) {
            addCriterion("pubtime >", value, "pubtime");
            return (Criteria) this;
        }

        public Criteria andPubtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pubtime >=", value, "pubtime");
            return (Criteria) this;
        }

        public Criteria andPubtimeLessThan(Date value) {
            addCriterion("pubtime <", value, "pubtime");
            return (Criteria) this;
        }

        public Criteria andPubtimeLessThanOrEqualTo(Date value) {
            addCriterion("pubtime <=", value, "pubtime");
            return (Criteria) this;
        }

        public Criteria andPubtimeIn(List<Date> values) {
            addCriterion("pubtime in", values, "pubtime");
            return (Criteria) this;
        }

        public Criteria andPubtimeNotIn(List<Date> values) {
            addCriterion("pubtime not in", values, "pubtime");
            return (Criteria) this;
        }

        public Criteria andPubtimeBetween(Date value1, Date value2) {
            addCriterion("pubtime between", value1, value2, "pubtime");
            return (Criteria) this;
        }

        public Criteria andPubtimeNotBetween(Date value1, Date value2) {
            addCriterion("pubtime not between", value1, value2, "pubtime");
            return (Criteria) this;
        }

        public Criteria andDowntimeIsNull() {
            addCriterion("downtime is null");
            return (Criteria) this;
        }

        public Criteria andDowntimeIsNotNull() {
            addCriterion("downtime is not null");
            return (Criteria) this;
        }

        public Criteria andDowntimeEqualTo(Date value) {
            addCriterion("downtime =", value, "downtime");
            return (Criteria) this;
        }

        public Criteria andDowntimeNotEqualTo(Date value) {
            addCriterion("downtime <>", value, "downtime");
            return (Criteria) this;
        }

        public Criteria andDowntimeGreaterThan(Date value) {
            addCriterion("downtime >", value, "downtime");
            return (Criteria) this;
        }

        public Criteria andDowntimeGreaterThanOrEqualTo(Date value) {
            addCriterion("downtime >=", value, "downtime");
            return (Criteria) this;
        }

        public Criteria andDowntimeLessThan(Date value) {
            addCriterion("downtime <", value, "downtime");
            return (Criteria) this;
        }

        public Criteria andDowntimeLessThanOrEqualTo(Date value) {
            addCriterion("downtime <=", value, "downtime");
            return (Criteria) this;
        }

        public Criteria andDowntimeIn(List<Date> values) {
            addCriterion("downtime in", values, "downtime");
            return (Criteria) this;
        }

        public Criteria andDowntimeNotIn(List<Date> values) {
            addCriterion("downtime not in", values, "downtime");
            return (Criteria) this;
        }

        public Criteria andDowntimeBetween(Date value1, Date value2) {
            addCriterion("downtime between", value1, value2, "downtime");
            return (Criteria) this;
        }

        public Criteria andDowntimeNotBetween(Date value1, Date value2) {
            addCriterion("downtime not between", value1, value2, "downtime");
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

        public Criteria andIsdirIsNull() {
            addCriterion("isdir is null");
            return (Criteria) this;
        }

        public Criteria andIsdirIsNotNull() {
            addCriterion("isdir is not null");
            return (Criteria) this;
        }

        public Criteria andIsdirEqualTo(String value) {
            addCriterion("isdir =", value, "isdir");
            return (Criteria) this;
        }

        public Criteria andIsdirNotEqualTo(String value) {
            addCriterion("isdir <>", value, "isdir");
            return (Criteria) this;
        }

        public Criteria andIsdirGreaterThan(String value) {
            addCriterion("isdir >", value, "isdir");
            return (Criteria) this;
        }

        public Criteria andIsdirGreaterThanOrEqualTo(String value) {
            addCriterion("isdir >=", value, "isdir");
            return (Criteria) this;
        }

        public Criteria andIsdirLessThan(String value) {
            addCriterion("isdir <", value, "isdir");
            return (Criteria) this;
        }

        public Criteria andIsdirLessThanOrEqualTo(String value) {
            addCriterion("isdir <=", value, "isdir");
            return (Criteria) this;
        }

        public Criteria andIsdirLike(String value) {
            addCriterion("isdir like", value, "isdir");
            return (Criteria) this;
        }

        public Criteria andIsdirNotLike(String value) {
            addCriterion("isdir not like", value, "isdir");
            return (Criteria) this;
        }

        public Criteria andIsdirIn(List<String> values) {
            addCriterion("isdir in", values, "isdir");
            return (Criteria) this;
        }

        public Criteria andIsdirNotIn(List<String> values) {
            addCriterion("isdir not in", values, "isdir");
            return (Criteria) this;
        }

        public Criteria andIsdirBetween(String value1, String value2) {
            addCriterion("isdir between", value1, value2, "isdir");
            return (Criteria) this;
        }

        public Criteria andIsdirNotBetween(String value1, String value2) {
            addCriterion("isdir not between", value1, value2, "isdir");
            return (Criteria) this;
        }

        public Criteria andLeaforderIsNull() {
            addCriterion("leaforder is null");
            return (Criteria) this;
        }

        public Criteria andLeaforderIsNotNull() {
            addCriterion("leaforder is not null");
            return (Criteria) this;
        }

        public Criteria andLeaforderEqualTo(Integer value) {
            addCriterion("leaforder =", value, "leaforder");
            return (Criteria) this;
        }

        public Criteria andLeaforderNotEqualTo(Integer value) {
            addCriterion("leaforder <>", value, "leaforder");
            return (Criteria) this;
        }

        public Criteria andLeaforderGreaterThan(Integer value) {
            addCriterion("leaforder >", value, "leaforder");
            return (Criteria) this;
        }

        public Criteria andLeaforderGreaterThanOrEqualTo(Integer value) {
            addCriterion("leaforder >=", value, "leaforder");
            return (Criteria) this;
        }

        public Criteria andLeaforderLessThan(Integer value) {
            addCriterion("leaforder <", value, "leaforder");
            return (Criteria) this;
        }

        public Criteria andLeaforderLessThanOrEqualTo(Integer value) {
            addCriterion("leaforder <=", value, "leaforder");
            return (Criteria) this;
        }

        public Criteria andLeaforderIn(List<Integer> values) {
            addCriterion("leaforder in", values, "leaforder");
            return (Criteria) this;
        }

        public Criteria andLeaforderNotIn(List<Integer> values) {
            addCriterion("leaforder not in", values, "leaforder");
            return (Criteria) this;
        }

        public Criteria andLeaforderBetween(Integer value1, Integer value2) {
            addCriterion("leaforder between", value1, value2, "leaforder");
            return (Criteria) this;
        }

        public Criteria andLeaforderNotBetween(Integer value1, Integer value2) {
            addCriterion("leaforder not between", value1, value2, "leaforder");
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