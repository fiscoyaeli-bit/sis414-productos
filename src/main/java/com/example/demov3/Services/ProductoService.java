package com.example.demov3.Services;

import com.example.demov3.Entities.Producto;
import com.example.demov3.Repositories.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public Producto crear(Producto datos) {
        Producto producto = new Producto();
        copiarDatos(datos, producto);
        return repository.save(producto);
    }

    public List<Producto> listar() { return repository.findAll(); }

    public Optional<Producto> buscarPorId(Long id) { return repository.findById(id); }

    public Optional<Producto> actualizar(Long id, Producto datos) {
        return repository.findById(id).map(producto -> {
            copiarDatos(datos, producto);
            return repository.save(producto);
        });
    }

    public boolean eliminar(Long id) {
        if (!repository.existsById(id)) { return false; }
        repository.deleteById(id);
        return true;
    }

    private void copiarDatos(Producto origen, Producto destino) {
        destino.setNombre(origen.getNombre());
        destino.setCategoria(origen.getCategoria());
        destino.setPrecio(origen.getPrecio());
        destino.setStock(origen.getStock());
    }
}
