package com.getit.repository;

import com.getit.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByApprovedTrueAndAvailableTrue();

	@Query("SELECT i FROM Item i WHERE i.approved = true AND i.available = true AND (:category IS NULL OR i.category = :category) AND (:text IS NULL OR LOWER(i.title) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(i.description) LIKE LOWER(CONCAT('%', :text, '%'))) ")
	List<Item> searchApprovedAvailable(@Param("text") String text, @Param("category") String category);

	@Query(value = "SELECT *,(6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(latitude)))) AS distance_km FROM items WHERE approved = true AND available = true HAVING distance_km <= :radius ORDER BY distance_km LIMIT :limit", nativeQuery = true)
	List<Object[]> findNearestApproved(@Param("lat") double lat, @Param("lng") double lng, @Param("radius") double radius, @Param("limit") int limit);
}