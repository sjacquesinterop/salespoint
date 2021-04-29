package com.felix.projects.salespoint.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class WishListId implements Serializable {

  protected Integer userId;
  protected Integer itemId;

  public WishListId() {}

  @Column(name = "userid")
  public Integer getUserId() {
    return this.userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  @Column(name = "itemid")
  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }
}
