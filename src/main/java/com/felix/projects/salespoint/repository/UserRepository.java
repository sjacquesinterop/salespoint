package com.felix.projects.salespoint.repository;

import com.felix.projects.salespoint.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** The interface User repository. */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {}
