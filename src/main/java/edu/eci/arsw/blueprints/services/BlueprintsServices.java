package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintFilter;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlueprintsServices {

    @Autowired
    private BlueprintsPersistence bpPersistence;

    @Autowired
    private BlueprintFilter redundancyFilter; // Filtro de redundancia
    @Autowired
    private BlueprintFilter subsamplingFilter; // Filtro de submuestreo

    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpPersistence.saveBlueprint(bp);
    }

    public Blueprint getBlueprint(String author, String name, String filterType) throws BlueprintNotFoundException {
        Blueprint bp = bpPersistence.getBlueprint(author, name);
        return applyFilter(bp, filterType);
    }

    public Set<Blueprint> getBlueprintsByAuthor(String author, String filterType) throws BlueprintNotFoundException {
        Set<Blueprint> blueprints = bpPersistence.getBlueprintsByAuthor(author);
        return blueprints.stream()
                .map(bp -> applyFilter(bp, filterType))
                .collect(Collectors.toSet());
    }

    private Blueprint applyFilter(Blueprint bp, String filterType) {
        if ("redundancy".equalsIgnoreCase(filterType)) {
            return redundancyFilter.filter(bp);
        } else if ("subsampling".equalsIgnoreCase(filterType)) {
            return subsamplingFilter.filter(bp);
        } else {
            return bp;
        }
    }
}