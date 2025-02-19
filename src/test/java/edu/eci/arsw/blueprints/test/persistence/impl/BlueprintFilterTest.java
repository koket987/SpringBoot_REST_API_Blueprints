package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.impl.RedundancyFilter;
import edu.eci.arsw.blueprints.persistence.impl.SubsamplingFilter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlueprintFilterTest {

    @Test
    public void testRedundancyFilter() {
        RedundancyFilter filter = new RedundancyFilter();
        Blueprint bp = new Blueprint("juan", "Casa", new Point[]{
                new Point(10, 20), new Point(10, 20), // Punto repetido
                new Point(30, 40), new Point(30, 40), new Point(30, 40), // Puntos repetidos
                new Point(50, 60)
        });

        Blueprint filteredBp = filter.filter(bp);

        // Esperamos que los puntos repetidos consecutivos sean eliminados.
        assertEquals(3, filteredBp.getPoints().size());
        assertEquals(new Point(10, 20), filteredBp.getPoints().get(0));
        assertEquals(new Point(30, 40), filteredBp.getPoints().get(1));
        assertEquals(new Point(50, 60), filteredBp.getPoints().get(2));
    }

    @Test
    public void testSubsamplingFilter() {
        SubsamplingFilter filter = new SubsamplingFilter();
        Blueprint bp = new Blueprint("maria", "Jardín", new Point[]{
                new Point(10, 20), new Point(15, 25),
                new Point(30, 40), new Point(35, 45),
                new Point(50, 60), new Point(55, 65)
        });

        Blueprint filteredBp = filter.filter(bp);

        // Esperamos que se elimine 1 de cada 2 puntos.
        assertEquals(3, filteredBp.getPoints().size());
        assertEquals(new Point(10, 20), filteredBp.getPoints().get(0));
        assertEquals(new Point(30, 40), filteredBp.getPoints().get(1));
        assertEquals(new Point(50, 60), filteredBp.getPoints().get(2));
    }
}
