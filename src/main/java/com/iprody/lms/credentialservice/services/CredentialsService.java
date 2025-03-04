package com.iprody.lms.credentialservice.services;

import com.iprody.lms.credentialservice.dto.LoginRequestDto;
import com.iprody.lms.credentialservice.dto.ResponseDto;
import com.iprody.lms.credentialservice.entity.User;
import com.iprody.lms.credentialservice.mapper.UsersMapper;
import com.iprody.lms.credentialservice.repository.UsersCredentialsRepository;
import com.iprody.lms.credentialservice.utils.CopyUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class CredentialsService {
  private final UsersCredentialsRepository repository;
  private final UsersMapper usersMapper;
  private final CopyUtils copyUtils;
  private static final Logger logger = LoggerFactory.getLogger(CredentialsService.class);

  public CredentialsService(UsersCredentialsRepository repository, UsersMapper usersMapper, CopyUtils copyUtils) {
    this.repository = repository;
    this.usersMapper = usersMapper;
    this.copyUtils = copyUtils;
  }

  public Mono<User> save(User user) {
    return repository.existsByLogin(user.getLogin())
       ? Mono.error(new EntityExistsException(user.getLogin()))
       : Mono.fromCallable(() -> {
         return repository.save(user);
       }
    );
  }

  public ResponseDto deleteByLogin(String login) {
    Optional<User> optionalUser = repository.findByLogin(login);

    if (optionalUser.isPresent()) {
      repository.deleteById(optionalUser.get().getId());
      return usersMapper.toUserDto(optionalUser.get());
    } else {
      throw new EntityNotFoundException();
    }
  }

  public User findUser(LoginRequestDto loginRequestDto){
    Optional<User> user = repository.findByLogin(loginRequestDto.getUsername());
    if (user.isEmpty()) {
      throw new EntityNotFoundException();
    }
    if (!user.get().getPassword().equals(loginRequestDto.getPassword())) {
      throw new RuntimeException("password incorrect");
    }
    return user.get();
  }

  public void addToken(User updatedUser) {
    repository.save(updatedUser);

  }
}
