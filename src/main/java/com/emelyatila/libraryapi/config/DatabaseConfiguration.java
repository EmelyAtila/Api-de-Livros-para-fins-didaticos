package com.emelyatila.libraryapi.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driverClassName}")
    String driver;

   // @Bean
    public DataSource dataSource(){
        // não é recomendado para utilizar em produção apenas para fins acadêmicos
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    @Bean
    public DataSource hikariDataSource(){
        // criar a config e retornar uma instancia do hikariDataSource passando a config
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); // máximo de conexões liveradas
        config.setMinimumIdle(1); // tamanho inicial do pool
        config.setPoolName("library-db-pool"); // nome que apareco no log
        config.setMaxLifetime(600000); // duração de uma conexão : minutos para milesegundos
        config.setConnectionTimeout(10000); // timeout para conseguir uma conexão
        config.setConnectionTestQuery("select 1"); // query de teste

        return new HikariDataSource(config);
    }
}
