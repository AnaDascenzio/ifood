package ifood.cadastro.dto;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Date;

public class BuscarRestauranteDTO {


    public String proprietario;
    public String cnpj;
    public String nome;
    @OneToOne(cascade = CascadeType.ALL)
    public LocalizacaoDTO localizacaoDTO;

    public Date dataCriacao;
    public Date dataAtualizacao;
}
