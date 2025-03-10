package com.exmp;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Integer> {
	List<Line> findByStatus(String status);
	
}
