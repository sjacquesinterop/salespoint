package com.felix.projects.salespoint.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Item {

  @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
  private Integer id;

  private String name;
  private String description;
  private Integer stock;
  private Float price;
  private Integer owner;
}
