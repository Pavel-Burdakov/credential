package com.iprody.lms.credentialservice.repository;

import com.iprody.lms.credentialservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersCredentialsRepository extends JpaRepository<User, Long> {
  boolean existsByLogin(String login);
  Optional<User> findByLogin(String login);
  Optional<User> deleteByLogin(String login);
}
