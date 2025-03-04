package com.iprody.lms.credentialservice.utils;


import com.iprody.lms.credentialservice.dto.ResponseDto;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

/** CopyUtils class.
 *
 */
@Component
public class CopyUtils {

  /** getNullPropertyNames.
   *
   * @param source UserDto.
   * @return String []
   */
  public String[] getNullPropertyNames(ResponseDto source) {
    return Arrays.stream(source.getClass().getDeclaredFields())
       .filter(field -> {
         field.setAccessible(true);
         try {
           return field.get(source) == null;
         } catch (IllegalAccessException e) {
           throw new RuntimeException(e);
         }
       })
       .map(Field::getName)
       .toArray(String[]::new);
  }
}
