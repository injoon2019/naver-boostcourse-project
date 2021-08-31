package com.ntscorp.intern.display.dao.displayinfo;

import static com.ntscorp.intern.display.dao.displayinfo.DisplayInfoDaoSqls.*;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ntscorp.intern.display.dto.DisplayInfoDto;

@Repository
public class DisplayInfoDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private static final RowMapper<DisplayInfoDto> DISPLAY_INFO_ROW_MAPPER = BeanPropertyRowMapper.newInstance(DisplayInfoDto.class); 
	
	public DisplayInfoDao (DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public DisplayInfoDto getDisplayInfo(int displayInfoId) {
		Map<String, Integer> parameters = new HashMap<>();
		parameters.put("displayInfoId", displayInfoId);
		
		return jdbcTemplate.queryForObject(SELECT_DISPLAY_INFO_BY_DISPLAY_INFO_ID, parameters, DISPLAY_INFO_ROW_MAPPER);
	}
}
