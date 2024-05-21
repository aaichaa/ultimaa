package ManyToOne.java.ManyToOne.commande;



import ManyToOne.java.ManyToOne.exceptions.httpexceptions.NotFoundException;
import ManyToOne.java.ManyToOne.model.Client;
import ManyToOne.java.ManyToOne.model.Commande;
import ManyToOne.java.ManyToOne.repository.ClientRepository;
import ManyToOne.java.ManyToOne.repository.CommandeRepository;
import ManyToOne.java.ManyToOne.service.ClientServiceImpl;
import ManyToOne.java.ManyToOne.service.CommandeServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.net.jsse.PEMFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Log4j2
public class CommandeExceptionTest {

    @InjectMocks
    CommandeServiceImpl commandeServiceImplementation;

    @Mock
    CommandeRepository commandeRepository;

    @InjectMocks
    ClientServiceImpl clientServiceImplementation;

    @Mock
    ClientRepository clientRepository;

    private Client client;

    private Client client5;

    List<Commande> commandeList;

    List<Client> clientList;


    private Commande commande;

    private Commande commande7;

    private Commande updatedCommande;





    @BeforeEach
    void setup() {

        client = new Client();
        client.setId(8);
        client.setNom("Diallo");
        client.setPrenom("Aissatou");
        client.setQuartier("Dijon");



        client5 = new Client();
        client5.setId(9);
        client5.setNom("Sow");
        client5.setPrenom("Aissatou");
        client5.setQuartier("Dijon");

        clientList = new ArrayList<>();
        clientList.add(client);
        clientList.add(client5);

        commande = new Commande();
        commande.setN_commande(2);
        commande.setDatecommande("12-02-2000");
        commande.setNomcommande("Bidon");
        commande.setClient(client5);

        updatedCommande = new Commande();
        updatedCommande.setDatecommande("12-02-10000");
        updatedCommande.setNomcommande("BULLE");
        updatedCommande.setClient(client5);


        commande7 = new Commande();
        commande7.setN_commande(0);
        commande7.setDatecommande("12-02-1999");
        commande7.setNomcommande("Vase");
        commande7.setClient(client5);

        commandeList = new ArrayList<>();
        commandeList.add(commande);
        commandeList.add(commande7);


    }

    @DisplayName("JUnit test for createCommande")
    @Test
    void testcreateCommande_thenThrowNotFoundException() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(NotFoundException.class, () ->{
            commandeServiceImplementation.createCommande(commande, client.getId());
        });
        assertEquals("Client id introuvable", exception.getMessage());
        log.info(exception.getMessage());

        verify(clientRepository, times(1)).findById(client.getId());

    }



    @DisplayName("JUnit test for getAllCommandeByClientId")
    @Test
    void testgetAllCommandeByClientId_thenThrowNotFoundException() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(NotFoundException.class, () ->{
            commandeServiceImplementation.getAllCommandeByClientId(client.getId());
        });
        assertEquals("Aucun client avec ce id", exception.getMessage());
        log.info(exception.getMessage());

        verify(clientRepository, times(1)).findById(client.getId());

    }


    @DisplayName("JUnit test for putCommande")
    @Test
    void testputCommande_thenThrowNotFoundException() {
        when(commandeRepository.findById(commande.getN_commande())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(NotFoundException.class, () ->{
            commandeServiceImplementation.putCommande( commande.getN_commande(),updatedCommande);
        });
        assertEquals("Aucune commande avec ce id", exception.getMessage());
        log.info(exception.getMessage());


    }


    @DisplayName("JUnit test for deleteCommande")
    @Test
    void testdeleteCommande_thenThrowNotFoundException() {
        when(commandeRepository.findById(commande.getN_commande())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(NotFoundException.class, () ->{
            commandeServiceImplementation.deleteCommande(commande.getN_commande());
        });
        assertEquals("cette commande n'existe pas", exception.getMessage());
        log.info(exception.getMessage());


    }

}
