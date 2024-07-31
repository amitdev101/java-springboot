package com.example.ecommercesystem.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class ProductTest {

    private Product product1;
    private Product product2;

    @BeforeAll
    static void init() {
        System.out.println("Before All Tests");
    }

    @AfterAll
    static void tearDown() {
        System.out.println("After All Tests");
    }

    @BeforeEach
    public void setUp() {
        product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setDescription("A high-end gaming laptop.");
        product1.setPrice(1200.00);

        product2 = new Product();
        product2.setId(2L);
        product2.setName("Smartphone");
        product2.setDescription("A latest model smartphone.");
        product2.setPrice(800.00);
        System.out.println("Before Each Test");
    }

    @AfterEach
    void cleanUp() {
        System.out.println("After Each Test");
    }

//    @Test
//    public void testGettersAndSetters() {
//        Product product = new Product();
//        product.setId(3L);
//        product.setName("Tablet");
//        product.setDescription("A tablet with a large screen.");
//        product.setPrice(500.00);
//
//        assertEquals(3L, product.getId());
//        assertEquals("Tablet", product.getName());
//        assertEquals("A tablet with a large screen.", product.getDescription());
//        assertEquals(500.00, product.getPrice());
//    }

    @Test
    @DisplayName("Test Getters and Setters")
    @Tag("getter-setter")
    void testGettersAndSetters() {
        Product product = new Product();
        product.setId(3L);
        product.setName("Tablet");
        product.setDescription("A tablet with a large screen.");
        product.setPrice(500.00);

        assertAll("product properties",
                () -> assertEquals(3L, product.getId(), "ID should be 3"),
                () -> assertEquals("Tablet", product.getName(), "Name should be 'Tablet'"),
                () -> assertEquals("A tablet with a large screen.", product.getDescription(), "Description should match"),
                () -> assertEquals(500.00, product.getPrice(), "Price should be 500.00")
        );
    }

    @Test
    public void testEquals() {
        Product anotherProduct = new Product();
        anotherProduct.setId(1L);
        anotherProduct.setName("Laptop");
        anotherProduct.setDescription("A high-end gaming laptop.");
        anotherProduct.setPrice(1200.00);

        assertTrue(product1.equals(anotherProduct));
        assertFalse(product1.equals(product2));
    }

    @Test
    public void testHashCode() {
        Product anotherProduct = new Product();
        anotherProduct.setId(1L);
        anotherProduct.setName("Laptop");
        anotherProduct.setDescription("A high-end gaming laptop.");
        anotherProduct.setPrice(1200.00);

        assertEquals(product1.hashCode(), anotherProduct.hashCode());
        assertNotEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    public void testToString() {
        String expectedString = "Product(id=1, name=Laptop, description=A high-end gaming laptop., price=1200.0)";
        assertEquals(expectedString, product1.toString());
    }

    @Test
    public void testNoArgsConstructor() {
        Product product = new Product();
        assertNull(product.getId());
        assertNull(product.getName());
        assertNull(product.getDescription());
        assertEquals(0.0, product.getPrice());
    }

    @Test
    void testSetNameThrowsNullPointerException() {
        Product product = new Product();

        // Using assertThrows to verify NullPointerException is thrown
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> product.setName(null),
                "Expected setName(null) to throw, but it didn't"
        );

        // Optionally, you can also assert the exception message
        assertEquals("Name cannot be null", exception.getMessage());
    }
}
