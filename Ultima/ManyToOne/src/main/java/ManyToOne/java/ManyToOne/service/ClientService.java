package ManyToOne.java.ManyToOne.service;

import ManyToOne.java.ManyToOne.model.Client;
import ManyToOne.java.ManyToOne.model.Commande;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    public Client creatClient(Client c);
    public ResponseEntity<List<Client>> getAllClient();
    public ResponseEntity<Client> getClient(int clientId);
   public ResponseEntity<String> deleteClient(int clientId);
   public ResponseEntity<Client> putClient (int clientId, Client client);





}
