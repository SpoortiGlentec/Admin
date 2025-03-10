package com.exmp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Integer> {

	List<Part> findByStatus(String status);
}
