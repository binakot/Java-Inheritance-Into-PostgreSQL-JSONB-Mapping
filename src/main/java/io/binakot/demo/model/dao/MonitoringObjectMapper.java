package io.binakot.demo.model.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.binakot.demo.model.entity.MonitoringObject;
import io.binakot.demo.util.JsonUtils;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public interface MonitoringObjectMapper {

    @Select("SELECT * FROM monitoring_objects WHERE id=#{id}")
    @ResultMap("monitoringObjectResultMap")
    MonitoringObject getById(int id);

    @Select("SELECT * FROM monitoring_objects")
    @ResultMap("monitoringObjectResultMap")
    List<MonitoringObject> getAll();

    @InsertProvider(type = SqlBuilder.class, method = "create")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void create(MonitoringObject entity);

    @UpdateProvider(type = SqlBuilder.class, method = "update")
    void update(MonitoringObject entity);

    @Delete("DELETE FROM monitoring_objects WHERE id=#{id}")
    int delete(int id);

    final class SqlBuilder {

        private static final String TABLE_NAME = "monitoring_objects";

        public String create(final MonitoringObject monitoringObject) throws JsonProcessingException {
            return new SQL() {{
                INSERT_INTO(TABLE_NAME);
                INTO_COLUMNS("type, latitude, longitude, course, speed, attributes");
                INTO_VALUES("#{type}::MONITORING_OBJECT_TYPE");
                INTO_VALUES("#{latitude}");
                INTO_VALUES("#{longitude}");
                INTO_VALUES("#{course}");
                INTO_VALUES("#{speed}");
                INTO_VALUES("'" + JsonUtils.JSON_MAPPER.writerFor(MonitoringObject.class).withView(JsonUtils.JsonColumn.class).writeValueAsString(monitoringObject) + "'::JSONB");
            }}.toString();
        }

        public String update(final MonitoringObject monitoringObject) throws JsonProcessingException {
            return new SQL() {{
                UPDATE(TABLE_NAME);
                SET("type = #{type}::MONITORING_OBJECT_TYPE");
                SET("latitude = #{latitude}");
                SET("longitude = #{longitude}");
                SET("course = #{course}");
                SET("speed = #{speed}");
                SET("attributes = '" + JsonUtils.JSON_MAPPER.writerFor(MonitoringObject.class).withView(JsonUtils.JsonColumn.class).writeValueAsString(monitoringObject) + "'::JSONB");
                WHERE("id = #{id}");
            }}.toString();
        }
    }
}
