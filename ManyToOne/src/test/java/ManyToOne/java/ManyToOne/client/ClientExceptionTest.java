package ManyToOne.java.ManyToOne.client;



import ManyToOne.java.ManyToOne.exceptions.httpexceptions.NotFoundException;
import ManyToOne.java.ManyToOne.model.Client;
import ManyToOne.java.ManyToOne.model.Commande;
import ManyToOne.java.ManyToOne.repository.ClientRepository;
import ManyToOne.java.ManyToOne.service.ClientServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Log4j2
public class ClientExceptionTest {
    @InjectMocks
    ClientServiceImpl clientServiceImplementation;

    @Mock
    ClientRepository clientRepository;

    private Client client;

    List<Client> clientList;

    private Client client5;


    private Client newClient;


    @BeforeEach
    void setup() {
        client = new Client();
        client.setId(7);
        client.setNom("Diallo");
        client.setPrenom("Aissatou");
        client.setQuartier("Dijon");


        newClient = new Client();
        newClient.setNom("dialoooooo");
        newClient.setPrenom("Aissatouuuuuuuuuuuu");
        newClient.setQuartier("Dijonnnnnnnnnn");


        client5 = new Client();
        client5.setId(9);
        client5.setNom("Sow");
        client5.setPrenom("Aissatou");
        client5.setQuartier("Dijon");

        clientList = new ArrayList<>();
        clientList.add(client);
        clientList.add(client5);


    }


    @DisplayName("JUnit test for getClient")
    @Test
    void testgetClient_thenThrowNotFoundException() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(NotFoundException.class, () ->{
            clientServiceImplementation.getClient(client.getId());
        });
        assertEquals("Ce ID n'existe pas, merci", exception.getMessage());
        log.info(exception.getMessage());

        verify(clientRepository, times(1)).findById(client.getId());

    }




    @DisplayName("JUnit test for putClient")
    @Test
    void testputClient_thenThrowNotFoundException() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(NotFoundException.class, () ->{
            clientServiceImplementation.putClient(client.getId(), newClient);
        });
        assertEquals("Ce id est introuvable desolé", exception.getMessage());
        log.info(exception.getMessage());

        verify(clientRepository, times(1)).findById(client.getId());

    }


    @DisplayName("JUnit test for deleteClient")
    @Test
    void testdeleteClient_thenThrowNotFoundException() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(NotFoundException.class, () ->{
            clientServiceImplementation.deleteClient(client.getId());
        });
        assertEquals("ce client n'existe pas :)", exception.getMessage());
        log.info(exception.getMessage());

        verify(clientRepository, times(1)).findById(client.getId());

    }

}
