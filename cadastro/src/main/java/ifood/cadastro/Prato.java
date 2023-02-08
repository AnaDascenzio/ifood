package ifood.cadastro;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "prato")
public class Prato extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String nome;
    public String descricao;

    @ManyToOne
    public Restaurante restaurante;

    public BigDecimal preco;
}
