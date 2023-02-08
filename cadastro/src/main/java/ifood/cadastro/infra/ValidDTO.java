package ifood.cadastro.infra;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidDTOValidator.class})
public @interface ValidDTO {

    String message() default ("com.github.ana.cadastro.infra.ValidDTO.message");
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}
