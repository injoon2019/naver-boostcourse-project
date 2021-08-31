package com.ntscorp.intern.comment.dao;

import static com.ntscorp.intern.comment.dao.CommentImageDaoSqls.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.ntscorp.intern.comment.dto.CommentImageDto;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class CommentImageDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private static final RowMapper<CommentImageDto> COMMNET_IMAGE_DTO_ROW_MAPPER = BeanPropertyRowMapper.newInstance(CommentImageDto.class); 
	
	public CommentImageDao(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<CommentImageDto> getCommentImageDtoList (int commentId) {
		Map<String, Integer> parameters = new HashMap<>();
		parameters.put("commentId", commentId);
		
		return jdbcTemplate.query(SELECT_COMMENT_IMAGE_BY_COMMENT_ID, parameters, COMMNET_IMAGE_DTO_ROW_MAPPER);
	}

	public int insertFileInfo(MultipartFile file, String fileName, String saveFileName) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue( "contentType", file.getContentType())
				.addValue( "fileName", fileName)
				.addValue( "saveFileName", saveFileName);

		jdbcTemplate.update(INSERT_FILE_INFO, parameters, keyHolder, new String[]{"id"});
		return Integer.parseInt(String.valueOf(keyHolder.getKey()));
	}
}
