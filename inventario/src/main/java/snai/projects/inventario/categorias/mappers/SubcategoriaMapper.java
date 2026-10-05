package snai.projects.inventario.categorias.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import snai.projects.inventario.categorias.domain.Categoria;
import snai.projects.inventario.categorias.domain.Subcategoria;
import snai.projects.inventario.categorias.dtos.PayloadSubcategoria;
import snai.projects.inventario.categorias.dtos.SubcategoriaResponse;
import snai.projects.inventario.categorias.dtos.SubcategoriaWithCategoria;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubcategoriaMapper {

    public SubcategoriaResponse convertForSubcategoria(Subcategoria subcategoria) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<PayloadSubcategoria> payload = objectMapper.readValue(subcategoria.getPayload(),new TypeReference<List<PayloadSubcategoria>>(){});

        return new SubcategoriaResponse(
                subcategoria.getSubcategoriaId(),
                subcategoria.getNombre(),
                payload
        );
    }

    public List<SubcategoriaWithCategoria> convertSubcategoriaWithCategorias(List<Subcategoria> subcategorias) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<SubcategoriaWithCategoria> subcategoriasWithCategorias = new ArrayList<>();
        for (Subcategoria subcategoria : subcategorias) {
            List<PayloadSubcategoria> payload  =  objectMapper.readValue(subcategoria.getPayload(),new TypeReference<List<PayloadSubcategoria>>(){});
            Categoria categoria = (Categoria) Hibernate.unproxy(subcategoria.getCategoria());
            subcategoriasWithCategorias.add(new SubcategoriaWithCategoria(subcategoria.getSubcategoriaId(),subcategoria.getNombre(),payload, subcategoria.getCreatedAt(), subcategoria.getUpdatedAt(),categoria));
        }

        return subcategoriasWithCategorias;
    }
}
