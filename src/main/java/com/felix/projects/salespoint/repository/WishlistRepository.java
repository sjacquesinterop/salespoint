package com.felix.projects.salespoint.repository;

import com.felix.projects.salespoint.entities.WishListEntity;
import com.felix.projects.salespoint.entities.WishListId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/** The interface Wishlist repository. */
@Repository
public interface WishlistRepository extends JpaRepository<WishListEntity, WishListId> {

  List<WishListEntity> findWishListEntityByIdUserIdEquals(Integer userId);

  List<WishListEntity> findWishListEntityByIdItemIdEquals(Integer itemId);
}
