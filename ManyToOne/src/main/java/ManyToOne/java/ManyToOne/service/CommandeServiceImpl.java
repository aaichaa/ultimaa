package ManyToOne.java.ManyToOne.service;


import ManyToOne.java.ManyToOne.exceptions.httpexceptions.NotFoundException;
import ManyToOne.java.ManyToOne.model.Client;
import ManyToOne.java.ManyToOne.model.Commande;
import ManyToOne.java.ManyToOne.repository.ClientRepository;
import ManyToOne.java.ManyToOne.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
@Service
public class CommandeServiceImpl implements CommandeService {
    @Autowired
    public CommandeRepository commandeRepo;
    @Autowired
    public ClientRepository clientRepository;



    private Optional<Client> getIdClient(int clientId){
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        return optionalClient;
    }

    @Override
    public ResponseEntity<Commande> createCommande(Commande command, int clientId) {

        Optional<Client> optionalClient = getIdClient(clientId);
        if (optionalClient.isPresent()) {
            //Client client = clientOptional.get();
            command.setClient(optionalClient.get());

            return ResponseEntity.status(HttpStatus.CREATED).body(commandeRepo.save(command));
        }
        else {
            throw new NotFoundException("Client id introuvable");
        }


    }


    @Override
    public ResponseEntity<List<Commande>> getAllCommandeByClientId(Integer clientId) {
        Optional<Client> optionalClient = getIdClient(clientId);
        if(optionalClient.isPresent()){
            List<Commande> commandes = commandeRepo.findAllByClientId(clientId);
            return ResponseEntity.ok(commandes);
        } else{
            throw new NotFoundException("Aucun client avec ce id");
        }
    }


    private Optional<Commande> getId(int commandeId) {
        Optional<Commande> optionalCommande = commandeRepo.findById(commandeId);

        return optionalCommande;
    }

    @Override
    public ResponseEntity<List<Commande>> getAllCommande() {
        List<Commande> clients = commandeRepo.findAll();
        return  ResponseEntity.ok(clients);
    }



    @Override
    public ResponseEntity<Commande> putCommande(int commandeId, Commande newcommande) {
        Optional<Commande> optionalCommande = getId(commandeId);
        if (optionalCommande.isPresent()){
            Commande commande = optionalCommande.get();
            commande.setNomcommande(newcommande.getNomcommande());
            commande.setDatecommande(newcommande.getDatecommande());

            return ResponseEntity.ok(commandeRepo.save(commande));
        }else{
            throw new NotFoundException("Aucune commande avec ce id");
        }

    }

    @Override
    public ResponseEntity<String> deleteCommande(int commandeId) {
        Optional<Commande> optionalCommande = getId(commandeId);
        if (optionalCommande.isPresent()){
            commandeRepo.deleteById(commandeId);
            return ResponseEntity.ok("Commande supprimée avec succès");

        }else {
            throw new NotFoundException("cette commande n'existe pas");
        }

    }









}
