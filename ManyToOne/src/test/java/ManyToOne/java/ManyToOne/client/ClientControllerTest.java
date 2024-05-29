package ManyToOne.java.ManyToOne.client;






import ManyToOne.java.ManyToOne.controller.ClientController;
import ManyToOne.java.ManyToOne.model.Client;
import ManyToOne.java.ManyToOne.service.ClientService;
import org.junit.jupiter.api.BeforeEach;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ManyToOne.java.ManyToOne.exceptions.ErrorMessageService;

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
 * @author Diallo iliassou
 * @version 0.0.1
 * @since 0.0.1
 */

@Log4j2
@WebMvcTest(value = ClientController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})


public class ClientControllerTest {

    /**
     * since @ControllerAdvice scans all controllers, so they all have a dependency to board controller
     * and here board controller is ErrorMessageController
     * and it has a dependency to ErrorMessageService so they will all have a dependency to ErrorMessageService
     */




    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;


    @MockBean
    private ErrorMessageService errorMessageService;

    private Client client;

    @Autowired
    private ObjectMapper objectMapper;
    private final String uri = "/Client";

    private Client clientReturn;

    private String clientInJSON;

    private Client client5;

    List<Client> clientList;

    private Client newClient;




    @BeforeEach
    void setup() {
        client = new Client();
        client.setId(7);
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

        newClient = new Client();
        newClient.setNom("dialoooooo");
        newClient.setPrenom("Aissatouuuuuuuuuuuu");
        newClient.setQuartier("Dijonnnnnnnnnn");



        try {
            clientInJSON = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(client);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }






    }

    @DisplayName("Junit test for create client and return client")
    @Test
    public void testCreateClient() throws Exception {

        when(clientService.creatClient(any(Client.class))).thenReturn(client);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientInJSON);

        ResultActions response = mockMvc.perform(requestBuilder);

        response

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",
                        equalTo(client.getId())
                ))
                .andExpect(jsonPath("$.nom",
                        equalTo(client.getNom())
                ))
                .andExpect(jsonPath("$.prenom",
                        equalTo(client.getPrenom())
                ))

                .andExpect(jsonPath("$.quartier",
                        equalTo(client.getQuartier())
                ))

                .andDo(print());

    }


    @DisplayName("Junit test for get all client and return client")
    @Test
    public void testGetAllClient() throws Exception {

        when(clientService.getAllClient()).thenReturn(ResponseEntity.ok(clientList));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri);

        ResultActions response = mockMvc.perform(requestBuilder);
       response
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].id",
                       equalTo((clientList.get(0).getId()))
               ))
               .andExpect(jsonPath("$.size()").value(clientList.size()))

                .andExpect(jsonPath("$[1].nom",
                        equalTo(clientList.get(1).getNom())
                ))
                .andExpect(jsonPath("$[1].prenom",
                        equalTo(clientList.get(1).getPrenom())
                ))
                .andExpect(jsonPath("$[0].quartier",
                        equalTo(clientList.get(0).getQuartier())
                ))
                .andDo(print());




    }
    @DisplayName("Junit test for get  client by id and return client")
    @Test
    public void testGetClient() throws Exception {

        when(clientService.getClient(eq(client.getId()))).thenReturn(ResponseEntity.ok(client));


        String uriUpdate = uri.concat("/{id}");


        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uriUpdate, client.getId());

        ResultActions response = mockMvc.perform(requestBuilder);


        response
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id",
                        equalTo((client.getId()))
                ))
                .andExpect(jsonPath("$.nom",
                        equalTo(client.getNom())
                ))
                .andExpect(jsonPath("$.prenom",
                        equalTo(client.getPrenom())
                ))
                .andExpect(jsonPath("$.quartier",
                        equalTo(client.getQuartier())
                ))
                .andDo(print());

        }
    @DisplayName("Junit test for put  client by id and return client")
    @Test
    public void testPutClient() throws Exception {
        client.setNom(newClient.getNom());
        client.setPrenom(newClient.getPrenom());
        client.setPrenom(newClient.getQuartier());

        when(clientService.putClient(client.getId(), newClient)).thenReturn(ResponseEntity.ok(client));

        String uriUpdate = uri + "/" + client.getId();


        String clientJson = objectMapper.writeValueAsString(newClient);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uriUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson);

        ResultActions response = mockMvc.perform(requestBuilder);

        response
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",
                        equalTo(client.getId())))
                .andExpect(jsonPath("$.nom",
                        equalTo(client.getNom())))
                .andExpect(jsonPath("$.prenom",
                        equalTo(client.getPrenom())))
                .andExpect(jsonPath("$.quartier",
                        equalTo(client.getQuartier())))
                .andDo(print());



    }
    @DisplayName("Junit test for put2  client by id and return client")
    @Test
    public void testPut2Client() throws Exception {

        BeanUtils.copyProperties(newClient, client, "id");

        when(clientService.putClient(client.getId(), newClient)).thenReturn(ResponseEntity.ok(client));

        String uriUpdate = uri + "/" + client.getId();


        String clientJson = objectMapper.writeValueAsString(newClient);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uriUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson);

        ResultActions response = mockMvc.perform(requestBuilder);

        response
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",
                        equalTo(client.getId())))
                .andExpect(jsonPath("$.nom",
                        equalTo(client.getNom())))
                .andExpect(jsonPath("$.prenom",
                        equalTo(client.getPrenom())))
                .andExpect(jsonPath("$.quartier",
                        equalTo(client.getQuartier())))
                .andDo(print());



    }




    @DisplayName("Junit test for delete client by id ")
    @Test
    public void testDeleteClient() throws Exception {
        String text = "Client deleted successfully";

        when(clientService.deleteClient(client.getId())).thenReturn(ResponseEntity.ok(text));

        String uriUpdate = uri.concat("/{id}");


        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uriUpdate, client.getId());

        ResultActions response = mockMvc.perform(requestBuilder);
        response
                .andExpect(status().isOk())
                .andExpect(content().string(text))
                .andDo(print());

    }

}











































