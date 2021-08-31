package com.ntscorp.intern.display.dao.displayinfoimage;

import static com.ntscorp.intern.display.dao.displayinfoimage.DisplayInfoImageDaoSqls.*;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ntscorp.intern.display.dto.DisplayInfoImageDto;

@Repository
public class DisplayInfoImageDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private static final RowMapper<DisplayInfoImageDto> DISPLAY_INFO_IMAGE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(DisplayInfoImageDto.class); 
	
	public DisplayInfoImageDao (DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public DisplayInfoImageDto getDisplayInfoImage(int displayInfoId) {
		Map<String, Integer> parameters = new HashMap<>();
		parameters.put("displayInfoId", displayInfoId);
		
		return jdbcTemplate.queryForObject(SELECT_DISPLAY_INFO_IMAGE_BY_DISPLAY_INFO_ID, parameters, 
				DISPLAY_INFO_IMAGE_ROW_MAPPER);
	}
}
