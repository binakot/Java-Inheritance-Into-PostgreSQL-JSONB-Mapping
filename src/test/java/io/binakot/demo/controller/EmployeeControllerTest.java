package io.binakot.demo.controller;

import io.binakot.demo.IntegrationTest;
import io.binakot.demo.TestApplication;
import io.binakot.demo.TestUtils;
import io.binakot.demo.model.entity.Employee;
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
public class EmployeeControllerTest extends IntegrationTest {

    @Test
    public void getById() {
        Employee object = TestUtils.buildTestEmployee();
        object = (Employee) daoFactory.getMonitoringObjectDao().create(object);

        final ResponseEntity<Employee> response = restTemplate.exchange(
            "http://localhost:" + port + "/employees/" + object.getId(),
            HttpMethod.GET, HttpEntity.EMPTY, Employee.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(object);
    }

    @Test
    public void getByIdNotExist() {
        final ResponseEntity<Employee> response = restTemplate.exchange(
            "http://localhost:" + port + "/employees/" + 100500,
            HttpMethod.GET, HttpEntity.EMPTY, Employee.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(response.getBody()).isNull();
    }

    @Test
    public void create() {
        final Employee object = TestUtils.buildTestEmployee();

        final RequestEntity<Employee> request = RequestEntity
            .post(URI.create("http://localhost:" + port + "/employees"))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(object);

        final ResponseEntity<Employee> response = restTemplate.exchange(
            request, Employee.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        final Employee responseObject = response.getBody();
        assertEquals(object.getName(), responseObject.getName());

        assertEquals(daoFactory.getMonitoringObjectDao().getById(responseObject.getId()), responseObject);
    }

    @Test
    public void update() {
        Employee object = TestUtils.buildTestEmployee();
        object = (Employee) daoFactory.getMonitoringObjectDao().create(object);

        // Update some fields
        object.setName("Jane Doe");
        object.setPhoneNumber("+7 (987) 654-32-10");

        final RequestEntity<Employee> request = RequestEntity
            .put(URI.create("http://localhost:" + port + "/employees"))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(object);

        final ResponseEntity<Employee> response = restTemplate.exchange(
            request, Employee.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        final Employee responseObject = response.getBody();
        assertEquals(object.getId(), responseObject.getId());
        assertEquals("Jane Doe", responseObject.getName());
        assertEquals("+7 (987) 654-32-10", responseObject.getPhoneNumber());

        assertEquals("Jane Doe", ((Employee) daoFactory.getMonitoringObjectDao().getById(object.getId())).getName());
        assertEquals("+7 (987) 654-32-10", ((Employee) daoFactory.getMonitoringObjectDao().getById(object.getId())).getPhoneNumber());
    }

    @Test
    public void delete() {
        Employee object = TestUtils.buildTestEmployee();
        object = (Employee) daoFactory.getMonitoringObjectDao().create(object);

        final ResponseEntity<Integer> response = restTemplate.exchange(
            "http://localhost:" + port + "/employees/" + object.getId(),
            HttpMethod.DELETE, HttpEntity.EMPTY, Integer.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo(1);

        assertNull(daoFactory.getMonitoringObjectDao().getById(object.getId()));
    }
}
