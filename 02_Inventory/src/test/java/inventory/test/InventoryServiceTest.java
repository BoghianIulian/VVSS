
import inventory.Validator.ValidatorException;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Inventory Service Tests")
class InventoryServiceTest {

    private InventoryRepository inventoryRepository;
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryRepository = new InventoryRepository();
        inventoryService = new InventoryService(inventoryRepository);
    }

    @AfterEach
    void tearDown() {
        inventoryRepository = null;
        inventoryService = null;
    }

    // BVA - Valide
    @Test
    @DisplayName("BVA: InStock = min")
    void testBVA_ValidMin() {
        assertDoesNotThrow(() -> inventoryService.addInhousePart("Ax", 10.0, 5, 5, 10, 101));
    }

    @Test
    @DisplayName("BVA: InStock = max")
    void testBVA_ValidMax() {
        assertDoesNotThrow(() -> inventoryService.addInhousePart("Piulita", 9.5, 10, 5, 10, 202));
    }

    // BVA - Invalide
    @Test
    @DisplayName("BVA: InStock < min")
    void testBVA_InvalidBelowMin() {
        ValidatorException ex = assertThrows(ValidatorException.class, () ->
                inventoryService.addInhousePart("Bolt", 8.0, 4, 5, 10, 303));
        assertTrue(ex.getMessage().contains("In stock must be between min and max"));
    }

    @Test
    @DisplayName("BVA: InStock > max")
    void testBVA_InvalidAboveMax() {
        ValidatorException ex = assertThrows(ValidatorException.class, () ->
                inventoryService.addInhousePart("Surub", 6.5, 11, 5, 10, 404));
        assertTrue(ex.getMessage().contains("In stock must be between min and max"));
    }

    // ECP - Valide
    @Test
    @DisplayName("ECP: Valori normale")
    void testECP_ValidLow() {
        assertDoesNotThrow(() -> inventoryService.addInhousePart("Motoras", 20.0, 7, 5, 15, 505));
    }

    @Test
    @DisplayName("ECP: Valori mari")
    void testECP_ValidHigh() {
        assertDoesNotThrow(() -> inventoryService.addInhousePart("Rotor", 50.0, 18, 10, 20, 606));
    }

    // ECP - Invalide
    @Test
    @DisplayName("ECP: Nume gol")
    void testECP_InvalidEmptyName() {
        ValidatorException ex = assertThrows(ValidatorException.class, () ->
                inventoryService.addInhousePart("", 12.0, 8, 5, 10, 707));
        assertTrue(ex.getMessage().contains("Name cannot be empty"));
    }

    @Test
    @DisplayName("ECP: min > max")
    void testECP_InvalidMinMax() {
        ValidatorException ex = assertThrows(ValidatorException.class, () ->
                inventoryService.addInhousePart("Capac", 14.0, 7, 10, 5, 808));
        assertTrue(ex.getMessage().contains("Min cannot be greater than max"));
    }
}
