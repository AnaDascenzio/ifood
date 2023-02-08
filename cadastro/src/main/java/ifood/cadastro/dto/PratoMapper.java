package ifood.cadastro.dto;

import ifood.cadastro.Prato;
import org.mapstruct.Mapper;



@Mapper(componentModel = "cdi")
public interface PratoMapper {

    PratoDTO toDTO(Prato p);
    Prato toPrato(AdicionarPratoDTO dto);
}
