package com.iprody.lms.arrangementservice.dto;

public class RegRequestDto {
  String email;
  String password;

  public RegRequestDto(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public RegRequestDto() {
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
