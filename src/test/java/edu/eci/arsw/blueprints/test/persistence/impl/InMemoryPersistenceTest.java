package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.impl.RedundancyFilter;
import edu.eci.arsw.blueprints.persistence.impl.SubsamplingFilter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryPersistenceTest {

    @Test
    public void testRedundancyFilter() {
        RedundancyFilter filter = new RedundancyFilter();
        Blueprint bp = new Blueprint("juan", "Casa", new Point[]{
                new Point(10, 20), new Point(10, 20),
                new Point(30, 40), new Point(30, 40), new Point(30, 40)
        });

        Blueprint filteredBp = filter.filter(bp);
        assertEquals(2, filteredBp.getPoints().size());
    }

    @Test
    public void testSubsamplingFilter() {
        SubsamplingFilter filter = new SubsamplingFilter();
        Blueprint bp = new Blueprint("maria", "Jardín", new Point[]{
                new Point(10, 20), new Point(15, 25),
                new Point(30, 40), new Point(35, 45), new Point(50, 60)
        });

        Blueprint filteredBp = filter.filter(bp);
        assertEquals(3, filteredBp.getPoints().size());
    }
}
