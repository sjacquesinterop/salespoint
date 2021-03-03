package com.felix.projects.salespoint.dto;

import com.felix.projects.salespoint.entities.UserEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
@Data
public class User {

    private int userId;
    private String name;
    private String password;
    private String email;
    private UserEntity.Roles role;



}
