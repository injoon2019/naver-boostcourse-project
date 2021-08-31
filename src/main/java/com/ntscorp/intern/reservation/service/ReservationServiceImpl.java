package com.ntscorp.intern.reservation.service;

import com.ntscorp.intern.commons.validator.ReservationValidator;
import com.ntscorp.intern.display.dao.displayinfo.DisplayInfoDao;
import com.ntscorp.intern.reservation.dao.ReservationDao;
import com.ntscorp.intern.reservation.dto.ReservationInfoDetailDto;
import com.ntscorp.intern.reservation.dto.ReservationInfoDto;
import com.ntscorp.intern.reservation.dto.ReservationResponseDto;
import com.ntscorp.intern.reservation.dto.request.ReservationParamDto;
import com.ntscorp.intern.reservation.dto.request.ReservationParamPriceDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationDao reservationDao;
    private final DisplayInfoDao displayInfoDao;
    private final ReservationValidator reservationValidator;

    public ReservationServiceImpl(ReservationDao reservationDao, DisplayInfoDao displayInfoDao, ReservationValidator reservationValidator) {
        this.reservationDao = reservationDao;
        this.displayInfoDao = displayInfoDao;
        this.reservationValidator = reservationValidator;
    }

    @Override
    public ReservationInfoDetailDto getReservationDetailDtoByEmail(String reservationEmail) {
        ReservationInfoDetailDto reservationDetailDto = new ReservationInfoDetailDto();

        List<ReservationInfoDto> reservationInfoDtoList = reservationDao.selectReservationInfoDtoListByEmail(reservationEmail);
        for (ReservationInfoDto reservationInfoDto : reservationInfoDtoList) {
            reservationInfoDto.setDisplayInfo(displayInfoDao.getDisplayInfo(reservationInfoDto.getDisplayInfoId()));
        }

        reservationDetailDto.setReservations(reservationInfoDtoList);
        reservationDetailDto.setSize(reservationInfoDtoList.size());

        return reservationDetailDto;
    }

    @Override
    public ReservationInfoDto getReservationInfoDtoById(int reservationInfoId) {
        ReservationInfoDto reservationInfoDto = reservationDao.selectReservationInfoDtoById(reservationInfoId);
        reservationInfoDto.setDisplayInfo(displayInfoDao.getDisplayInfo(reservationInfoDto.getDisplayInfoId()));
        return reservationInfoDto;
    }

    @Override
    @Transactional
    public ReservationResponseDto addReservationInfo(ReservationParamDto reservationParamDto) {
        int generatedReservationInfoId = reservationDao.insertReservationInfo(reservationParamDto);

        List<ReservationParamPriceDto> reservationParamPriceDtoList = reservationParamDto.getPrices();
        Map<String, Object> priceParameters = new HashMap<>();

        for (ReservationParamPriceDto reservationParamPriceDto : reservationParamPriceDtoList) {
            priceParameters.put("count", reservationParamPriceDto.getCount());
            priceParameters.put("productPriceId", reservationParamPriceDto.getProductPriceId());
            priceParameters.put("reservationInfoId", generatedReservationInfoId);
            reservationDao.insertReservationInfoPrice(priceParameters);
        }

        return createRandomReservation(reservationParamDto, generatedReservationInfoId);
    }

    @Override
    public ReservationResponseDto cancelReservation(int reservationId) {
        return reservationDao.updateReservation(reservationId);
    }

    @Override
    @Transactional
    public boolean existReservationEmail(String reservationEmail) {
        return reservationDao.countReservationEmail(reservationEmail) > 0;
    }

    public ReservationResponseDto createRandomReservation(ReservationParamDto reservationParamDto, int reservationInfoId) {
        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();

        reservationResponseDto.setCancelYn(false);
        reservationResponseDto.setCreateDate(reservationParamDto.getCreateDate());
        reservationResponseDto.setDisplayInfoId(reservationParamDto.getDisplayInfoId());
        reservationResponseDto.setModifyDate(reservationParamDto.getModifyDate());
        reservationResponseDto.setPrices(reservationParamDto.getPrices());
        reservationResponseDto.setProductId(reservationParamDto.getProductId());
        reservationResponseDto.setReservationDate(reservationParamDto.getReservationYearMonthDay());
        reservationResponseDto.setReservationInfoId(reservationInfoId);
        reservationResponseDto.setReservationEmail(reservationParamDto.getReservationEmail());
        reservationResponseDto.setReservationName(reservationParamDto.getReservationName());
        reservationResponseDto.setReservationTelephone(reservationParamDto.getReservationTelephone());

        return reservationResponseDto;
    }

    public boolean validReservationParamDto(ReservationParamDto reservationParamDto) {
        String name = reservationParamDto.getReservationName();
        String email = reservationParamDto.getReservationEmail();
        String phoneNumber = reservationParamDto.getReservationTelephone();
        return reservationValidator.validName(name) && reservationValidator.validEmail(email) && reservationValidator.validPhoneNumber(phoneNumber);
    }

    public boolean validReservationId(int reservationId) {
        return reservationId > 0;
    }
}
