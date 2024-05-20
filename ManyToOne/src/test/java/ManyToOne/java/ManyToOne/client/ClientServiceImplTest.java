package ManyToOne.java.ManyToOne.client;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Log4j2
public class ClientServiceImplTest {
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



    @DisplayName("JUnit test for create client and return client")
    @Test
    void testCreateClient_thenReturnClient() {
        // Arrange
        when(clientRepository.save(client)).thenReturn(client);
        // Act
        Client createdClient = clientServiceImplementation.creatClient(client);
        // Assert
        assertThat(createdClient).isNotNull();
        log.info(createdClient);  // Utilisez String.valueOf pour éviter les erreurs de type

        assertThat(createdClient.getId()).isNotNull();
        assertThat(createdClient.getId()).isEqualTo(7);
        assertThat(createdClient.getNom()).isEqualTo(client.getNom());

        assertThat(createdClient)
                .hasFieldOrPropertyWithValue("id",client.getId());
    }


    @DisplayName("Junit test for get list Client and return list Client")
    @Test
    void testGetAllClient_thenReturnAllClient(){
        when(clientRepository.findAll()).thenReturn(clientList);
        ResponseEntity<List<Client>> getAllClient = clientServiceImplementation.getAllClient();
        log.info(getAllClient.getBody());
        assertEquals("Diallo", getAllClient.getBody().get(0).getNom());
        assertEquals(2,getAllClient.getBody().size());
        verify(clientRepository, times(1)).findAll();

    }

    @DisplayName("Junit test for get Client by id and return list Client by id")
    @Test
    void testGetClientById_thenReturnClient(){
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        ResponseEntity<Client> getClient = clientServiceImplementation.getClient(client.getId());
        log.info(getClient.getBody());
        assertThat(getClient).isNotNull();
        assertThat(getClient.getStatusCode()).isEqualTo(HttpStatus.OK);


    }


    @DisplayName("Junit test for update Client")
    @Test
    public void testUpdateClient_Success() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);

        ResponseEntity<Client> putClient = clientServiceImplementation.putClient(client.getId(), newClient);

        log.info(putClient.getBody());



    }


    @DisplayName("Junit test for delete Client")
    @Test
    public void testDeleteClient_Success() {
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        ResponseEntity<String>  deleteClient = clientServiceImplementation.deleteClient(client.getId());

        log.info(deleteClient.getBody());
        assertThat(deleteClient).isNotNull();
        assertThat(deleteClient.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("Client supprimé avec succès", deleteClient.getBody());

        verify(clientRepository, times(1)).deleteById(client.getId());


    }



}