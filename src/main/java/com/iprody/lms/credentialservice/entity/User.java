package com.iprody.lms.credentialservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Entity class.
 */
@Entity
@Table(name = "users_credentials")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "login", nullable = false)
  private String login;
  @Column(name = "password", unique = false, nullable = false)
  private String password;
  @Column(name = "jwt")
  private String jwt;
  @Column
  private String role;

  public User(String login, String password, String role) {
    this.login = login;
    this.password = password;
    this.role = role;
  }
}
