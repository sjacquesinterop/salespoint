package com.felix.projects.salespoint.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@DynamicUpdate
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "name", nullable = false)
  @NotNull
  @Pattern(regexp = "^[a-zA-Z]*$", message = "Must only be letters.")
  @Size(min = 1, max = 64)
  private String name;

  @Column(name = "password", nullable = false)
  @NotNull
  @Pattern(
      regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$",
      message =
          "Must contain : At least a capital letter, at least a minuscule letter, at least a number and at least a symbol.")
  @Size(min = 8, max = 16)
  private String password;

  @Column(name = "email", nullable = false)
  @NotNull
  @Email(message = "This is not a valid email address.")
  private String email;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(referencedColumnName = "id", name = "role")
  private RoleEntity role;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
  private List<ItemEntity> listOfItems;
}
