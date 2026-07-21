# Conversación con Gemini - Implementación de Programación

## Enlace de conversación con Gemini

https://share.gemini.google/dU4zkeWQEGoQ

## Análisis inicial

Se utilizó Gemini para analizar la clase `Order` del proyecto Java antes
de realizar las pruebas unitarias.

La clase analizada fue:

    src/main/java/es/upm/grise/order/Order.java

La clase contiene:

-   ArrayList`<Item>`{=html} items
-   Status status
-   Invoice invoice

Los métodos principales evaluados fueron:

-   Constructor Order()
-   addItem()
-   removeItem()

## Análisis del constructor

Gemini identificó que el constructor inicializa:

``` java
items = new ArrayList<Item>();
status = null;
```

Por lo tanto, se verificó que una nueva orden:

-   Tenga una lista de elementos vacía.
-   Tenga estado null.
-   No tenga factura asignada.

## Análisis de addItem()

Se revisó la lógica del método:

-   No permite agregar productos cuando el estado es PLACED.
-   Valida que el precio no sea negativo.
-   Valida que la cantidad sea mayor que cero.
-   Si el producto ya existe, aumenta la cantidad.
-   Actualiza el precio solamente cuando el nuevo precio es mayor.
-   Si es el primer elemento agregado, cambia el estado a UNCONFIRMED.

## Análisis de removeItem()

Se verificó:

-   Eliminación correcta del producto existente.
-   Lanzamiento de NonExistingItemException cuando el producto no
    existe.
-   Cambio del estado a null cuando la orden queda vacía.

## Archivos trabajados

Archivo principal:

    src/main/java/es/upm/grise/order/Order.java

Archivo de pruebas:

    src/test/java/es/upm/grise/order/OrderTest.java

La implementación se realizó mediante pruebas unitarias utilizando JUnit
5.
