package io.binakot.demo.controller;

import io.binakot.demo.IntegrationTest;
import io.binakot.demo.TestApplication;
import io.binakot.demo.TestUtils;
import io.binakot.demo.model.entity.Vehicle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = TestApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VehicleControllerTest extends IntegrationTest {

    @Test
    public void getById() {
        Vehicle object = TestUtils.buildTestVehicle();
        object = (Vehicle) daoFactory.getMonitoringObjectDao().create(object);

        final ResponseEntity<Vehicle> response = restTemplate.exchange(
            "http://localhost:" + port + "/vehicles/" + object.getId(),
            HttpMethod.GET, HttpEntity.EMPTY, Vehicle.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(object);
    }

    @Test
    public void getByIdNotExist() {
        final ResponseEntity<Vehicle> response = restTemplate.exchange(
            "http://localhost:" + port + "/vehicles/" + 100500,
            HttpMethod.GET, HttpEntity.EMPTY, Vehicle.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isNull();
    }

    @Test
    public void create() {
        final Vehicle object = TestUtils.buildTestVehicle();

        final RequestEntity<Vehicle> request = RequestEntity
            .post(URI.create("http://localhost:" + port + "/vehicles"))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(object);

        final ResponseEntity<Vehicle> response = restTemplate.exchange(
            request, Vehicle.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        final Vehicle responseObject = response.getBody();
        assertEquals(object.getRegNumber(), responseObject.getRegNumber());

        assertEquals(daoFactory.getMonitoringObjectDao().getById(responseObject.getId()), responseObject);
    }

    @Test
    public void update() {
        Vehicle object = TestUtils.buildTestVehicle();
        object = (Vehicle) daoFactory.getMonitoringObjectDao().create(object);

        // Update some fields
        object.setModel("KAMAZ");
        object.setRegNumber("X 777 YZ 123");

        final RequestEntity<Vehicle> request = RequestEntity
            .put(URI.create("http://localhost:" + port + "/vehicles"))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(object);

        final ResponseEntity<Vehicle> response = restTemplate.exchange(
            request, Vehicle.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        final Vehicle responseObject = response.getBody();
        assertEquals(object.getId(), responseObject.getId());
        assertEquals("KAMAZ", responseObject.getModel());
        assertEquals("X 777 YZ 123", responseObject.getRegNumber());

        assertEquals("KAMAZ", ((Vehicle) daoFactory.getMonitoringObjectDao().getById(object.getId())).getModel());
        assertEquals("X 777 YZ 123", ((Vehicle) daoFactory.getMonitoringObjectDao().getById(object.getId())).getRegNumber());
    }

    @Test
    public void delete() {
        Vehicle object = TestUtils.buildTestVehicle();
        object = (Vehicle) daoFactory.getMonitoringObjectDao().create(object);

        final ResponseEntity<Integer> response = restTemplate.exchange(
            "http://localhost:" + port + "/vehicles/" + object.getId(),
            HttpMethod.DELETE, HttpEntity.EMPTY, Integer.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(1);

        assertNull(daoFactory.getMonitoringObjectDao().getById(object.getId()));
    }
}
