package com.getit.controller;

import com.getit.model.Item;
import com.getit.model.Role;
import com.getit.model.User;
import com.getit.service.ItemService;
import com.getit.service.UserService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;
	private final UserService userService;

	@GetMapping("/home")
	public List<Item> home() {
		return itemService.homeApprovedItems();
	}

	@GetMapping("/search")
	public List<Item> search(@RequestParam(required = false) String text, @RequestParam(required = false) String category) {
		return itemService.searchApproved(text, category);
	}

	@GetMapping("/nearest")
	public List<Item> nearest(@RequestParam double lat, @RequestParam double lng, @RequestParam(defaultValue = "10") double radiusKm, @RequestParam(defaultValue = "20") int limit) {
		return itemService.nearestWithFallback(lat, lng, radiusKm, limit);
	}

	@PostMapping
	public ResponseEntity<Item> create(@Valid @RequestBody CreateItemRequest request) {
		User current = userService.getCurrentUserOrThrow();
		Item item = Item.builder()
			.title(request.title)
			.description(request.description)
			.category(request.category)
			.imageUrl(request.imageUrl)
			.hourlyPrice(request.hourlyPrice)
			.dailyPrice(request.dailyPrice)
			.latitude(request.latitude)
			.longitude(request.longitude)
			.build();
		Item saved = itemService.createItem(item, current);
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/{id}/approve")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> approve(@PathVariable Long id, @RequestBody Map<String, Boolean> payload) {
		boolean approved = payload.getOrDefault("approved", true);
		Item updated = itemService.approveItem(id, approved);
		return ResponseEntity.ok(Map.of("id", updated.getId(), "approved", updated.isApproved()));
	}

	@Data
	static class CreateItemRequest {
		private String title;
		private String description;
		private String category;
		private String imageUrl;
		private Double hourlyPrice;
		private Double dailyPrice;
		private Double latitude;
		private Double longitude;
	}
}