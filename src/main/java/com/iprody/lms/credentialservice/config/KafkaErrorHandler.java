package com.iprody.lms.credentialservice.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.stereotype.Component;

@Component
public class KafkaErrorHandler implements CommonErrorHandler {

  @Override
  public boolean handleOne(
     Exception thrownException,
     ConsumerRecord<?, ?> record,
     org.apache.kafka.clients.consumer.Consumer<?, ?> consumer,
     MessageListenerContainer container
  ) {
    if (thrownException instanceof DeserializationException) {
      // Обработка ошибки десериализации
      System.err.println("Deserialization error for record: " + record);
      System.err.println("Exception: " + thrownException.getMessage());
      System.err.println("Headers: " + record.headers());
      System.err.println("Value: " + record.value());
    } else {
      // Обработка других ошибок
      System.err.println("Error in processing message: " + record);
      System.err.println("Exception: " + thrownException.getMessage());
    }

    // Возвращаем true, если ошибка была обработана, и false, если нет
    return true; // Ошибка обработана, пропускаем запись
  }
}