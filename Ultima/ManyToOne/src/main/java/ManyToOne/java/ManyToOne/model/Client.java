package ManyToOne.java.ManyToOne.model;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Data
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "client_id")
    private Integer id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private  String quartier;

    //@OneToMany (mappedBy = "client")
   // private List<Commande> commande;

    /*@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_emp", referencedColumnName = "client_id")
    private List<Commande> commandes;*/

}
