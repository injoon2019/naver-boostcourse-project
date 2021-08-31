package com.ntscorp.intern.category.dao;

import static com.ntscorp.intern.category.dao.CatgegoryDaoSqls.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ntscorp.intern.category.dto.CategoryDto;

@Repository
public class CategoryDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private static final RowMapper<CategoryDto> PROMOTION_ROW_MAPPER = BeanPropertyRowMapper.newInstance(CategoryDto.class);
	
	public CategoryDao(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<CategoryDto> getCategoryList() {
		return jdbcTemplate.query(SELECT_ALL_CATEGORY_LIST, PROMOTION_ROW_MAPPER);
	}
}
