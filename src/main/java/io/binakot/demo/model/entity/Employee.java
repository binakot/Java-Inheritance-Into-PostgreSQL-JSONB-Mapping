package io.binakot.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import io.binakot.demo.util.JsonUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.IOException;
import java.util.Objects;

public class Employee extends MonitoringObject {

    @JsonView(JsonUtils.JsonColumn.class)
    protected String name;

    @JsonView(JsonUtils.JsonColumn.class)
    protected String phoneNumber;

    public Employee() {
        super.type = MonitoringObjectType.EMPLOYEE;
    }

    private Employee(final String jsonAttributes) throws IOException {
        JsonUtils.JSON_MAPPER.readerForUpdating(this).readValue(jsonAttributes);
    }

    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final Employee employee = (Employee) o;
        return Objects.equals(name, employee.name) &&
            Objects.equals(phoneNumber, employee.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, phoneNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("name", name)
            .append("phoneNumber", phoneNumber)
            .toString();
    }
}
