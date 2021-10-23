package io.binakot.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import io.binakot.demo.util.JsonUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.IOException;
import java.util.Objects;

public class Vehicle extends MonitoringObject {

    @JsonView(JsonUtils.JsonColumn.class)
    protected String model;
    @JsonView(JsonUtils.JsonColumn.class)
    protected String regNumber;

    public Vehicle() {
        super.type = MonitoringObjectType.VEHICLE;
    }

    private Vehicle(final String jsonAttributes) throws IOException {
        JsonUtils.JSON_MAPPER.readerForUpdating(this).readValue(jsonAttributes);
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(final String regNumber) {
        this.regNumber = regNumber;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final Vehicle vehicle = (Vehicle) o;
        return Objects.equals(model, vehicle.model) &&
            Objects.equals(regNumber, vehicle.regNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), model, regNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("model", model)
            .append("regNumber", regNumber)
            .toString();
    }
}
