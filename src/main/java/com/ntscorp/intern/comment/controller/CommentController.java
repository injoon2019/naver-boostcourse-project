package com.ntscorp.intern.comment.controller;

import com.ntscorp.intern.comment.service.CommentService;
import com.ntscorp.intern.commons.exception.customexception.InvalidInputException;
import com.ntscorp.intern.comment.dto.CommentParamDto;
import com.ntscorp.intern.commons.exception.handler.GlobalRestExceptionHandler;
import com.ntscorp.intern.reservation.dto.ReservationInfoDto;
import com.ntscorp.intern.reservation.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);
    private static final int COMMENT_MIN_LENGTH = 5;
    private static final int COMMENT_MAX_LENGTH = 400;
    private final CommentService commentService;
    private final ReservationService reservationService;

    public CommentController(CommentService commentService, ReservationService reservationService) {
        this.commentService = commentService;
        this.reservationService = reservationService;
    }

    @GetMapping("/reviewwrite")
    public String reviewWritePage(@RequestParam int reservationInfoId, Model model) {
        ReservationInfoDto reservationInfoDto = reservationService.getReservationInfoDtoById(reservationInfoId);
        model.addAttribute("reservationInfoDto", reservationInfoDto);

        return "reviewwrite";
    }

    @PostMapping("/api/reservations/{reservationInfoId}/image")
    @ResponseBody
    public int addImageFile(@PathVariable int reservationInfoId, @RequestParam(value = "imageFile") MultipartFile file) {
        int fileInfoId = commentService.insertFileInfo(file, commentService.createFileName(file));
        commentService.addImage(reservationInfoId, file);
        return fileInfoId;
    }

    @PostMapping("/api/reservations/{reservationInfoId}/comments")
    @ResponseBody
    public int addCommentDto(@PathVariable int reservationInfoId, @RequestBody CommentParamDto commentParamDto) {
        if (reservationInfoId < 0 || commentParamDto.getComment().length() <  COMMENT_MIN_LENGTH || commentParamDto.getComment().length() > COMMENT_MAX_LENGTH) {
            throw new InvalidInputException();
        }
        return commentService.addComment(reservationInfoId, commentParamDto);
    }
}
