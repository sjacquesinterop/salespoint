package com.felix.projects.salespoint.entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name= "role", nullable = false)
    private Roles role;//not sure about this

    public enum Roles {

        CLIENT("client"),
        OWNER("owner");

        private final String role;

        Roles(String role) {
            this.role = role;
        }
    }
}

