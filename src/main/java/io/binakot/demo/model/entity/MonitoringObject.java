package io.binakot.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    visible = true,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Vehicle.class, name = "VEHICLE"),
    @JsonSubTypes.Type(value = Employee.class, name = "EMPLOYEE"),
})
public abstract class MonitoringObject {

    protected Integer id;
    protected MonitoringObjectType type;
    protected Double latitude;
    protected Double longitude;
    protected Float course;
    protected Float speed;

    public Integer getId() {
        return id;
    }
    public void setId(final Integer id) {
        this.id = id;
    }

    public MonitoringObjectType getType() {
        return type;
    }
    public void setType(final MonitoringObjectType type) {
        this.type = type;
    }

    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    public Float getCourse() {
        return course;
    }
    public void setCourse(final Float course) {
        this.course = course;
    }

    public Float getSpeed() {
        return speed;
    }
    public void setSpeed(final Float speed) {
        this.speed = speed;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MonitoringObject that = (MonitoringObject) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(course, that.course) &&
            Objects.equals(speed, that.speed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, latitude, longitude, course, speed);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("id", id)
            .append("type", type)
            .append("latitude", latitude)
            .append("longitude", longitude)
            .append("course", course)
            .append("speed", speed)
            .toString();
    }
}
