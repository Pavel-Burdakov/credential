package com.iprody.lms.credentialservice.mapper;

import com.iprody.lms.credentialservice.dto.ResponseDto;
import com.iprody.lms.credentialservice.entity.User;
import org.mapstruct.Mapper;

/**
 * mapper interface.
 */
@Mapper(componentModel = "spring")
public interface UsersMapper {
  /**
   * user to dto.
   */
  ResponseDto toUserDto(User user); //map User to UserResponse

  /**
   * dto to user.
   */
  User toUserEntity(ResponseDto userDto);
}
