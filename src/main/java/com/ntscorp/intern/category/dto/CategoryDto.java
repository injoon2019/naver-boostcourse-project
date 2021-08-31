package com.ntscorp.intern.category.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
	int id;
	String name;
	int count;
	
	public CategoryDto() {
	}

	public CategoryDto(Integer id, String name, Integer count) {
		this.id = id;
		this.name = name;
		this.count = count;
	}
}
