package BBT_WBT;

import inventory.model.Inventory;
import inventory.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductLookupTest {
    private Inventory inventory;

    @BeforeEach
    public void setup() {
        inventory = new Inventory();
        inventory.addProduct(new Product(1, "Bujie", 250, 10, 0, 20, null));
        inventory.addProduct(new Product(2, "Motoras", 100.0, 20, 0, 30, null));
        inventory.addProduct(new Product(3, "Surubelnita", 50.0, 15, 10, 20, null));
    }


    @Test
    public void testLookup_withEmptyList() {
        inventory = new Inventory(); // listă goală, fără produse
        Product result = inventory.lookupProduct("anything");
        assertEquals(0, result.getProductId());
        assertNull(result.getName());
    }

    @Test
    public void testLookup_noMatchName() {
        Product result = inventory.lookupProduct("Piulita");
        assertEquals(0, result.getProductId());
        assertNull(result.getName());
    }
    @Test
    public void testLookup_noMatchId() {
        Product result = inventory.lookupProduct("4");
        assertEquals(0, result.getProductId());
        assertNull(result.getName());
    }

    @Test
    public void testLookup_matchByName_returnsProduct() {
        Product result = inventory.lookupProduct("Bujie");
        assertEquals(1, result.getProductId());
    }

    @Test
    public void testLookup_matchById_returnsProduct() {
        Product result = inventory.lookupProduct("1");
        assertEquals(1, result.getProductId());
    }

    @Test
    public void testLookup_matchLaterInList_returnsCorrectProduct() {
        Product result = inventory.lookupProduct("Surubelnita");
        assertEquals(3, result.getProductId());
    }

    @Test
    public void testLookup_matchLaterInList_returnsCorrectProductById() {
        Product result = inventory.lookupProduct("2");
        assertEquals(2, result.getProductId());
    }

}
