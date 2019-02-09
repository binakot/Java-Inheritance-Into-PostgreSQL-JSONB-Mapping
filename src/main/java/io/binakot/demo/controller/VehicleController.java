package io.binakot.demo.controller;

import io.binakot.demo.model.dao.DaoFactory;
import io.binakot.demo.model.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final DaoFactory daoFactory;

    @Autowired
    public VehicleController(final DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Vehicle> getById(@PathVariable("id") @NotNull final Integer id) {
        return Optional.ofNullable(daoFactory.getMonitoringObjectDao().getById(id))
            .filter(Vehicle.class::isInstance)
            .map(Vehicle.class::cast)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Vehicle> create(@RequestBody @NotNull final Vehicle entity) {
        return ResponseEntity.ok((Vehicle) daoFactory.getMonitoringObjectDao().create(entity));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Vehicle> update(@RequestBody @NotNull final Vehicle entity) {
        return ResponseEntity.ok((Vehicle) daoFactory.getMonitoringObjectDao().update(entity));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable("id") @NotNull final Integer id) {
        return ResponseEntity.ok(daoFactory.getMonitoringObjectDao().delete(id));
    }
}
