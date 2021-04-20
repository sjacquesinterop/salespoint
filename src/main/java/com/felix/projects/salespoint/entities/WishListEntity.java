package com.felix.projects.salespoint.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@DynamicUpdate
@Table(name = "wishlist")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class WishListEntity {

  private WishListId id;

  @EmbeddedId
  public WishListId getId() {
    return this.id;
  }

  public void setId(WishListId id) {
    this.id = id;
  }
}
