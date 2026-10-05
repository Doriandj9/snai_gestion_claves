package snai.projects.inventario.categorias;


import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import snai.projects.inventario.categorias.domain.Categoria;
import snai.projects.inventario.categorias.dtos.CreateCategoriaDTO;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import  static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceImpTest {

    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @BeforeEach
    void setUp() {
        categoriaService = new CategoriaServiceImp(categoriaRepository);
    }


    @Test
    void checkCrateCategoria(){
        //dado un dto devuelva una categoria
        CreateCategoriaDTO dto = new CreateCategoriaDTO("Computadoras");
        Categoria categoria = new Categoria("Computadoras");
        given(categoriaRepository.save(any())).will(returnsFirstArg());

        //when
        Categoria result =  categoriaService.crate(dto);

        verify(categoriaRepository).save(categoria);
        then(result).isEqualTo(categoria);
    }

    @Test
    void checkWrongCreateCategoria(){
        CreateCategoriaDTO dto = new CreateCategoriaDTO("Computadoras");
        Categoria categoria = new Categoria("Computadoras");

        given(categoriaRepository.findByNombreIgnoreCase("Computadoras")).willReturn(Optional.of(categoria));

        //when
        Throwable throwable = catchThrowable( () -> categoriaService.crate(dto));

        then(throwable).isInstanceOf(IllegalArgumentException.class);
    }

}
