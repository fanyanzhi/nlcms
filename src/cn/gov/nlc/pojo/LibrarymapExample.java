package cn.gov.nlc.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LibrarymapExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LibrarymapExample() {
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

        public Criteria andSrc2IsNull() {
            addCriterion("src2 is null");
            return (Criteria) this;
        }

        public Criteria andSrc2IsNotNull() {
            addCriterion("src2 is not null");
            return (Criteria) this;
        }

        public Criteria andSrc2EqualTo(String value) {
            addCriterion("src2 =", value, "src2");
            return (Criteria) this;
        }

        public Criteria andSrc2NotEqualTo(String value) {
            addCriterion("src2 <>", value, "src2");
            return (Criteria) this;
        }

        public Criteria andSrc2GreaterThan(String value) {
            addCriterion("src2 >", value, "src2");
            return (Criteria) this;
        }

        public Criteria andSrc2GreaterThanOrEqualTo(String value) {
            addCriterion("src2 >=", value, "src2");
            return (Criteria) this;
        }

        public Criteria andSrc2LessThan(String value) {
            addCriterion("src2 <", value, "src2");
            return (Criteria) this;
        }

        public Criteria andSrc2LessThanOrEqualTo(String value) {
            addCriterion("src2 <=", value, "src2");
            return (Criteria) this;
        }

        public Criteria andSrc2Like(String value) {
            addCriterion("src2 like", value, "src2");
            return (Criteria) this;
        }

        public Criteria andSrc2NotLike(String value) {
            addCriterion("src2 not like", value, "src2");
            return (Criteria) this;
        }

        public Criteria andSrc2In(List<String> values) {
            addCriterion("src2 in", values, "src2");
            return (Criteria) this;
        }

        public Criteria andSrc2NotIn(List<String> values) {
            addCriterion("src2 not in", values, "src2");
            return (Criteria) this;
        }

        public Criteria andSrc2Between(String value1, String value2) {
            addCriterion("src2 between", value1, value2, "src2");
            return (Criteria) this;
        }

        public Criteria andSrc2NotBetween(String value1, String value2) {
            addCriterion("src2 not between", value1, value2, "src2");
            return (Criteria) this;
        }

        public Criteria andSrc3IsNull() {
            addCriterion("src3 is null");
            return (Criteria) this;
        }

        public Criteria andSrc3IsNotNull() {
            addCriterion("src3 is not null");
            return (Criteria) this;
        }

        public Criteria andSrc3EqualTo(String value) {
            addCriterion("src3 =", value, "src3");
            return (Criteria) this;
        }

        public Criteria andSrc3NotEqualTo(String value) {
            addCriterion("src3 <>", value, "src3");
            return (Criteria) this;
        }

        public Criteria andSrc3GreaterThan(String value) {
            addCriterion("src3 >", value, "src3");
            return (Criteria) this;
        }

        public Criteria andSrc3GreaterThanOrEqualTo(String value) {
            addCriterion("src3 >=", value, "src3");
            return (Criteria) this;
        }

        public Criteria andSrc3LessThan(String value) {
            addCriterion("src3 <", value, "src3");
            return (Criteria) this;
        }

        public Criteria andSrc3LessThanOrEqualTo(String value) {
            addCriterion("src3 <=", value, "src3");
            return (Criteria) this;
        }

        public Criteria andSrc3Like(String value) {
            addCriterion("src3 like", value, "src3");
            return (Criteria) this;
        }

        public Criteria andSrc3NotLike(String value) {
            addCriterion("src3 not like", value, "src3");
            return (Criteria) this;
        }

        public Criteria andSrc3In(List<String> values) {
            addCriterion("src3 in", values, "src3");
            return (Criteria) this;
        }

        public Criteria andSrc3NotIn(List<String> values) {
            addCriterion("src3 not in", values, "src3");
            return (Criteria) this;
        }

        public Criteria andSrc3Between(String value1, String value2) {
            addCriterion("src3 between", value1, value2, "src3");
            return (Criteria) this;
        }

        public Criteria andSrc3NotBetween(String value1, String value2) {
            addCriterion("src3 not between", value1, value2, "src3");
            return (Criteria) this;
        }

        public Criteria andSrc4IsNull() {
            addCriterion("src4 is null");
            return (Criteria) this;
        }

        public Criteria andSrc4IsNotNull() {
            addCriterion("src4 is not null");
            return (Criteria) this;
        }

        public Criteria andSrc4EqualTo(String value) {
            addCriterion("src4 =", value, "src4");
            return (Criteria) this;
        }

        public Criteria andSrc4NotEqualTo(String value) {
            addCriterion("src4 <>", value, "src4");
            return (Criteria) this;
        }

        public Criteria andSrc4GreaterThan(String value) {
            addCriterion("src4 >", value, "src4");
            return (Criteria) this;
        }

        public Criteria andSrc4GreaterThanOrEqualTo(String value) {
            addCriterion("src4 >=", value, "src4");
            return (Criteria) this;
        }

        public Criteria andSrc4LessThan(String value) {
            addCriterion("src4 <", value, "src4");
            return (Criteria) this;
        }

        public Criteria andSrc4LessThanOrEqualTo(String value) {
            addCriterion("src4 <=", value, "src4");
            return (Criteria) this;
        }

        public Criteria andSrc4Like(String value) {
            addCriterion("src4 like", value, "src4");
            return (Criteria) this;
        }

        public Criteria andSrc4NotLike(String value) {
            addCriterion("src4 not like", value, "src4");
            return (Criteria) this;
        }

        public Criteria andSrc4In(List<String> values) {
            addCriterion("src4 in", values, "src4");
            return (Criteria) this;
        }

        public Criteria andSrc4NotIn(List<String> values) {
            addCriterion("src4 not in", values, "src4");
            return (Criteria) this;
        }

        public Criteria andSrc4Between(String value1, String value2) {
            addCriterion("src4 between", value1, value2, "src4");
            return (Criteria) this;
        }

        public Criteria andSrc4NotBetween(String value1, String value2) {
            addCriterion("src4 not between", value1, value2, "src4");
            return (Criteria) this;
        }

        public Criteria andSrc5IsNull() {
            addCriterion("src5 is null");
            return (Criteria) this;
        }

        public Criteria andSrc5IsNotNull() {
            addCriterion("src5 is not null");
            return (Criteria) this;
        }

        public Criteria andSrc5EqualTo(String value) {
            addCriterion("src5 =", value, "src5");
            return (Criteria) this;
        }

        public Criteria andSrc5NotEqualTo(String value) {
            addCriterion("src5 <>", value, "src5");
            return (Criteria) this;
        }

        public Criteria andSrc5GreaterThan(String value) {
            addCriterion("src5 >", value, "src5");
            return (Criteria) this;
        }

        public Criteria andSrc5GreaterThanOrEqualTo(String value) {
            addCriterion("src5 >=", value, "src5");
            return (Criteria) this;
        }

        public Criteria andSrc5LessThan(String value) {
            addCriterion("src5 <", value, "src5");
            return (Criteria) this;
        }

        public Criteria andSrc5LessThanOrEqualTo(String value) {
            addCriterion("src5 <=", value, "src5");
            return (Criteria) this;
        }

        public Criteria andSrc5Like(String value) {
            addCriterion("src5 like", value, "src5");
            return (Criteria) this;
        }

        public Criteria andSrc5NotLike(String value) {
            addCriterion("src5 not like", value, "src5");
            return (Criteria) this;
        }

        public Criteria andSrc5In(List<String> values) {
            addCriterion("src5 in", values, "src5");
            return (Criteria) this;
        }

        public Criteria andSrc5NotIn(List<String> values) {
            addCriterion("src5 not in", values, "src5");
            return (Criteria) this;
        }

        public Criteria andSrc5Between(String value1, String value2) {
            addCriterion("src5 between", value1, value2, "src5");
            return (Criteria) this;
        }

        public Criteria andSrc5NotBetween(String value1, String value2) {
            addCriterion("src5 not between", value1, value2, "src5");
            return (Criteria) this;
        }

        public Criteria andSrc6IsNull() {
            addCriterion("src6 is null");
            return (Criteria) this;
        }

        public Criteria andSrc6IsNotNull() {
            addCriterion("src6 is not null");
            return (Criteria) this;
        }

        public Criteria andSrc6EqualTo(String value) {
            addCriterion("src6 =", value, "src6");
            return (Criteria) this;
        }

        public Criteria andSrc6NotEqualTo(String value) {
            addCriterion("src6 <>", value, "src6");
            return (Criteria) this;
        }

        public Criteria andSrc6GreaterThan(String value) {
            addCriterion("src6 >", value, "src6");
            return (Criteria) this;
        }

        public Criteria andSrc6GreaterThanOrEqualTo(String value) {
            addCriterion("src6 >=", value, "src6");
            return (Criteria) this;
        }

        public Criteria andSrc6LessThan(String value) {
            addCriterion("src6 <", value, "src6");
            return (Criteria) this;
        }

        public Criteria andSrc6LessThanOrEqualTo(String value) {
            addCriterion("src6 <=", value, "src6");
            return (Criteria) this;
        }

        public Criteria andSrc6Like(String value) {
            addCriterion("src6 like", value, "src6");
            return (Criteria) this;
        }

        public Criteria andSrc6NotLike(String value) {
            addCriterion("src6 not like", value, "src6");
            return (Criteria) this;
        }

        public Criteria andSrc6In(List<String> values) {
            addCriterion("src6 in", values, "src6");
            return (Criteria) this;
        }

        public Criteria andSrc6NotIn(List<String> values) {
            addCriterion("src6 not in", values, "src6");
            return (Criteria) this;
        }

        public Criteria andSrc6Between(String value1, String value2) {
            addCriterion("src6 between", value1, value2, "src6");
            return (Criteria) this;
        }

        public Criteria andSrc6NotBetween(String value1, String value2) {
            addCriterion("src6 not between", value1, value2, "src6");
            return (Criteria) this;
        }

        public Criteria andSrc7IsNull() {
            addCriterion("src7 is null");
            return (Criteria) this;
        }

        public Criteria andSrc7IsNotNull() {
            addCriterion("src7 is not null");
            return (Criteria) this;
        }

        public Criteria andSrc7EqualTo(String value) {
            addCriterion("src7 =", value, "src7");
            return (Criteria) this;
        }

        public Criteria andSrc7NotEqualTo(String value) {
            addCriterion("src7 <>", value, "src7");
            return (Criteria) this;
        }

        public Criteria andSrc7GreaterThan(String value) {
            addCriterion("src7 >", value, "src7");
            return (Criteria) this;
        }

        public Criteria andSrc7GreaterThanOrEqualTo(String value) {
            addCriterion("src7 >=", value, "src7");
            return (Criteria) this;
        }

        public Criteria andSrc7LessThan(String value) {
            addCriterion("src7 <", value, "src7");
            return (Criteria) this;
        }

        public Criteria andSrc7LessThanOrEqualTo(String value) {
            addCriterion("src7 <=", value, "src7");
            return (Criteria) this;
        }

        public Criteria andSrc7Like(String value) {
            addCriterion("src7 like", value, "src7");
            return (Criteria) this;
        }

        public Criteria andSrc7NotLike(String value) {
            addCriterion("src7 not like", value, "src7");
            return (Criteria) this;
        }

        public Criteria andSrc7In(List<String> values) {
            addCriterion("src7 in", values, "src7");
            return (Criteria) this;
        }

        public Criteria andSrc7NotIn(List<String> values) {
            addCriterion("src7 not in", values, "src7");
            return (Criteria) this;
        }

        public Criteria andSrc7Between(String value1, String value2) {
            addCriterion("src7 between", value1, value2, "src7");
            return (Criteria) this;
        }

        public Criteria andSrc7NotBetween(String value1, String value2) {
            addCriterion("src7 not between", value1, value2, "src7");
            return (Criteria) this;
        }

        public Criteria andSrc8IsNull() {
            addCriterion("src8 is null");
            return (Criteria) this;
        }

        public Criteria andSrc8IsNotNull() {
            addCriterion("src8 is not null");
            return (Criteria) this;
        }

        public Criteria andSrc8EqualTo(String value) {
            addCriterion("src8 =", value, "src8");
            return (Criteria) this;
        }

        public Criteria andSrc8NotEqualTo(String value) {
            addCriterion("src8 <>", value, "src8");
            return (Criteria) this;
        }

        public Criteria andSrc8GreaterThan(String value) {
            addCriterion("src8 >", value, "src8");
            return (Criteria) this;
        }

        public Criteria andSrc8GreaterThanOrEqualTo(String value) {
            addCriterion("src8 >=", value, "src8");
            return (Criteria) this;
        }

        public Criteria andSrc8LessThan(String value) {
            addCriterion("src8 <", value, "src8");
            return (Criteria) this;
        }

        public Criteria andSrc8LessThanOrEqualTo(String value) {
            addCriterion("src8 <=", value, "src8");
            return (Criteria) this;
        }

        public Criteria andSrc8Like(String value) {
            addCriterion("src8 like", value, "src8");
            return (Criteria) this;
        }

        public Criteria andSrc8NotLike(String value) {
            addCriterion("src8 not like", value, "src8");
            return (Criteria) this;
        }

        public Criteria andSrc8In(List<String> values) {
            addCriterion("src8 in", values, "src8");
            return (Criteria) this;
        }

        public Criteria andSrc8NotIn(List<String> values) {
            addCriterion("src8 not in", values, "src8");
            return (Criteria) this;
        }

        public Criteria andSrc8Between(String value1, String value2) {
            addCriterion("src8 between", value1, value2, "src8");
            return (Criteria) this;
        }

        public Criteria andSrc8NotBetween(String value1, String value2) {
            addCriterion("src8 not between", value1, value2, "src8");
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

        public Criteria andSeqIsNull() {
            addCriterion("seq is null");
            return (Criteria) this;
        }

        public Criteria andSeqIsNotNull() {
            addCriterion("seq is not null");
            return (Criteria) this;
        }

        public Criteria andSeqEqualTo(Integer value) {
            addCriterion("seq =", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotEqualTo(Integer value) {
            addCriterion("seq <>", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThan(Integer value) {
            addCriterion("seq >", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThanOrEqualTo(Integer value) {
            addCriterion("seq >=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThan(Integer value) {
            addCriterion("seq <", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThanOrEqualTo(Integer value) {
            addCriterion("seq <=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqIn(List<Integer> values) {
            addCriterion("seq in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotIn(List<Integer> values) {
            addCriterion("seq not in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqBetween(Integer value1, Integer value2) {
            addCriterion("seq between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotBetween(Integer value1, Integer value2) {
            addCriterion("seq not between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
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