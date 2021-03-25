package com.felix.projects.salespoint.mapper;

import com.felix.projects.salespoint.dto.User;
import com.felix.projects.salespoint.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(uses = ItemMapper.class)
@Component
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(source = "role.id", target = "role")
  User toDto(UserEntity user);

  @Mapping(source = "role", target = "role.id")
  UserEntity toEntity(User user);

  List<User> toDto(List<UserEntity> users);

  List<UserEntity> toEntity(List<User> users);
}
