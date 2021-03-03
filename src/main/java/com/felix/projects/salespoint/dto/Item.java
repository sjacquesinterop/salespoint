package com.felix.projects.salespoint.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Item {

    private int itemId;
    private String name;
    private String description;
    private int stock;
    private float price;
    private User owner;

}
