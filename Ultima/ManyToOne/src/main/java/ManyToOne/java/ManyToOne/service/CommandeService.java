package ManyToOne.java.ManyToOne.service;

import ManyToOne.java.ManyToOne.model.Commande;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CommandeService {
    public ResponseEntity<Commande> createCommande(Commande command, int clientId);
    public ResponseEntity<List<Commande>> getAllCommande();

     public ResponseEntity<List<Commande>> getAllCommandeByClientId(Integer clientId);
  //  Optional<Commande> findByIdCommande(Integer n_commande);

    public ResponseEntity<String> deleteCommande(int commandeId);
    public ResponseEntity<Commande> putCommande(int commandeId, Commande newcommande);





}
