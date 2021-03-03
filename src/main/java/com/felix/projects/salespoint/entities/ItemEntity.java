package com.felix.projects.salespoint.entities;



import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "items")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "price", nullable = false)
    private float price;

    @JoinColumn(name = "owner", nullable = false, referencedColumnName = "userId")
    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity userEntity;
}
