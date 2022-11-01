package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.custom.CountryDropDown;
import com.example.dto.CountryDto;
import com.example.dto.CountryDtoFiltered;
import com.example.dto.CountryFilterRequest;
import com.example.entity.Country;
import com.example.repository.CountryRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CountryService {

	private CountryRepository countryRepository;

	public List<CountryDropDown> getCountries() {
		log.info("Country Service");
		return countryRepository.getCountryNames();
	}

	public List<CountryDto> getCountriesByExample(CountryDto countryDto) {
		// proble an object with example data
		Country probe = new Country();
		
		probe.setA2(countryDto.getA2());
		probe.setName(countryDto.getName());
		probe.setNumericCode(Objects.nonNull(countryDto.getNumericCode()) ? countryDto.getNumericCode() : -1);
		
		// example matcher
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnorePaths("status","displayOrder","active", probe.getNumericCode() == -1 ? "numericCode" : "")
				.withIgnoreCase("name","a2")
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); 
		
		// query the exmple
		return countryRepository.findAll(Example.of(probe,matcher)).stream()
				.map(c-> new CountryDto(c.getName(), c.getA2(), c.getNumericCode())).toList();
	}

	public List<CountryDtoFiltered> getcountries(CountryFilterRequest filter) {

		Byte active = filter.getActive();
		String name = filter.getName();
		List<Integer> numericCodes = filter.getNumericCodes();
		int doFrom = filter.getDisplayOrderFrom();
		int doTo = filter.getDisplayOrderTo();
		
		List<Country> filterCountries = countryRepository.findAll(new Specification<Country>() {
	
			private static final long serialVersionUID = 5628424924472135187L;

			@Override
			public Predicate toPredicate(Root<Country> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				if (Objects.nonNull(active)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), active)));
				}
				
				if (Objects.nonNull(name)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("name"), "%" + name + "%")));
				}
				
				if (Objects.nonNull(numericCodes) && !numericCodes.isEmpty()) {	
					predicates.add(criteriaBuilder.and(root.get("numericCode").in(numericCodes)));	
				}
				
				if (doFrom > 0 || doTo > 0) {
					
					Predicate ftPredicate = null;
					
					if (doFrom > 0 && doTo > 0) {
						ftPredicate = criteriaBuilder.between(root.get("displayOrder"), doFrom, doTo);
					}else if (doFrom > 0) {
						ftPredicate = criteriaBuilder.equal(root.get("displayOrder"), doFrom);
					} else if (doTo > 0) {
						ftPredicate = criteriaBuilder.equal(root.get("displayOrder"), doTo);
					}
					
					predicates.add(criteriaBuilder.and(ftPredicate));
				}
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		
		return filterCountries.stream()
				.map(c-> new CountryDtoFiltered(c.getName(), c.getA2(), c.getNumericCode(),c.getActive(),c.getDisplayOrder())).toList();
	}

}
