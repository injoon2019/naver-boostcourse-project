package com.ntscorp.intern.display.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDto {
	private String contentType;
	private String createDate;
	private boolean deleteFlag;
	private int fileInfoId;
	private String fileName;
	private String modifyDate;
	private int productId;
	private int productImageId;
	private String saveFileName;
	private String type;
}
