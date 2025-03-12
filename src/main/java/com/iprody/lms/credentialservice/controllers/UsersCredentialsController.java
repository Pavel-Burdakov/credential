package com.iprody.lms.credentialservice.controllers;

import com.iprody.lms.arrangementservice.dto.RegRequestDto;
import com.iprody.lms.credentialservice.dto.DelRequestDto;
import com.iprody.lms.credentialservice.dto.LoginRequestDto;

import com.iprody.lms.credentialservice.dto.ResponseDto;
import com.iprody.lms.credentialservice.entity.User;
import com.iprody.lms.credentialservice.services.CredentialsService;
import com.iprody.lms.credentialservice.utils.JwtUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class UsersCredentialsController {
  private final CredentialsService service;

  public UsersCredentialsController(CredentialsService service, JwtUtil jwtUtil) {
    this.service = service;
    this.jwtUtil = jwtUtil;
  }

  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  public String login(@RequestBody LoginRequestDto request) {
    // Логика аутентификации (например, проверка в базе данных)
    User user = service.findUser(request);
    String token = jwtUtil.generateToken(user.getLogin(), user.getRole());
    user.setJwt(token);
    service.addToken(user);
    // Получить роль из базы данных
    return token;
  }

  @PostMapping("/validate")
  public boolean validateToken(@RequestBody String token) {
    return jwtUtil.validateToken(token);
  }

  @PostMapping("/deleteuser")
  public ResponseEntity<String> deleteUser(@RequestBody DelRequestDto delRequestDto) {
    try {
      ResponseDto deletedUser = service.deleteByLogin(delRequestDto.getLogin());
      return ResponseEntity.status(HttpStatus.OK).body("Ok");
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping("/registration")
  public Mono<ResponseEntity<User>> createUser(@Valid @RequestBody RegRequestDto userRegDto) {
    User user = new User(userRegDto.getEmail(), userRegDto.getPassword(), "USER");
    return service.save(user)
       .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body(savedUser))
       .onErrorReturn(EntityExistsException.class,
          ResponseEntity.status(HttpStatus.CONFLICT).build());
  }
}
