package com.ntscorp.intern.comment.service;

import com.ntscorp.intern.comment.dao.CommentDao;
import com.ntscorp.intern.comment.dao.CommentImageDao;
import com.ntscorp.intern.comment.dto.CommentParamDto;
import com.ntscorp.intern.commons.exception.handler.GlobalRestExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CommentServiceImpl implements CommentService{
    private static final String IMAGE_FILE_SAVE_PATH = "c:/imageFolder/";
    private static final String IMAGE_FILE_SAVE_FOLDER = "imageFolder/";
    private static final int BYTE_BUFFER_SIZE = 1024;
    private static final DateTimeFormatter DATE_TO_MS_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

    private final CommentDao commentDao;
    private final CommentImageDao commentImageDao;

    public CommentServiceImpl (CommentDao commentDao, CommentImageDao commentImageDao) {
        this.commentDao = commentDao;
        this.commentImageDao = commentImageDao;
    }

    @Override
    @Transactional
    public int addComment(int reservationInfoId, CommentParamDto commentParamDto) {
        int reservationUserCommentId = commentDao.insertComment(reservationInfoId, commentParamDto);
        return commentDao.insertCommentImage(reservationInfoId, reservationUserCommentId, commentParamDto.getFileId());
    }

    @Override
    public String addImage(int reservationInfoId, MultipartFile file) {
        String filename = createFileName(file);
        try (
            FileOutputStream fileOutputStream = new FileOutputStream(IMAGE_FILE_SAVE_PATH + filename);
             InputStream inputStream = file.getInputStream();) {
            int readCount = 0;
            byte[] buffer = new byte[BYTE_BUFFER_SIZE];

            while((readCount = inputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer,0, readCount);
            }
        } catch (Exception e) {
            LOGGER.error("IOException log = {}", e.toString());
        }
        return filename;
    }

    @Override
    public int insertFileInfo(MultipartFile file, String createdFileName) {
        return commentImageDao.insertFileInfo(file, createdFileName, IMAGE_FILE_SAVE_FOLDER + createdFileName);
    }

    @Override
    public String extractFileName(String fileName) {
        return fileName.split("\\.")[0];
    }

    @Override
    public String createFileName(MultipartFile file) {
        return extractFileName(file.getOriginalFilename()) + getCurrentDateTime() + getFileExtensionName(file.getOriginalFilename()) ;
    }

    @Override
    public String getFileExtensionName(String fileName) {
        return "." + fileName.split("\\.")[1];
    }

    @Override
    public String getCurrentDateTime() {
        return LocalDateTime.now().format(DATE_TO_MS_FORMATTER);
    }
}
