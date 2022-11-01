package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDtoFiltered {

	private String name;
	private String a2;
	private Integer numericCode;
    private Byte active;
	private int displayOrder;
}
