package ifood.cadastro;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "restaurante")
public class Restaurante extends PanacheEntityBase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String proprietario;
    public String cnpj;
    public String nome;

    @OneToOne(cascade = CascadeType.ALL)
    public Localizacao localizacao;

    @CreationTimestamp
    public Date dataCriacao;

    @UpdateTimestamp
    public Date dataAtualizacao;
}
