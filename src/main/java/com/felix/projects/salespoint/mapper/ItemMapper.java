package com.felix.projects.salespoint.mapper;

import com.felix.projects.salespoint.dto.Item;
import com.felix.projects.salespoint.entities.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ItemMapper {

  ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

  @Mapping(source = "owner.id", target = "owner")
  Item toDto(ItemEntity item);

  @Mapping(source = "owner", target = "owner.id")
  ItemEntity toEntity(Item item);

  @Mapping(source = "owner.id", target = "owner")
  List<Item> toDto(List<ItemEntity> items);

  @Mapping(source = "owner", target = "owner.id")
  List<ItemEntity> toEntity(List<Item> items);
}
