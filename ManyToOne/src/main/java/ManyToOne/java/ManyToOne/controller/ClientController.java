 package ManyToOne.java.ManyToOne.controller;

import ManyToOne.java.ManyToOne.model.Client;
import ManyToOne.java.ManyToOne.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Client")
public class ClientController {
    @Autowired
    public ClientService clientService;

    @PostMapping()
    public Client creatClient(@RequestBody Client c) {
        return clientService.creatClient(c);
    }

    @GetMapping()
    public ResponseEntity<List<Client>> getAllClient() {
        return clientService.getAllClient();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable int id) {
        return clientService.getClient(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable int id) {

       return clientService.deleteClient(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client>  putClient(@RequestBody Client client, @PathVariable int id) {
        return clientService.putClient(id, client);
    }
}