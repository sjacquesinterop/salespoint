package com.felix.projects.salespoint.dto;

import com.felix.projects.salespoint.entities.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class User {

  @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
  private Integer id;

  private String name;
  private String password;
  private String email;

  @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
  private List<Item> listOfItems;

  private UserEntity.Roles role;
}
