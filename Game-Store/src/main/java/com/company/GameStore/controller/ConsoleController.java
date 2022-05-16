package com.company.GameStore.controller;

import com.company.GameStore.DTO.Console;
import com.company.GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ConsoleController {

    @Autowired
    ServiceLayer service;

    @GetMapping("/consoles")
    @ResponseStatus(HttpStatus.OK)
    public List<Console> getConsoles(@RequestParam(required = false) String manufacturer) {
         if (manufacturer != null) {
            return service.getConsolesByManufacturer(manufacturer);
        }
        return service.getAllConsoles();
    }

    @GetMapping("/consoles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Console> getConsole(@PathVariable int id, @RequestParam(required = false) String manufacturer) {
        if (manufacturer != null) {
            return service.getConsoleByManufacturer(manufacturer);
        }
        return service.getSingleConsole(id);
    }

    @GetMapping("/consoles/manufacturer/{manufacturer}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Console> getConsoleByManufacturer(@PathVariable String manufacturer) {
        return service.getConsoleByManufacturer(manufacturer);
    }

    @PostMapping("/consoles")
    @ResponseStatus(HttpStatus.CREATED)
    public Console createConsole(@RequestBody Console console) {
        System.out.println(console);
        return service.addConsole(console);
    }

    @PutMapping("/consoles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateConsole(@RequestBody Console console, @PathVariable int id) {
        if (console.getConsole_id() == null) {
            console.setConsole_id(id);
        }
        if (id != console.getConsole_id()) {
            throw new DataIntegrityViolationException("Your request body ID does not match your Path Variable id");
        }
        service.updateConsole(console);
    }

    @DeleteMapping("/consoles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable int id) {
        service.deleteConsole(id);
    }

}
