package com.ntscorp.intern.comment.dao;

import com.ntscorp.intern.comment.dto.CommentDto;
import com.ntscorp.intern.comment.dto.CommentParamDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ntscorp.intern.comment.dao.CommentDaoSqls.*;

@Repository
public class CommentDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private static final RowMapper<CommentDto> COMMENTS_DTO_ROW_MAPPER = BeanPropertyRowMapper.newInstance(CommentDto.class); 
	
	public CommentDao(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<CommentDto> getCommentDtoList (int displayInfoId) {
		Map<String, Integer> parameters = new HashMap<>();
		parameters.put("displayInfoId", displayInfoId);
		
		return jdbcTemplate.query(SELECT_COMMENT_BY_DISPLAY_ID, parameters, COMMENTS_DTO_ROW_MAPPER);
	}
	
	public Double getAverageScore(int displayInfoId) {
		Map<String, Integer> parameters = new HashMap<>();
		parameters.put("displayInfoId", displayInfoId);
		
		return jdbcTemplate.queryForObject(SELECT_AVERAGE_SCORE_BY_DISPLAY_ID, parameters, Double.class);
	}

	public int insertComment(int reservationInfoId, CommentParamDto commentParamDto) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue( "comment", commentParamDto.getComment())
				.addValue( "productId", commentParamDto.getProductId())
				.addValue( "reservationInfoId", reservationInfoId)
				.addValue( "score", commentParamDto.getScore());

		jdbcTemplate.update(INSERT_RESERVATION_USER_COMMENT, parameters, keyHolder, new String[]{"id"});
		return Integer.parseInt(String.valueOf(keyHolder.getKey()));
	}

	public int insertCommentImage(int reservationInfoId, int reservationUserCommentId, int fileId) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue( "reservationInfoId", reservationInfoId)
				.addValue( "reservationUserCommentId", reservationUserCommentId)
				.addValue( "fileId", fileId);

		jdbcTemplate.update(INSERT_RESERVATION_COMMENT_IMAGE, parameters, keyHolder, new String[]{"id"});
		return Integer.parseInt(String.valueOf(keyHolder.getKey()));
	}
}
