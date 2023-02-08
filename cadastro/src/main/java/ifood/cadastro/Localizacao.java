package ifood.cadastro;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "localizacao")
public class Localizacao extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public Double latitude;
    public Double longitude;


}
