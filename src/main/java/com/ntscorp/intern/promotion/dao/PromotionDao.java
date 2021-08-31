package com.ntscorp.intern.promotion.dao;

import static com.ntscorp.intern.promotion.dao.PromotionDaoSqls.*;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ntscorp.intern.promotion.dto.PromotionDto;

@Repository
public class PromotionDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private static final RowMapper<PromotionDto> PROMOTION_ROW_MAPPER = BeanPropertyRowMapper.newInstance(PromotionDto.class);
	
	public PromotionDao(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<PromotionDto> getPromotionDtoList() {
		return jdbcTemplate.query(SELECT_ALL_PROMOTION_LIST, PROMOTION_ROW_MAPPER);
	}
}
