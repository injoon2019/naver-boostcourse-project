package com.ntscorp.intern.product.dao;

import static com.ntscorp.intern.product.dao.ProductDaoSqls.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ntscorp.intern.product.dto.ProductDto;


@Repository
public class ProductDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private static final RowMapper<ProductDto> PRODUCT_ROW_MAPPER = BeanPropertyRowMapper.newInstance(ProductDto.class); 
	
	public ProductDao(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<ProductDto> selectByCategoryId(int categoryId, int offset) {
		Map<String, Integer> parameters = new HashMap<>();
		parameters.put("categoryId", categoryId);
		parameters.put("offset", offset);
		
		return jdbcTemplate.query(SELECT_ALL_PRODUCT_BY_CATEGORY_ID, parameters, PRODUCT_ROW_MAPPER);
	}
	
	public List<ProductDto> selectAllCategoryList(int offset) {
		Map<String, Integer> parameters = new HashMap<>();
		parameters.put("offset", offset);
		
		return jdbcTemplate.query(SELECT_ALL_PRODUCT, parameters, PRODUCT_ROW_MAPPER);
	}
	
	public int getTotalCountByCategoryId(int categoryId) {
		Map<String, Integer> parameters = new HashMap<>();
		parameters.put("categoryId", categoryId);
		
		return jdbcTemplate.queryForObject(SELECT_PRODUCT_TOTAL_COUNT_BY_CATEGORY_ID, parameters, Integer.class);
	}
	
	public int getTotalCount() {
		return jdbcTemplate.queryForObject(SELECT_PRODUCT_TOTAL_COUNT, Collections.emptyMap(), Integer.class);
	}
}
