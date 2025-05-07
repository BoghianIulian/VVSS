package model;

import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    void testAddAndLookupProductByName() {
        Product product = new Product(1, "Laptop", 1000.0, 10, 1, 20, FXCollections.observableArrayList());
        inventory.addProduct(product);

        Product found = inventory.lookupProduct("Laptop");
        assertNotNull(found);
        assertEquals("Laptop", found.getName());
    }

    @Test
    void testAddAndRemovePart() {
        Part part = new InhousePart(1, "Fan", 25.0, 5, 1, 10, 101);
        inventory.addPart(part);

        ObservableList<Part> parts = inventory.getAllParts();
        assertTrue(parts.contains(part));

        inventory.deletePart(part);
        assertFalse(inventory.getAllParts().contains(part));
    }
}
