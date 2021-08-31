package com.ntscorp.intern.display.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ntscorp.intern.comment.dao.CommentDao;
import com.ntscorp.intern.comment.dao.CommentImageDao;
import com.ntscorp.intern.display.dao.displayinfo.DisplayInfoDao;
import com.ntscorp.intern.display.dao.displayinfoimage.DisplayInfoImageDao;
import com.ntscorp.intern.display.dao.productimage.ProductImageDao;
import com.ntscorp.intern.display.dao.productprice.ProductPriceDao;
import com.ntscorp.intern.comment.dto.CommentDto;
import com.ntscorp.intern.display.dto.DisplayDetail;

@Service
public class DisplayServiceImpl implements DisplayService{
	private static final int EMAIL_SHOW_COUNT = 4;
	private static final String EMAIL_MASK_STARS = "****";
	
	private final CommentDao commentDao;
	private final CommentImageDao commentImagesDao;
	private final DisplayInfoDao displayInfoDao;
	private final DisplayInfoImageDao displayInfoImageDao;
	private final ProductImageDao productImageDao;
	private final ProductPriceDao productPriceDao;
	
	public DisplayServiceImpl(CommentDao commentDao, CommentImageDao commentImagesDao, DisplayInfoDao displayInfoDao, 
			DisplayInfoImageDao displayInfoImageDao, ProductImageDao productImageDao, ProductPriceDao productPriceDao) {
		this.commentDao = commentDao;
		this.commentImagesDao = commentImagesDao;
		this.displayInfoDao = displayInfoDao;
		this.displayInfoImageDao = displayInfoImageDao;
		this.productImageDao = productImageDao;
		this.productPriceDao = productPriceDao;
	}
	
	@Override
	public DisplayDetail getDisplayDetail(int displayInfoId) {
		DisplayDetail displayDetail = new DisplayDetail();
		
		List<CommentDto> commentDtoList = commentDao.getCommentDtoList(displayInfoId);
		
		for (CommentDto commentDto : commentDtoList) {
			int commentId = commentDto.getCommentId();
			commentDto.setCommentImages(commentImagesDao.getCommentImageDtoList(commentId));
			commentDto.setReservationEmail(maskEmail(commentDto.getReservationEmail()));
		}
				
		displayDetail.setComments(commentDtoList);
		displayDetail.setAverageScore(commentDao.getAverageScore(displayInfoId));
		displayDetail.setDisplayInfo(displayInfoDao.getDisplayInfo(displayInfoId));
		displayDetail.setDisplayInfoImage(displayInfoImageDao.getDisplayInfoImage(displayInfoId));
		displayDetail.setProductImages(productImageDao.getProductImageDtoList(displayInfoId));
		displayDetail.setProductPrices(productPriceDao.getProductPricesDtoList(displayInfoId));
		
		return displayDetail;
	}
	
	public String maskEmail(String email) {
		StringBuffer emailStringBuffer = new StringBuffer(email);
		emailStringBuffer.delete(EMAIL_SHOW_COUNT, emailStringBuffer.length());
		return emailStringBuffer.append(EMAIL_MASK_STARS).toString();
	}
}
