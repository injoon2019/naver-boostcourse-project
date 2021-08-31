package com.ntscorp.intern.comment.service;

import com.ntscorp.intern.comment.dto.CommentParamDto;
import org.springframework.web.multipart.MultipartFile;

public interface CommentService {
    int addComment(int reservationInfoId, CommentParamDto commentDto);
    String addImage(int reservationInfoId, MultipartFile file);
    int insertFileInfo(MultipartFile file, String createdFileName);
    String extractFileName(String fileName);
    String createFileName(MultipartFile file);
    String getFileExtensionName(String fileName);
    String getCurrentDateTime();
}
