package com.getit.repository;

import com.getit.model.Rental;
import com.getit.model.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
	List<Rental> findByItemIdAndStatusIn(Long itemId, List<RentalStatus> statuses);
	boolean existsByItemIdAndStatusInAndStartDateTimeLessThanEqualAndEndDateTimeGreaterThanEqual(Long itemId, List<RentalStatus> statuses, LocalDateTime start, LocalDateTime end);
}