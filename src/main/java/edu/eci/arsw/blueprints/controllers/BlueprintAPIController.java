package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/blueprints")
public class BlueprintAPIController {

    @Autowired
    private BlueprintsServices blueprintServices;

    // Obtener todos los planos de un autor con filtro opcional
    @GetMapping("/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(
            @PathVariable String author,
            @RequestParam(required = false, defaultValue = "none") String filter) {
        try {
            Set<Blueprint> blueprints = blueprintServices.getBlueprintsByAuthor(author, filter);
            return new ResponseEntity<>(blueprints, HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Obtener un plano específico con filtro opcional
    @GetMapping("/{author}/{name}")
    public ResponseEntity<?> getBlueprint(
            @PathVariable String author,
            @PathVariable String name,
            @RequestParam(required = false, defaultValue = "none") String filter) {
        try {
            Blueprint blueprint = blueprintServices.getBlueprint(author, name, filter);
            return new ResponseEntity<>(blueprint, HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Agregar un nuevo plano
    @PostMapping
    public ResponseEntity<?> addNewBlueprint(@RequestBody Blueprint blueprint) {
        try {
            blueprintServices.addNewBlueprint(blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}