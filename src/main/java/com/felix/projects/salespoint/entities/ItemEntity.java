package com.felix.projects.salespoint.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@DynamicUpdate
@Table(name = "items")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class ItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "name", nullable = false)
  @NotNull
  @Pattern(
      regexp = "^[a-zA-Z\\s0-9]+$",
      message = "Name cannot contain special characters and must be between 1 and 64 characters.")
  @Size(min = 1, max = 64)
  private String name;

  @Column(name = "description", nullable = false)
  @Size(min = 1, max = 256)
  @NotNull
  @Pattern(
      regexp = "^[a-zA-Z\0-9]+$",
      message =
          "Description cannot contain special characters and must be between 1 and 256 characters.\"")
  private String description;

  @Column(name = "stock", nullable = false)
  @Max(1000)
  @Min(0)
  @NotNull
  private Integer stock;

  @Column(name = "price", nullable = false)
  @NotNull
  @Positive
  private Float price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(referencedColumnName = "id", name = "owner")
  @NotNull
  private UserEntity owner;
}
