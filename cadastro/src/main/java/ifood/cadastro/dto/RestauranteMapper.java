package ifood.cadastro.dto;

//import Classes.Restaurante;
import ifood.cadastro.Restaurante;
import org.mapstruct.Mapper;


@Mapper(componentModel = "cdi")
public interface RestauranteMapper {


   public Restaurante toRestaurante(AdicionarRestauranteDTO dto);


}