package com.sitproject.sit.repository;

import com.sitproject.sit.entity.Temp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempRepository extends JpaRepository<Temp, Integer> {
}
