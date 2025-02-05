package io.github.ybertoldi.libraryapi.config;

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

    @Value("${spring.datasource.driver-class-name}")
    String driver;

//    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    @Bean
    public DataSource hikariDataSource(){
        HikariConfig hc = new HikariConfig();
        hc.setUsername(username);
        hc.setJdbcUrl(url);
        hc.setPassword(password);
        hc.setDriverClassName(driver);

        hc.setMaximumPoolSize(10); //máximo de conexões liberadas
        hc.setMinimumIdle(1); //tamanho inicial do pool
        hc.setPoolName("library-db-pool");
        hc.setMaxLifetime((10 * 60) * 1000); //tempo máximo de conexão (10 minutos, no caso)
        hc.setConnectionTimeout( (int) (1.6 * 60) * 1000); //tempo máximo de o hikari gastará para tentar abrir uma conexão
        hc.setConnectionTestQuery("select 1");

        return new HikariDataSource(hc);
    }
}
