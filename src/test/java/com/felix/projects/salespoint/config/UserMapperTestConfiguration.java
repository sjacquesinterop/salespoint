package com.felix.projects.salespoint.config;

import com.felix.projects.salespoint.mapper.UserMapper;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("userMapperTest")
@Configuration
public class UserMapperTestConfiguration {

  @Bean
  @Primary
  public UserMapper userMapper() {
    return Mockito.mock(UserMapper.class);
  }
}
