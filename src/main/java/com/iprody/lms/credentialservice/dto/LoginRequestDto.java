package com.iprody.lms.credentialservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class LoginRequestDto {
  String username;
  String password;
}
