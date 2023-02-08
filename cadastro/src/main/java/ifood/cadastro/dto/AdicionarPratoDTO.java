package ifood.cadastro.dto;

import ifood.cadastro.Restaurante;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AdicionarPratoDTO {

    @NotEmpty
    @NotNull
    public String nome;

    public String descricao;

    @ManyToOne(cascade = CascadeType.ALL)
    public Restaurante restaurante;

}
