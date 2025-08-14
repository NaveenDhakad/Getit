package com.getit.service;

import com.getit.model.Item;
import com.getit.model.User;
import com.getit.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;

	@Transactional
	public Item createItem(Item item, User owner) {
		item.setOwner(owner);
		item.setApproved(false);
		item.setAvailable(true);
		if (item.getLatitude() == null) item.setLatitude(owner.getLatitude());
		if (item.getLongitude() == null) item.setLongitude(owner.getLongitude());
		return itemRepository.save(item);
	}

	@Transactional
	public Item approveItem(Long id, boolean approved) {
		Item item = itemRepository.findById(id).orElseThrow();
		item.setApproved(approved);
		return itemRepository.save(item);
	}

	public List<Item> homeApprovedItems() {
		return itemRepository.findByApprovedTrueAndAvailableTrue();
	}

	public List<Item> searchApproved(String text, String category) {
		return itemRepository.searchApprovedAvailable(text, category);
	}

	public List<Item> nearest(double lat, double lng, double radiusKm, int limit) {
		List<Object[]> rows = itemRepository.findNearestApproved(lat, lng, radiusKm, limit);
		return rows.stream().map(r -> (Item) r[0]).toList();
	}

	public List<Item> nearestWithFallback(double lat, double lng, double radiusKm, int limit) {
		List<Object[]> rows = itemRepository.findNearestApproved(lat, lng, radiusKm, limit);
		if (!rows.isEmpty()) {
			return rows.stream().map(r -> (Item) r[0]).toList();
		}
		// fallback: get all approved sorted by distance and limit
		List<Item> all = itemRepository.findByApprovedTrueAndAvailableTrue();
		return all.stream()
			.sorted(Comparator.comparingDouble(i -> haversineKm(lat, lng, Optional.ofNullable(i.getLatitude()).orElse(0.0), Optional.ofNullable(i.getLongitude()).orElse(0.0))))
			.limit(limit)
			.toList();
	}

	private double haversineKm(double lat1, double lon1, double lat2, double lon2) {
		double R = 6371.0;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return R * c;
	}
}