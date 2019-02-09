package io.binakot.demo;

import com.github.dockerjava.api.exception.DockerClientException;
import io.binakot.demo.model.dao.DaoFactory;
import org.apache.ibatis.io.Resources;
import org.junit.After;
import org.junit.Before;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public abstract class IntegrationTest {

    private static final PostgreSQLContainer pgContainer = new PostgreSQLContainer("postgres");
    static {
        pgContainer.start();
    }

    @Configuration
    public static class TestDataSourceConfig {

        @Bean
        public DataSource dataSource() {
            if (!pgContainer.isRunning()) {
                throw new DockerClientException("Test DB container is not running! Check your Docker.");
            }

            final PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setUrl(pgContainer.getJdbcUrl());
            ds.setUser(pgContainer.getUsername());
            ds.setPassword(pgContainer.getPassword());
            return ds;
        }
    }

    @Autowired
    private DataSource dataSource;
    @Autowired
    protected DaoFactory daoFactory;

    @LocalServerPort
    protected int port;
    @Autowired
    protected TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        final String sqlQuery = IOUtils.toString(Resources.getResourceAsStream("sql/init.sql"));
        try (final Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.execute();
        }
    }

    @After
    public void tearDown() throws Exception {
        final String sqlQuery = IOUtils.toString(Resources.getResourceAsStream("sql/clean.sql"));
        try (final Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.execute();
        }
    }
}
