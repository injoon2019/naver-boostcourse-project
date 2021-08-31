package com.ntscorp.intern.display.dto;

import com.ntscorp.intern.comment.dto.CommentDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DisplayDetail {
	private double averageScore;
	private List<CommentDto> comments;
	private DisplayInfoDto displayInfo;
	private DisplayInfoImageDto displayInfoImage;
	private List<ProductImageDto> productImages;
	private List<ProductPriceDto> productPrices;
}
