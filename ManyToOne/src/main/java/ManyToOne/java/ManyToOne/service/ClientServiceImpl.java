package ManyToOne.java.ManyToOne.service;

import ManyToOne.java.ManyToOne.exceptions.httpexceptions.NotFoundException;
import ManyToOne.java.ManyToOne.model.Client;
import ManyToOne.java.ManyToOne.model.Commande;
import ManyToOne.java.ManyToOne.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService{
    @Autowired
    public ClientRepository clientRepos;
    @Override
    public Client creatClient(Client c) {

        return clientRepos.save(c);
    }

    @Override
    public ResponseEntity<List<Client>> getAllClient() {

        return ResponseEntity.ok(clientRepos.findAll());
    }

    private Optional<Client> getId(int clientId){
        Optional<Client> optionalClient = clientRepos.findById(clientId);
        return optionalClient;
    }

   @Override
    public ResponseEntity<Client> getClient(int clientId) {
        Optional<Client> optionalClient = getId(clientId);

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            return ResponseEntity.ok(client);
        } else {
            throw new NotFoundException("Ce ID n'existe pas, merci");
        }
    }

   @Override
    public ResponseEntity<String>  deleteClient(int clientId) {
       Optional<Client> optionalClient = getId(clientId);
       if (optionalClient.isPresent()){
           clientRepos.deleteById(clientId);
           return ResponseEntity.ok("Client supprimé avec succès");
       }else{
           throw new NotFoundException("ce client n'existe pas :)");
       }




    }



    @Override
    public ResponseEntity<Client> putClient(int clientId, Client newClient) {
        Optional<Client> optionalClient = clientRepos.findById(clientId);
        if (optionalClient.isPresent()){
            Client clients = optionalClient.get();
            clients.setNom(newClient.getNom());
            clients.setPrenom(newClient.getPrenom());
            clients.setQuartier(newClient.getQuartier());

            return ResponseEntity.ok(clientRepos.save(clients));
        }else{
            throw new NotFoundException("Ce id est introuvable desolé");
        }




    }
























}
