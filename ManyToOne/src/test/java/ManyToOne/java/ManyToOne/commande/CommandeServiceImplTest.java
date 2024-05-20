package ManyToOne.java.ManyToOne.commande;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Log4j2
public class CommandeServiceImplTest {

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

    @DisplayName("Junit test for create Commmande and return Commmande")
    @Test
    void testCreateCommmande_thenReturnCommmande() {
            when(commandeRepository.save(commande7)).thenReturn(commande7);
            when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        ResponseEntity<Commande> createCommande =commandeServiceImplementation.createCommande(commande7,client.getId());


        assertThat(createCommande).isNotNull();
        assertThat(createCommande.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        log.info(createCommande.getBody());
         assertThat(createCommande.getBody())
                .isNotNull()
                .hasFieldOrPropertyWithValue("N_commande",0)
                .hasFieldOrPropertyWithValue("Nomcommande", commande7.getNomcommande());

        verify(commandeRepository, times(1)).save(commande7);


    }


    @DisplayName("Junit test for get list Commande and return list Commande")
    @Test
    void testGetAllCommande_thenReturnAllCommande(){
        when(commandeRepository.findAll()).thenReturn(commandeList);
        ResponseEntity<List<Commande>> getAllCommande = commandeServiceImplementation.getAllCommande();

        log.info(getAllCommande.getBody());
        assertEquals("Vase", getAllCommande.getBody().get(1).getNomcommande());
        assertEquals(2,getAllCommande.getBody().size());
        verify(commandeRepository, times(1)).findAll();

    }

    @DisplayName("Junit test for get Commande by id and return list Commande by id")
    @Test
    void testGetCommandeByClientId_thenReturnCommande() {

            when(clientRepository.findById(client5.getId())).thenReturn(Optional.of(client5));

            when(commandeRepository.findAllByClientId(client5.getId())).thenReturn(commandeList);

        ResponseEntity<List<Commande>> getAllCommandeByClientId = commandeServiceImplementation.getAllCommandeByClientId(client5.getId());


        log.info(getAllCommandeByClientId.getBody());
        assertThat(getAllCommandeByClientId).isNotNull();
        assertThat(getAllCommandeByClientId.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @DisplayName("Junit test for update Commande")
    @Test
    public void testUpdateCommande_Success() {
        when(commandeRepository.findById(commande.getN_commande())).thenReturn(Optional.of(commande));
        when(commandeRepository.save(commande)).thenReturn(commande);

        ResponseEntity<Commande> putUtilisateur = commandeServiceImplementation.putCommande(commande.getN_commande(),updatedCommande);
        log.info(putUtilisateur.getBody());



    }


    @DisplayName("Junit test for delete Commande")
    @Test
    public void testDeleteCommande_Success() {
        when(commandeRepository.findById(commande7.getN_commande())).thenReturn(Optional.of(commande7));

        ResponseEntity<String> deleteUtilisateur = commandeServiceImplementation.deleteCommande(commande7.getN_commande());

        log.info(deleteUtilisateur.getBody());
        assertThat(deleteUtilisateur).isNotNull();
        assertThat(deleteUtilisateur.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("Commande supprimée avec succès", deleteUtilisateur.getBody());

        verify(commandeRepository, times(1)).deleteById(commande7.getN_commande());


    }


}
