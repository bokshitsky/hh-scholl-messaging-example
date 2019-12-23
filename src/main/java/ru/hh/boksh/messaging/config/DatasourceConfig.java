package ru.hh.boksh.messaging.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfig {

  @Bean
  DataSource dataSource() {
    return null;
  }

}
