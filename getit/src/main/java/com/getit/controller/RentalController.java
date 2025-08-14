package com.getit.controller;

import com.getit.model.Item;
import com.getit.model.Rental;
import com.getit.model.RentalStatus;
import com.getit.model.RentalType;
import com.getit.model.User;
import com.getit.repository.ItemRepository;
import com.getit.repository.RentalRepository;
import com.getit.service.UserService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
	private final RentalRepository rentalRepository;
	private final ItemRepository itemRepository;
	private final UserService userService;

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody CreateRentalRequest request) {
		User renter = userService.getCurrentUserOrThrow();
		Item item = itemRepository.findById(request.itemId).orElseThrow();
		if (!item.isApproved() || !item.isAvailable()) {
			return ResponseEntity.badRequest().body("Item not available");
		}
		LocalDateTime start = request.startDateTime;
		LocalDateTime end = request.endDateTime;
		if (!end.isAfter(start)) {
			return ResponseEntity.badRequest().body("End must be after start");
		}
		List<RentalStatus> activeStatuses = List.of(RentalStatus.APPROVED, RentalStatus.ACTIVE, RentalStatus.PENDING);
		boolean overlaps = rentalRepository.existsByItemIdAndStatusInAndStartDateTimeLessThanEqualAndEndDateTimeGreaterThanEqual(item.getId(), activeStatuses, end, start);
		if (overlaps) {
			return ResponseEntity.badRequest().body("Item already booked in this time range");
		}
		double total = calculatePrice(item, request.rentalType, start, end);
		Rental rental = Rental.builder()
			.item(item)
			.renter(renter)
			.startDateTime(start)
			.endDateTime(end)
			.rentalType(request.rentalType)
			.totalPrice(total)
			.status(RentalStatus.PENDING)
			.build();
		Rental saved = rentalRepository.save(rental);
		return ResponseEntity.ok(saved);
	}

	private double calculatePrice(Item item, RentalType type, LocalDateTime start, LocalDateTime end) {
		if (type == RentalType.HOURLY) {
			long hours = Math.max(1, Duration.between(start, end).toHours());
			return hours * item.getHourlyPrice();
		} else {
			long days = Math.max(1, Duration.between(start, end).toDays());
			return days * item.getDailyPrice();
		}
	}

	@Data
	static class CreateRentalRequest {
		private Long itemId;
		private LocalDateTime startDateTime;
		private LocalDateTime endDateTime;
		private RentalType rentalType;
	}
}