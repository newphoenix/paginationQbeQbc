package com.example.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryFilterRequest {

	private String name;
	private Byte active;
	private List<Integer> numericCodes; // In clause
	private int displayOrderFrom;
	private int displayOrderTo;
}
