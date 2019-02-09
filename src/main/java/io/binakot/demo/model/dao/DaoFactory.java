package io.binakot.demo.model.dao;

import org.apache.ibatis.session.SqlSessionFactory;

public final class DaoFactory {

    private final MonitoringObjectDao monitoringObjectDao;

    public DaoFactory(final SqlSessionFactory sqlSessionFactory) {
        monitoringObjectDao = new MonitoringObjectDao(sqlSessionFactory);
    }

    public MonitoringObjectDao getMonitoringObjectDao() {
        return monitoringObjectDao;
    }
}
