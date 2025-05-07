package Service;

import inventory.model.InhousePart;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class InventoryServiceTest {
    private InventoryRepository mockRepo;
    private InventoryService service;

    @BeforeEach
    void setUp() {
        mockRepo = mock(InventoryRepository.class);
        service = new InventoryService(mockRepo);
    }

    @Test
    void testAddInhousePartDelegatesToRepo() {
        when(mockRepo.getAutoPartId()).thenReturn(1);

        service.addInhousePart("Wheel", 5.0, 10, 1, 20, 1001);

        verify(mockRepo, times(1)).addPart(any(InhousePart.class));
    }

    @Test
    void testAddProductDelegatesToRepo() {
        when(mockRepo.getAutoProductId()).thenReturn(1);
        ObservableList parts = FXCollections.observableArrayList();

        service.addProduct("Laptop", 1000.0, 10, 1, 50, parts);

        verify(mockRepo, times(1)).addProduct(any(Product.class));
    }
}
