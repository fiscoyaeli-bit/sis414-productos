package com.example.demov3;

import com.example.demov3.Entities.Producto;
import com.example.demov3.Repositories.ProductoRepository;
import com.example.demov3.Services.ProductoService;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTests {
    private final ProductoRepository repository = mock(ProductoRepository.class);
    private final ProductoService service = new ProductoService(repository);

    private Producto datos() {
        Producto producto = new Producto();
        producto.setNombre("F-001");
        producto.setCategoria("Ana");
        producto.setPrecio(125.5);
        producto.setStock(10);
        return producto;
    }

    @Test
    void crearGeneraUnaEntidadNuevaSinUsarElIdDelCategoria() {
        Producto entrada = datos();
        entrada.setId(99L);
        when(repository.save(any(Producto.class))).thenAnswer(call -> {
            Producto nueva = call.getArgument(0);
            assertNull(nueva.getId());
            nueva.setId(1L);
            return nueva;
        });
        Producto creada = service.crear(entrada);
        assertEquals(1L, creada.getId());
        assertEquals("F-001", creada.getNombre());
        assertEquals("Ana", creada.getCategoria());
        assertEquals(125.5, creada.getPrecio());
        assertEquals(10, creada.getStock());
    }

    @Test
    void actualizarConservaElIdDeLaRuta() {
        Producto existente = datos();
        existente.setId(1L);
        Producto entrada = datos();
        entrada.setId(99L);
        entrada.setCategoria("Luis");
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);
        Producto actualizada = service.actualizar(1L, entrada).orElseThrow();
        assertEquals(1L, actualizada.getId());
        assertEquals("Luis", actualizada.getCategoria());
        verify(repository).save(existente);
    }

    @Test
    void actualizarAusenteNoCreaOtraProducto() {
        when(repository.findById(8L)).thenReturn(Optional.empty());
        assertTrue(service.actualizar(8L, datos()).isEmpty());
        verify(repository, never()).save(any());
    }

    @Test
    void eliminarSoloBorraCuandoExiste() {
        when(repository.existsById(1L)).thenReturn(true);
        assertTrue(service.eliminar(1L));
        verify(repository).deleteById(1L);
        assertFalse(service.eliminar(8L));
        verify(repository, never()).deleteById(8L);
    }
}
