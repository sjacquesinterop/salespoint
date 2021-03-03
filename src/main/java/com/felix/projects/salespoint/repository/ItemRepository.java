package com.felix.projects.salespoint.repository;

import com.felix.projects.salespoint.dto.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Item repository.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
