package com.felix.projects.salespoint.mapper;

import com.felix.projects.salespoint.dto.WishList;
import com.felix.projects.salespoint.entities.WishListEntity;
import com.felix.projects.salespoint.entities.WishListId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WishlistMapper {

  WishlistMapper INSTANCE = Mappers.getMapper(WishlistMapper.class);

  @Mapping(target = "user", source = "id.userId")
  @Mapping(target = "item", source = "id.itemId")
  WishList toDto(WishListEntity wishList);

  @Mapping(target = "id.userId", source = "user")
  @Mapping(target = "id.itemId", source = "item")
  WishListEntity toEntity(WishList wishList);

  // TODO Added this temporarely, will need to remove it potentially
  @Mapping(target = "user", source = "userId")
  @Mapping(target = "item", source = "itemId")
  WishList toDto(WishListId wishListId);

  @Mapping(target = "userId", source = "id.userId")
  @Mapping(target = "itemId", source = "id.itemId")
  WishListId toId(WishListEntity wishListEntity);

  List<WishList> toDto(List<WishListEntity> wishLists);

  List<WishListEntity> toEntity(List<WishList> wishLists);
}
