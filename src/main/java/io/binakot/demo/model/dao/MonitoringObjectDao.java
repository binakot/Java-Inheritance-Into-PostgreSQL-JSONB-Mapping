package io.binakot.demo.model.dao;

import io.binakot.demo.model.entity.MonitoringObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public final class MonitoringObjectDao {

    private final SqlSessionFactory sqlSessionFactory;

    MonitoringObjectDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public MonitoringObject getById(final Integer id) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            final MonitoringObjectMapper mapper = session.getMapper(MonitoringObjectMapper.class);
            return mapper.getById(id);
        }
    }

    public List<MonitoringObject> getAll() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            final MonitoringObjectMapper mapper = session.getMapper(MonitoringObjectMapper.class);
            return mapper.getAll();
        }
    }

    public MonitoringObject create(final MonitoringObject entity) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            final MonitoringObjectMapper mapper = session.getMapper(MonitoringObjectMapper.class);
            mapper.create(entity);
            return entity;
        }
    }

    public MonitoringObject update(final MonitoringObject entity) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            final MonitoringObjectMapper mapper = session.getMapper(MonitoringObjectMapper.class);
            mapper.update(entity);
            return mapper.getById(entity.getId());
        }
    }

    public int delete(final Integer id) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            final MonitoringObjectMapper mapper = session.getMapper(MonitoringObjectMapper.class);
            return mapper.delete(id);
        }
    }
}
