import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InventoryIntegrationStep3Test {
    private InventoryService service;

    @BeforeEach
    void setUp() {
        InventoryRepository repo = new InventoryRepository();
        repo.setInventory(new inventory.model.Inventory()); // folosim un Inventory curat
        service = new InventoryService(repo);
    }

    @Test
    void testAddAndGetPartIntegrated() {
        service.addInhousePart("Rotor", 50.0, 10, 1, 100, 999);

        Part found = service.lookupPart("Rotor");

        assertNotNull(found);
        assertEquals("Rotor", found.getName());
        assertEquals(50.0, found.getPrice());
    }

    @Test
    void testAddAndGetProductWithPartIntegrated() {
        // Cream și adăugăm o piesă mai întâi
        service.addInhousePart("Blade", 30.0, 5, 1, 50, 123);
        Part blade = service.lookupPart("Blade");

        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(blade);

        service.addProduct("Turbine", 500.0, 3, 1, 10, parts);
        Product found = service.lookupProduct("Turbine");

        assertNotNull(found);
        assertEquals("Turbine", found.getName());
        assertEquals(1, found.getAssociatedParts().size());
        assertEquals("Blade", found.getAssociatedParts().get(0).getName());
    }
}
