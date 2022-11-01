package com.example.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CityDto;
import com.example.service.CityService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/cities")
@AllArgsConstructor
public class CityController {

	private CityService cityService;

	@GetMapping("/{countryId}")
	public ResponseEntity<List<String>> getCitiesForCountry(@PathVariable Integer countryId) {
		return ResponseEntity.ok(cityService.getcities(countryId));
	}

	@GetMapping
	public ResponseEntity<List<CityDto>> getCities() {

		return ResponseEntity.ok(cityService.getcities());
	}
	
	@GetMapping("page")
	public ResponseEntity<Page<CityDto>> getCitiesPage(
			@RequestParam("index") Integer index,//
			@RequestParam("page_size") Integer pageSize
			) {

		return ResponseEntity.ok(cityService.getcities(index,pageSize));
	}

}
