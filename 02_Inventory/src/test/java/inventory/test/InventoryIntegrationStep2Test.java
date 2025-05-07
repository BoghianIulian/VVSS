import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.model.Part;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class InventoryIntegrationStep2Test {
    private InventoryService service;
    private InventoryRepository repo;
    private Inventory mockInventory;

    @BeforeEach
    void setUp() {
        mockInventory = mock(Inventory.class);

        // Configurăm listele ca să nu dea null
        when(mockInventory.getAllParts()).thenReturn(FXCollections.observableArrayList());
        when(mockInventory.getProducts()).thenReturn(FXCollections.observableArrayList());

        repo = new InventoryRepository();
        repo.setInventory(mockInventory);  // injectăm mock-ul
        service = new InventoryService(repo);
    }

    @Test
    void testAddInhousePart() {
        when(mockInventory.getAutoPartId()).thenReturn(1);

        service.addInhousePart("Cooler", 20.0, 5, 1, 10, 999);

        verify(mockInventory, times(1)).addPart(any(InhousePart.class));
    }

    @Test
    void testLookupPart() {
        Part mockedPart = new InhousePart(2, "Fan", 12.0, 8, 2, 20, 100);
        when(mockInventory.lookupPart("Fan")).thenReturn(mockedPart);

        Part found = service.lookupPart("Fan");

        assertNotNull(found);
        assertEquals("Fan", found.getName());
        verify(mockInventory).lookupPart("Fan");
    }
}
