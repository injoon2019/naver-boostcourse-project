package com.ntscorp.intern.reservation.dao;

import com.ntscorp.intern.display.dao.displayinfo.DisplayInfoDao;
import com.ntscorp.intern.reservation.dto.ReservationInfoDto;
import com.ntscorp.intern.reservation.dto.ReservationResponseDto;
import com.ntscorp.intern.reservation.dto.request.ReservationParamDto;
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

import static com.ntscorp.intern.reservation.dao.ReservationDaoSqls.*;

@Repository
public class ReservationDao {
    private static final RowMapper<ReservationInfoDto> RESERVATION_INFO_DTO_ROW_MAPPER = BeanPropertyRowMapper.newInstance(ReservationInfoDto.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ReservationDao(DataSource dataSource, DisplayInfoDao displayInfoDao) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<ReservationInfoDto> selectReservationInfoDtoListByEmail(String reservationEmail) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reservationEmail", reservationEmail);

        return jdbcTemplate.query(SELECT_RESERVATION_INFO_BY_RESERVATION_EMAIL, parameters, RESERVATION_INFO_DTO_ROW_MAPPER);
    }

    public int insertReservationInfo(ReservationParamDto reservationParamDto) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue( "displayInfoId", reservationParamDto.getDisplayInfoId())
                .addValue( "productId",  reservationParamDto.getProductId())
                .addValue( "reservationEmail",   reservationParamDto.getReservationEmail())
                .addValue( "reservationName",  reservationParamDto.getReservationName())
                .addValue( "reservationTelephone",  reservationParamDto.getReservationTelephone())
                .addValue( "reservationYearMonthDay",  reservationParamDto.getReservationYearMonthDay())
                .addValue( "createDate",  reservationParamDto.getCreateDate())
                .addValue( "modifyDate",  reservationParamDto.getModifyDate());

        jdbcTemplate.update(INSERT_RESERVATION_INFO, parameters, keyHolder, new String[]{"id"});

        return Integer.parseInt(String.valueOf(keyHolder.getKey()));
    }

    public void insertReservationInfoPrice(Map<String, Object> priceParameters) {
        jdbcTemplate.update(INSERT_RESERVATION_INFO_PRICE, priceParameters);
    }

    public ReservationResponseDto updateReservation(int reservationId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reservationId", reservationId);
        jdbcTemplate.update(UPDATE_RESERVATION_BY_ID, parameters);

        return new ReservationResponseDto();
    }

    public int countReservationEmail(String reservationEmail) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reservationEmail", reservationEmail);

        return jdbcTemplate.queryForObject(CHECK_RESERVATION_EMAIL_EXIST, parameters, Integer.class);
    }

    public ReservationInfoDto selectReservationInfoDtoById(int reservationInfoId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reservationInfoId", reservationInfoId);

        return jdbcTemplate.queryForObject(SELECT_RESERVATION_INFO_BY_ID, parameters, RESERVATION_INFO_DTO_ROW_MAPPER);
    }
}
