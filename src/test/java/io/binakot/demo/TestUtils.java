package io.binakot.demo;

import io.binakot.demo.model.entity.Employee;
import io.binakot.demo.model.entity.Vehicle;

public enum TestUtils {;

    public static Vehicle buildTestVehicle() {
        final Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        vehicle.setLatitude(1d);
        vehicle.setLongitude(2d);
        vehicle.setCourse(3f);
        vehicle.setSpeed(4f);
        vehicle.setModel("Toyota Celica");
        vehicle.setRegNumber("A 123 BC 777");
        return vehicle;
    }

    public static Employee buildTestEmployee() {
        final Employee employee = new Employee();
        employee.setId(1);
        employee.setLatitude(1d);
        employee.setLongitude(2d);
        employee.setCourse(3f);
        employee.setSpeed(4f);
        employee.setName("John Doe");
        employee.setPhoneNumber("8-800 2000 600");
        return employee;
    }
}
