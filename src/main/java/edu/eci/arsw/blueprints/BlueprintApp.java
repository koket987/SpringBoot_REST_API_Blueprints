package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Set;

public class BlueprintApp {
    public static void main(String[] args) {

        // Cargar el contexto de Spring
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Obtener la instancia de BlueprintsServices gestionada por Spring
        BlueprintsServices blueprintService = context.getBean(BlueprintsServices.class);

        try {
            // 1. Registrar nuevos Blueprints
            Blueprint bp1 = new Blueprint("juan", "Casa", new Point[]{ new Point(10, 20), new Point(30, 40) });
            Blueprint bp2 = new Blueprint("juan", "Oficina", new Point[]{ new Point(50, 60), new Point(70, 80) });
            Blueprint bp3 = new Blueprint("maria", "Jardín", new Point[]{ new Point(15, 25), new Point(35, 45) });

            blueprintService.addNewBlueprint(bp1);
            blueprintService.addNewBlueprint(bp2);
            blueprintService.addNewBlueprint(bp3);

            System.out.println("✔️ Planos registrados exitosamente.");

            // 2. Consultar un Blueprint específico
            System.out.println("\n🔍 Consultando el plano 'Casa' de 'juan':");
            Blueprint retrievedBlueprint = blueprintService.getBlueprint("juan", "Casa", "none"); // Agregar el tipo de filtro
            System.out.println(retrievedBlueprint);

            // 3. Consultar todos los Blueprints de un autor
            System.out.println("\n📚 Consultando todos los planos de 'juan':");
            Set<Blueprint> blueprintsByJuan = blueprintService.getBlueprintsByAuthor("juan", "none"); // Agregar el tipo de filtro
            blueprintsByJuan.forEach(System.out::println);

            // 4. Manejar la excepción si no existe el Blueprint
            System.out.println("\n🔎 Intentando buscar un plano que no existe...");
            try {
                blueprintService.getBlueprint("carlos", "Museo", "none"); // Agregar el tipo de filtro
            } catch (BlueprintNotFoundException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}