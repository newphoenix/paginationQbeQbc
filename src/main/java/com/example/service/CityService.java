package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dto.CityDto;
import com.example.repository.CityRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class CityService {

	private CityRepository cityRepository;

	public List<String> getcities(Integer countryId) {
		log.info("City service");
		return cityRepository.findCitiesByCountryId(countryId);
	}
	
	public List<CityDto> getcities() {		
		return cityRepository.findCities();
	}

	public Page<CityDto> getcities(Integer index, Integer pageSize) {
	
		 Pageable pageable = PageRequest.of(index, pageSize,
				 Sort.by("displayOrder").and(Sort.by("created").descending())
				 );
		
		return cityRepository.findCities(pageable);
	}

}
