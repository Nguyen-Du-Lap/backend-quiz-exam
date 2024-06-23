package com.nlu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nlu.model.entity.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long>{

}
