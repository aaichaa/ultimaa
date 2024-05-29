package ManyToOne.java.ManyToOne.controller;
import ManyToOne.java.ManyToOne.model.Commande;
import ManyToOne.java.ManyToOne.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Commande")
public class CommandeController {
    @Autowired
    public CommandeService commandeService;
    @PostMapping("/clients/{clientId}")
    public ResponseEntity<Commande> createCommande(@PathVariable int clientId, @RequestBody Commande command){
        return commandeService.createCommande(command,clientId);
    }

    @GetMapping()
    public ResponseEntity<List<Commande>> getAllCommande(){

        return commandeService.getAllCommande();
    }
    @GetMapping("/{clientId}")
    public ResponseEntity<List<Commande>> getAllCommandesWithClientId(@PathVariable(value = "clientId") Integer clientId){

        return commandeService.getAllCommandeByClientId(clientId);
    }
/*
    @GetMapping("/com/{n_commande}")
    public Optional<Commande> findById(@PathVariable (value = "n_commande") Integer n_commande ){

        return commandeService.findByIdCommande(n_commande);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<Commande> putCommande(@PathVariable int id, @RequestBody Commande newcommande) {

        return commandeService.putCommande(id, newcommande);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommande (@PathVariable int id){
        return commandeService.deleteCommande(id);
    }




}
