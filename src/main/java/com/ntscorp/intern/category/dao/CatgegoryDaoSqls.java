package com.ntscorp.intern.category.dao;

public class CatgegoryDaoSqls {
	public static final String SELECT_ALL_CATEGORY_LIST = "SELECT category.id, category.name, COUNT(*) AS count "
			+ "FROM category "
			+ "JOIN product ON category.id = product.category_id "
			+ "GROUP BY category.id";
}
