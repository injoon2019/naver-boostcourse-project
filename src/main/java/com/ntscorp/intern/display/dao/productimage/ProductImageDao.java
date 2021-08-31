package com.ntscorp.intern.display.dao.productimage;

import static com.ntscorp.intern.display.dao.productimage.ProductImageDaoSqls.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ntscorp.intern.display.dto.ProductImageDto;

@Repository
public class ProductImageDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private static final RowMapper<ProductImageDto> PRODUCT_IMAGE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(ProductImageDto.class); 
	
	public ProductImageDao (DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<ProductImageDto> getProductImageDtoList(int displayInfoId) {
		Map<String, Integer> parameters = new HashMap<>();
		parameters.put("displayInfoId", displayInfoId);
		
		return jdbcTemplate.query(SELECT_PRODUCT_IMAGE_LIST_BY_DISPLAY_INFO_ID, parameters,	PRODUCT_IMAGE_ROW_MAPPER);
	}
}
