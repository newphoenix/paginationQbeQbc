package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.custom.CountryDropDown;
import com.example.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>,JpaSpecificationExecutor<Country> {

  @Query("SELECT c.id as id, c.name as name FROM Country c")
  List<CountryDropDown> getCountryNames();

}
