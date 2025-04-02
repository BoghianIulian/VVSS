import inventory.Validator.ValidatorException;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
    @Tag("BVA")
    @DisplayName("BVA: InStock = min")
    void testBVA_ValidMin() {
        assertDoesNotThrow(() -> inventoryService.addInhousePart("Ax", 10.0, 5, 5, 10, 101));
    }

    @Test
    @Tag("BVA")
    @DisplayName("BVA: InStock = max")
    void testBVA_ValidMax() {
        assertDoesNotThrow(() -> inventoryService.addInhousePart("Piulita", 9.5, 10, 5, 10, 202));
    }

    // BVA - Invalide
    @Test
    @Tag("BVA")
    @DisplayName("BVA: InStock < min")
    void testBVA_InvalidBelowMin() {
        ValidatorException ex = assertThrows(ValidatorException.class, () ->
                inventoryService.addInhousePart("Bolt", 8.0, 4, 5, 10, 303));
        assertTrue(ex.getMessage().contains("In stock must be between min and max"));
    }

    @RepeatedTest(3)
    @Tag("BVA")
    @DisplayName("BVA: InStock > max")
    void testBVA_InvalidAboveMax() {
        ValidatorException ex = assertThrows(ValidatorException.class, () ->
                inventoryService.addInhousePart("Surub", 6.5, 11, 5, 10, 404));
        assertTrue(ex.getMessage().contains("In stock must be between min and max"));
    }

    // ECP - Valide
    @ParameterizedTest
    @Tag("ECP")
    @ValueSource(strings = {"Motoras", "Surubelnita", "Freze"})
    @DisplayName("ECP: Valori normale pentru diverse piese")
    void testECP_ValidLow(String partName) {
        assertDoesNotThrow(() -> inventoryService.addInhousePart(partName, 20.0, 7, 5, 15, 505));
    }

    @Test
    @Tag("ECP")
    @DisplayName("ECP: Valori mari")
    void testECP_ValidHigh() {
        assertDoesNotThrow(() -> inventoryService.addInhousePart("Rotor", 50.0, 18, 10, 20, 606));
    }

    // ECP - Invalide
    @Test
    @Tag("ECP")
    @DisplayName("ECP: Nume gol")
    void testECP_InvalidEmptyName() {
        ValidatorException ex = assertThrows(ValidatorException.class, () ->
                inventoryService.addInhousePart("", 12.0, 8, 5, 10, 707));
        assertTrue(ex.getMessage().contains("Name cannot be empty"));
    }

    @Test
    @Tag("ECP")
    @DisplayName("ECP: min > max")
    void testECP_InvalidMinMax() {
        ValidatorException ex = assertThrows(ValidatorException.class, () ->
                inventoryService.addInhousePart("Capac", 14.0, 7, 10, 5, 808));
        assertTrue(ex.getMessage().contains("Min cannot be greater than max"));
    }
}
