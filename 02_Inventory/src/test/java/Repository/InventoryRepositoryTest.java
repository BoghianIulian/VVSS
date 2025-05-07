package Repository;

import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.model.Part;
import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;


public class InventoryRepositoryTest {
    private InventoryRepository repo;
    private Inventory spyInventory;

    @BeforeEach
    void setUp() {
        // Facem un Inventory "spionat"
        Inventory realInventory = new Inventory();
        spyInventory = spy(realInventory);

        // Injectăm spy-ul manual în repository
        repo = new InventoryRepository();
        repo.setInventory(spyInventory);
    }

    @Test
    void testAddPartWritesToInventory() {
        Part part = new InhousePart(1, "Screw", 0.5, 100, 10, 200, 123);
        repo.addPart(part);

        // Verificăm dacă metoda addPart() a fost apelată pe inventory
        verify(spyInventory, times(1)).addPart(part);
    }

    @Test
    void testLookupPartCallsInventory() {
        Part part = new InhousePart(1, "Bolt", 1.0, 50, 5, 100, 321);
        repo.addPart(part);

        repo.lookupPart("Bolt");

        verify(spyInventory, times(1)).lookupPart("Bolt");
    }
}
