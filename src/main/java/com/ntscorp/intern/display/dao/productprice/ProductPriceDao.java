package com.ntscorp.intern.display.dao.productprice;

import static com.ntscorp.intern.display.dao.productprice.ProductPriceDaoSqls.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ntscorp.intern.display.dto.ProductPriceDto;

@Repository
public class ProductPriceDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private static final RowMapper<ProductPriceDto> PRODUCT_PRICE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(ProductPriceDto.class); 
	
	public ProductPriceDao(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<ProductPriceDto> getProductPricesDtoList(int displayInfoId) {
		Map<String, Integer> parameters = new HashMap<>();
		parameters.put("displayInfoId", displayInfoId);
		
		return jdbcTemplate.query(SELECT_PRODUCT_PRICE_BY_DISPLAY_INFO_ID, parameters, PRODUCT_PRICE_ROW_MAPPER);
	}
}
