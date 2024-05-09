package ManyToOne.java.ManyToOne.repository;

import ManyToOne.java.ManyToOne.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Integer> {
    List<Commande> findAllByClientId(Integer clientId);

   // Optional<Commande> findById(Integer n_commande);



}

