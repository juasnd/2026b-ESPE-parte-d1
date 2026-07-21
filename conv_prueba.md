# Conversación con Gemini - Pruebas Unitarias

## Enlace de conversación con Gemini

https://share.gemini.google/dU4zkeWQEGoQ

## Creación de pruebas unitarias

Se utilizó Gemini para crear y revisar las pruebas unitarias de la
clase:

    src/test/java/es/upm/grise/order/OrderTest.java

## Pruebas implementadas

### Constructor

Se validó:

-   La lista de items no sea null.
-   La lista inicie vacía.
-   El estado inicial sea null.
-   La factura inicial sea null.

### addItem()

Se probaron los siguientes escenarios:

Caso correcto:

-   Agregar un producto válido.
-   Cambiar el estado a UNCONFIRMED.

Caso producto existente:

-   Aumentar la cantidad del producto.
-   Actualizar el precio cuando el nuevo precio es mayor.
-   Mantener el precio cuando el nuevo precio es menor.

Casos inválidos:

-   Precio negativo.
-   Cantidad igual a cero.
-   Cantidad negativa.

### Estado PLACED

Se agregó una prueba utilizando reflexión para modificar el estado
privado de la orden a:

    Status.PLACED

y comprobar que al intentar agregar otro elemento se lance:

    CannotAddItemsToPlacedOrderException

### removeItem()

Se validó:

-   Eliminación correcta del elemento.
-   Estado vuelve a null cuando la lista queda vacía.
-   Mantener estado UNCONFIRMED cuando todavía existen elementos.
-   Lanzar NonExistingItemException cuando se intenta eliminar un
    elemento inexistente.

## Resultado final

La suite de pruebas cubre los principales caminos de ejecución de la
clase Order y valida las reglas de negocio definidas.

Archivo generado:

    OrderTest.java
