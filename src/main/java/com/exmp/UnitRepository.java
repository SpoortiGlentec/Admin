package com.exmp;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UnitRepository extends JpaRepository<Unit, Integer> {
	List<Unit> findByStatus(String status);
	
}
