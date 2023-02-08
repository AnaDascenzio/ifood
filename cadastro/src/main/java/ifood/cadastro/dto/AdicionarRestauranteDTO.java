package ifood.cadastro.dto;

import ifood.cadastro.Restaurante;
import ifood.cadastro.infra.DTO;

import javax.validation.Constraint;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AdicionarRestauranteDTO implements DTO {


    @NotEmpty
    @NotNull
    public String proprietario;

    @Pattern(regexp = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}\\/[0-9]{4}\\-[0-9]{2}")
    @NotNull
    public String cnpj;


    public String nome;


    public LocalizacaoDTO localizacao;

    public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (Restaurante.find("cnpj", cnpj).count() > 0) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("cnpj já cadastrado")
                    .addPropertyNode("cnpj")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }



}
