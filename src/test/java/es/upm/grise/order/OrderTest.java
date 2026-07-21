package es.upm.grise.order;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import es.upm.grise.order.exceptions.CannotAddItemsToPlacedOrderException;
import es.upm.grise.order.exceptions.IncorrectItemException;
import es.upm.grise.order.exceptions.NonExistingItemException;

public class OrderTest {

    private Product createProduct(long id) {
        Product product = new Product();
        product.id = id;
        return product;
    }

    @Test
    public void testConstructor() {
        Order order = new Order();

        assertNotNull(order.getItems());
        assertTrue(order.getItems().isEmpty());
        assertNull(order.getStatus());
        assertNull(order.getInvoice());
    }

    @Test
    public void testAddItemCorrecto()
            throws CannotAddItemsToPlacedOrderException, IncorrectItemException {
        Order order = new Order();
        Product product = createProduct(1);
        Item item = new Item(product, 2, 10);

        order.addItem(item);

        assertEquals(1, order.getItems().size());
        assertEquals(Status.UNCONFIRMED, order.getStatus());
        assertEquals(2, order.getItems().get(0).getQuantity());
    }

    @Test
    public void testAddItemMismoProductoAumentaCantidadYActualizaPrecioMayor()
            throws CannotAddItemsToPlacedOrderException, IncorrectItemException {
        Order order = new Order();
        Product product = createProduct(1);

        order.addItem(new Item(product, 2, 10));
        order.addItem(new Item(product, 3, 15));

        assertEquals(1, order.getItems().size());
        assertEquals(5, order.getItems().get(0).getQuantity());
        assertEquals(15, order.getItems().get(0).getPrice());
    }

    @Test
    public void testAddItemMismoProductoPrecioMenorConservaPrecioMayor()
            throws CannotAddItemsToPlacedOrderException, IncorrectItemException {
        Order order = new Order();
        Product product = createProduct(1);

        order.addItem(new Item(product, 2, 20.0));
        order.addItem(new Item(product, 1, 10.0));

        assertEquals(1, order.getItems().size());
        assertEquals(3, order.getItems().get(0).getQuantity());
        assertEquals(20.0, order.getItems().get(0).getPrice());
    }

    @Test
    public void testAddItemPrecioNegativo() {
        Order order = new Order();
        Product product = createProduct(1);
        Item item = new Item(product, 1, -5);

        assertThrows(
            IncorrectItemException.class,
            () -> order.addItem(item)
        );
    }

    @Test
    public void testAddItemCantidadCero() {
        Order order = new Order();
        Product product = createProduct(1);
        Item item = new Item(product, 0, 10);

        assertThrows(
            IncorrectItemException.class,
            () -> order.addItem(item)
        );
    }

    @Test
    public void testNoAgregarItemCuandoEstadoPlaced() throws Exception {
        Order order = new Order();
        Product product = createProduct(1);
        order.addItem(new Item(product, 1, 10));

        // Forzamos el estado a PLACED para testear la excepción
        Field statusField = Order.class.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(order, Status.PLACED);

        Item newItem = new Item(product, 1, 10);

        assertThrows(
            CannotAddItemsToPlacedOrderException.class,
            () -> order.addItem(newItem)
        );
    }

    @Test
    public void testRemoveItemVacíaListaYReseteaEstado() throws Exception {
        Order order = new Order();
        Product product = createProduct(1);
        Item item = new Item(product, 2, 10);

        order.addItem(item);
        order.removeItem(item);

        assertTrue(order.getItems().isEmpty());
        assertNull(order.getStatus());
    }

    @Test
    public void testRemoveItemConservaEstadoSiQuedanElementos() throws Exception {
        Order order = new Order();
        Product p1 = createProduct(1);
        Product p2 = createProduct(2);

        Item item1 = new Item(p1, 1, 10);
        Item item2 = new Item(p2, 1, 15);

        order.addItem(item1);
        order.addItem(item2);

        order.removeItem(item1);

        assertEquals(1, order.getItems().size());
        assertEquals(Status.UNCONFIRMED, order.getStatus());
    }

    @Test
    public void testRemoveItemNoExiste() {
        Order order = new Order();
        Product product = createProduct(1);
        Item item = new Item(product, 1, 10);

        assertThrows(
            NonExistingItemException.class,
            () -> order.removeItem(item)
        );
    }
}