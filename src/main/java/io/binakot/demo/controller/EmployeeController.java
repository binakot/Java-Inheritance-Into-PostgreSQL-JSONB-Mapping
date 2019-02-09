package io.binakot.demo.controller;

import io.binakot.demo.model.dao.DaoFactory;
import io.binakot.demo.model.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final DaoFactory daoFactory;

    @Autowired
    public EmployeeController(final DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Employee> getById(@PathVariable("id") @NotNull final Integer id) {
        return Optional.ofNullable(daoFactory.getMonitoringObjectDao().getById(id))
            .filter(Employee.class::isInstance)
            .map(Employee.class::cast)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Employee> create(@RequestBody @NotNull final Employee entity) {
        return ResponseEntity.ok((Employee) daoFactory.getMonitoringObjectDao().create(entity));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Employee> update(@RequestBody @NotNull final Employee entity) {
        return ResponseEntity.ok((Employee) daoFactory.getMonitoringObjectDao().update(entity));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable("id") @NotNull final Integer id) {
        return ResponseEntity.ok(daoFactory.getMonitoringObjectDao().delete(id));
    }
}
