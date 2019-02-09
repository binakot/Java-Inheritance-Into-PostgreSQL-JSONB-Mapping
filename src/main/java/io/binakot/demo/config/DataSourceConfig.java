package io.binakot.demo.config;

import io.binakot.demo.model.dao.DaoFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @Profile("!test")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @Profile("!test")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource(final DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(final DataSource dataSource) throws Exception {
        final TransactionFactory transactionFactory = new JdbcTransactionFactory();
        final Environment environment = new Environment("default", transactionFactory, dataSource);
        final SqlSessionFactory factory = new SqlSessionFactoryBuilder()
            .build(Resources.getResourceAsStream("mybatis-config.xml"));
        factory.getConfiguration().setEnvironment(environment);
        return factory;
    }

    @Bean
    @Primary
    public DaoFactory daoFactory(final SqlSessionFactory sqlSessionFactory) {
        return new DaoFactory(sqlSessionFactory);
    }
}
