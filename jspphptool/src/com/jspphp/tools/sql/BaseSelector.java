package com.jspphp.tools.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * SQL动态封装工具
 * 
 * simple：：
 * 
 * BaseSelector select = new BaseSelector(); select.setCommand("select * from
 * stat_user "); select.createCriteria().andEqualTo("org_code", "YZ1201")
 * .andGreaterThanOrEqualTo("day_code", "2009-01-01")
 * .andLessThanOrEqualTo("day_code", "2009-03-31"); List<Map> rowset =
 * SqlTemplate.executeQuery(select.toSql(),new Object[] {});
 * 
 * output:select * from stat_user where org_code = 'YZ1201' and day_code >=
 * '2009-01-01' and day_code <= '2009-03-31'
 * 
 */
public class BaseSelector {

	/**
	 * sql语句
	 */
	private String command;
	/**
	 * 排序字段
	 */
	protected String orderByClause;

	/**
	 * sql条件
	 */
	protected List<Criteria> oredCriteria;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public BaseSelector() {
		oredCriteria = new ArrayList<Criteria>();
	}

	protected BaseSelector(BaseSelector example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * 生成最终sql语句
	 */
	public String toSql() {
		if (oredCriteria == null || oredCriteria.size() <= 0)
			return command;

		StringBuffer sqlAll = new StringBuffer();
		sqlAll.append(command);
		if (command != null && command.toUpperCase().indexOf(" WHERE ") == -1)
			sqlAll.append(" WHERE ");
		else
			sqlAll.append(" AND ");
		for (Criteria cri : oredCriteria) {
			if (!cri.isValid())
				continue;
			sqlAll.append("(");
			StringBuffer sql = new StringBuffer();
			criteriaWithoutValueSql(sql, cri.criteriaWithoutValue);
			criteriaWithSingleValueSql(sql, cri.criteriaWithSingleValue);
			criteriaWithListValueSql(sql, cri.criteriaWithListValue);
			criteriaWithBetweenValueSql(sql, cri.criteriaWithBetweenValue);
			sqlAll.append(sql.toString());
			sqlAll.append(")");
			sqlAll.append(" or ");
		}
		return sqlAll.substring(0, sqlAll.length() - 4);

	}

	@SuppressWarnings("unchecked")
	private String criteriaWithoutValueSql(StringBuffer sql, List list) {
		if (list == null)
			return "";

		int n = list.size();
		for (int i = 0; i < n; i++) {
			sql.append(list.get(i));
			if (i < n - 1)
				sql.append(" and ");
		}

		return sql.toString();
	}

	@SuppressWarnings("unchecked")
	private String criteriaWithSingleValueSql(StringBuffer sql, List list) {
		if (list == null)
			return "";
		if (sql.length() > 0 && list.size() > 0)
			sql.append(" and ");
		int n = list.size();
		for (int i = 0; i < n; i++) {
			Map map = (Map) list.get(i);
			sql.append(map.get("condition")).append(map.get("value"));
			if (i < n - 1)
				sql.append(" and ");
		}
		return sql.toString();
	}

	@SuppressWarnings("unchecked")
	private String criteriaWithListValueSql(StringBuffer sql, List list) {
		if (list == null)
			return "";
		if (sql.length() > 0 && list.size() > 0)
			sql.append(" and ");
		int n = list.size();
		for (int i = 0; i < n; i++) {
			Map map = (Map) list.get(i);
			sql.append(map.get("condition")).append(
					"(" + join((Collection) map.get("values"), ",") + ")");
			if (i < n - 1)
				sql.append(" and ");
		}
		return sql.toString();
	}

	@SuppressWarnings("unchecked")
	private String criteriaWithBetweenValueSql(StringBuffer sql, List list) {
		if (list == null)
			return "";
		if (sql.length() > 0 && list.size() > 0)
			sql.append(" and ");
		int n = list.size();
		for (int i = 0; i < n; i++) {
			Map map = (Map) list.get(i);
			sql.append(map.get("condition")).append(
					join((Collection) map.get("values"), " and "));
			if (i < n - 1)
				sql.append(" and ");
		}
		return sql.toString();
	}

	@SuppressWarnings("unchecked")
	private String join(Collection list, String spe) {
		if (list == null)
			return "";
		Object array[] = list.toArray();
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			buff.append(array[i]);
			if (i < array.length - 1)
				buff.append(spe);
		}
		return buff.toString();
	}

	/**
	 * 顺序排序
	 * 
	 * @param field
	 */
	public void setOrderByClauseAsc(String field) {
		this.orderByClause = getFieldName(field) + " ASC";
	}

	/**
	 * 倒序排序
	 * 
	 * @param field
	 */
	public void setOrderByClauseDesc(String field) {
		this.orderByClause = getFieldName(field) + " DESC";
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * or 条件
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * 创建条件对象
	 */
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

	/**
	 * 清除条件
	 */
	public void clear() {
		oredCriteria.clear();
	}

	static String getFieldName(String field) {

		if (field == null) {
			throw new RuntimeException(field + " cannot be null");
		}
		return field.toUpperCase();
		//
		//
		// Pattern pattern = Pattern.compile("[A-Z]{1}");
		// Matcher m = pattern.matcher(field);
		// StringBuffer sbr = new StringBuffer();
		// while(m.find())
		// m.appendReplacement(sbr, "_"+m.group());
		// m.appendTail(sbr);
		// return sbr.toString().toUpperCase();
	}

	/**
	 * 查询条件
	 */
	public static class Criteria {
		protected List<String> criteriaWithoutValue;

		protected List<Map<String, Object>> criteriaWithSingleValue;

		protected List<Map<String, Object>> criteriaWithListValue;

		protected List<Map<String, Object>> criteriaWithBetweenValue;

		protected Criteria() {
			super();
			criteriaWithoutValue = new ArrayList<String>();
			criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
			criteriaWithListValue = new ArrayList<Map<String, Object>>();
			criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
		}

		public boolean isValid() {
			return criteriaWithoutValue.size() > 0
					|| criteriaWithSingleValue.size() > 0
					|| criteriaWithListValue.size() > 0
					|| criteriaWithBetweenValue.size() > 0;
		}

		public List<String> getCriteriaWithoutValue() {
			return criteriaWithoutValue;
		}

		public List<Map<String, Object>> getCriteriaWithSingleValue() {
			return criteriaWithSingleValue;
		}

		public List<Map<String, Object>> getCriteriaWithListValue() {
			return criteriaWithListValue;
		}

		public List<Map<String, Object>> getCriteriaWithBetweenValue() {
			return criteriaWithBetweenValue;
		}

		protected void addCriterion(String condition) {
			if (condition == null || "".equals(condition)) {
				return;
			}
			criteriaWithoutValue.add(condition);
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null || "".equals(value)) {
				return;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("value", value);
			criteriaWithSingleValue.add(map);
		}

		protected void addCriterion(String condition,
				List<? extends Object> values, String property) {
			if (values == null || values.size() == 0) {
				return;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("values", values);
			criteriaWithListValue.add(map);
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				return;
			}
			List<Object> list = new ArrayList<Object>();
			list.add(value1);
			list.add(value2);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("values", list);
			criteriaWithBetweenValue.add(map);
		}

		public Criteria andIsNull(String field) {
			addCriterion(getFieldName(field) + " is null");
			return this;
		}

		public Criteria andIsNotNull(String field) {
			addCriterion(getFieldName(field) + " is not null");
			return this;
		}

		public Criteria andEqualTo(String field, String value) {
			addCriterion(getFieldName(field) + " =", quoteStr(value), field);
			return this;
		}

		public Criteria andNotEqualTo(String field, String value) {
			addCriterion(getFieldName(field) + " <>", quoteStr(value), field);
			return this;
		}

		public Criteria andGreaterThan(String field, String value) {
			addCriterion(getFieldName(field) + " >", quoteStr(value), field);
			return this;
		}

		public Criteria andGreaterThanOrEqualTo(String field, String value) {
			addCriterion(getFieldName(field) + " >=", quoteStr(value), field);
			return this;
		}

		public Criteria andLessThan(String field, String value) {
			addCriterion(getFieldName(field) + " <", quoteStr(value), field);
			return this;
		}

		public Criteria andLessThanOrEqualTo(String field, String value) {
			addCriterion(getFieldName(field) + " <=", quoteStr(value), field);
			return this;
		}

		public Criteria andLike(String field, String value) {
			addCriterion(getFieldName(field) + " like", quoteStr(value), field);
			return this;
		}

		public Criteria andNotLike(String field, String value) {
			addCriterion(getFieldName(field) + " not like", quoteStr(value),
					field);
			return this;
		}

		@SuppressWarnings("unchecked")
		public Criteria andIn(String field, List<String> values) {
			List vs = new ArrayList();
			for (String string : values) {
				vs.add(quoteStr(string));
			}
			addCriterion(getFieldName(field) + " in", vs, field);

			return this;
		}

		@SuppressWarnings("unchecked")
		public Criteria andNotIn(String field, List<String> values) {
			List vs = new ArrayList();
			for (String string : values) {
				vs.add(quoteStr(string));
			}
			addCriterion(getFieldName(field) + " not in", vs, field);
			return this;
		}

		public Criteria andBetween(String field, String value1, String value2) {
			addCriterion(getFieldName(field) + " between", quoteStr(value1),
					quoteStr(value2), field);
			return this;
		}

		public Criteria andNotBetween(String field, String value1, String value2) {
			addCriterion(getFieldName(field) + " not between",
					quoteStr(value1), quoteStr(value2), field);
			return this;
		}

		private String quoteStr(String str) {
			if (str == null)
				return null;
			return "'" + str + "'";
		}

	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		List days = new LinkedList();
		days.add("2008-01-01");
		days.add("2008-01-02");
		BaseSelector cri = new BaseSelector();
		cri.setCommand("select * from table where 1=1");
		cri.createCriteria().andEqualTo("org_code", "vvv").andIn("day_code",
				days);
		cri.or(cri.createCriteria().andEqualTo("status", "1"));

		System.out.println(cri.toSql());
	}

}
