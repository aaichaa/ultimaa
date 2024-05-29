package ManyToOne.java.ManyToOne.commande;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import ManyToOne.java.ManyToOne.controller.CommandeController;
import ManyToOne.java.ManyToOne.exceptions.ErrorMessageService;
import ManyToOne.java.ManyToOne.model.Client;
import ManyToOne.java.ManyToOne.model.Commande;
import ManyToOne.java.ManyToOne.service.CommandeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Diallo iliassou
 * @version 0.0.1
 * @since 0.0.1
 */
@Log4j2
@WebMvcTest(value = CommandeController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class CommandeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommandeService commandeService;

    /**
     * since @ControllerAdvice scans all controllers, so they all have a dependency to board controller
     * and here board controller is ErrorMessageController
     * and it has a dependency to ErrorMessageService so they will all have a dependency to ErrorMessageService
     */
    @MockBean
    private ErrorMessageService errorMessageService;


    @Autowired
    private ObjectMapper objectMapper;
    private Commande commande;
    private final String uri = "/Commande";

    private  ResponseEntity<Commande> commandeReturn;

    private String commandeInJSON;

    private Client client;

    private final int clientId = 2;

    private Commande commande1;
    private Commande updatedCommande;



    List<Commande> commandeList;



    @BeforeEach
    void setup() {
        client = new Client();
        client.setId(8);
        client.setNom("Diallo");
        client.setPrenom("Aissatou");
        client.setQuartier("Dijon");


        commande = new Commande();
        commande.setN_commande(2);
        commande.setDatecommande("12-02-2000");
        commande.setNomcommande("Bidon");
        commande.setClient(client);


        commande1 = new Commande();
        commande1.setN_commande(2);
        commande1.setDatecommande("12-02-2000");
        commande1.setNomcommande("Bidon");
        commande1.setClient(client);

        commandeList = new ArrayList<>();
        commandeList.add(commande1);
        commandeList.add(commande);

        updatedCommande = new Commande();
        updatedCommande.setDatecommande("12-02-10000");
        updatedCommande.setNomcommande("BULLE");
        updatedCommande.setClient(client);

        try {
            commandeInJSON = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(commande);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }

        commandeReturn = ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(commande);
    }



    @DisplayName("Junit test for create client and return client")
    @Test
    public void testCreateClient() throws Exception {


        when(commandeService.createCommande(any(Commande.class),eq(clientId)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(commande));

        String uriUpdate = uri.concat("/clients/{clientId}");


        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uriUpdate,clientId)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(commandeInJSON);
        ResultActions response = mockMvc.perform(requestBuilder);

        response
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.n_commande",
                        equalTo(commande.getN_commande())
                ))
                .andExpect(jsonPath("$.nomcommande",
                        equalTo(commande.getNomcommande())
                ))
                .andExpect(jsonPath("$.datecommande",
                        equalTo(commande.getDatecommande())
                ))
                .andExpect(jsonPath("$.client.id",
                        equalTo(commande.getClient().getId())
                ))
                .andDo(print());

    }


    @DisplayName("Junit test for get commande and return commande")
    @Test
    public void testGetAllCommande() throws Exception {


        when(commandeService.getAllCommande()).thenReturn(ResponseEntity.ok(commandeList));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri);

        ResultActions response = mockMvc.perform(requestBuilder);


        response
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.[1].n_commande",
                        equalTo((commandeList.get(1).getN_commande()))
                ))
                .andExpect(jsonPath("$.[0].nomcommande",
                        equalTo(commandeList.get(0).getNomcommande())
                ))
                .andExpect(jsonPath("$.[0].datecommande",
                        equalTo(commandeList.get(0).getDatecommande())
                ))
                .andExpect(jsonPath("$.[1].client.id",
                      equalTo(commandeList.get(1).getClient().getId())
                ))
                .andDo(print());
    }

    @DisplayName("Junit test for get commande by client id and return commande")
    @Test
    public void testGetCommande() throws Exception {


        when(commandeService.getAllCommandeByClientId(client.getId()))
                .thenReturn(ResponseEntity.ok(commandeList));


        String uriUpdate = uri.concat("/{clientId}");


        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uriUpdate, client.getId());

        ResultActions response = mockMvc.perform(requestBuilder);


        response
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.[1].n_commande",
                        equalTo((commandeList.get(1).getN_commande()))
                ))
                .andExpect(jsonPath("$.[0].nomcommande",
                        equalTo(commandeList.get(0).getNomcommande())
                ))
                .andExpect(jsonPath("$.[0].datecommande",
                        equalTo(commandeList.get(0).getDatecommande())
                ))
                .andExpect(jsonPath("$.[1].client.id",
                        equalTo(commandeList.get(1).getClient().getId())
                ))
                .andDo(print());


    }

    @DisplayName("Junit test for update commande by id ")
    @Test
    public void testUpdateCommande() throws Exception {
        commande.setNomcommande(updatedCommande.getNomcommande());
        commande.setDatecommande(updatedCommande.getDatecommande());
        commande.setClient(updatedCommande.getClient());

        when(commandeService.putCommande(commande.getN_commande(),updatedCommande)).thenReturn(ResponseEntity.ok(commande));

        String updateUri = uri + "/{id}";

        String commandeJson = objectMapper.writeValueAsString(updatedCommande);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(updateUri,commande.getN_commande())
                .contentType(MediaType.APPLICATION_JSON)
                .content(commandeJson);
        ResultActions response = mockMvc.perform(requestBuilder);

        response
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.n_commande",
                        equalTo(commande.getN_commande())
                ))
                .andExpect(jsonPath("$.nomcommande",
                        equalTo(commande.getNomcommande())
                ))
                .andExpect(jsonPath("$.datecommande",
                        equalTo(commande.getDatecommande())
                ))
                .andExpect(jsonPath("$.client.id",
                        equalTo(commande.getClient().getId())
                ))
                .andDo(print());


    }

    @DisplayName("Junit test for update2 commande by id ")
    @Test
    public void testUpdate2Commande() throws Exception {

        BeanUtils.copyProperties(updatedCommande, commande, "n_commande");

        when(commandeService.putCommande(commande.getN_commande(),updatedCommande)).thenReturn(ResponseEntity.ok(commande));

        String updateUri = uri + "/{id}";

        String commandeJson = objectMapper.writeValueAsString(updatedCommande);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(updateUri,commande.getN_commande())
                .contentType(MediaType.APPLICATION_JSON)
                .content(commandeJson);
        ResultActions response = mockMvc.perform(requestBuilder);

        response
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.n_commande",
                        equalTo(commande.getN_commande())
                ))
                .andExpect(jsonPath("$.nomcommande",
                        equalTo(updatedCommande.getNomcommande())
                ))
                .andExpect(jsonPath("$.datecommande",
                        equalTo(updatedCommande.getDatecommande())
                ))
                .andExpect(jsonPath("$.client.id",
                        equalTo(updatedCommande.getClient().getId())
                ))
                .andDo(print());


    }

















    @DisplayName("Junit test for delete commande by id ")
    @Test
    public void testDeleteCommande() throws Exception {
        String text = "Commande deleted successfully";

        when(commandeService.deleteCommande(commande.getN_commande())).thenReturn(ResponseEntity.ok(text));

        String uriUpdate = uri.concat("/{id}");


        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uriUpdate, commande.getN_commande());

        ResultActions response = mockMvc.perform(requestBuilder);

        response
                .andExpect(status().isOk())
                .andExpect(content().string(text))
                .andDo(print());




    }










}




















