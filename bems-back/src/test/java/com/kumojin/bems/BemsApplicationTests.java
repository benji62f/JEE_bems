package com.kumojin.bems;

import com.kumojin.bems.event.controllers.EventController;
import com.kumojin.bems.event.entities.EventEntity;
import com.kumojin.bems.event.services.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.json.Json;
import javax.json.JsonObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventController.class)
class BemsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    public void test_events_list_ok() throws Exception {
        mockMvc.perform(get("/api/events?start=2022-08-01T00:00:00.183+02:00&end=2023-08-31T23:59:59.183+02:00"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_events_list_400_dates_missing() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().is(400));
    }

    @Test
    public void test_events_edit_ok() throws Exception {
        // JSON body data
        JsonObject value =
                Json
                        .createObjectBuilder()
                        .add("label", "Réunion Kumojin")
                        .add("description", "Réunion client XYZ : démarrage de projet.")
                        .add("startDate", "2022-08-03T10:25:30.183+02:00")
                        .add("endDate", "2022-08-04T18:25:30.183+02:00")
                        .add("color", "deep-purple")
                        .build();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        // Entity before edition
        String startDateStringInitial = "2022-08-03T10:25:30.183+02:00";
        String endDateStringInitial = "2022-08-04T18:25:30.183+02:00";
        Date startDateInitial = dateFormat.parse(startDateStringInitial);
        Date endDateInitial = dateFormat.parse(endDateStringInitial);

        EventEntity eventEntityInitial = new EventEntity();
        eventEntityInitial.setId(1L);
        eventEntityInitial.setLabel("Réunion Kumojin");
        eventEntityInitial.setDescription("Réunion client XYZ : démarrage de projet.");
        eventEntityInitial.setStartDate(startDateInitial);
        eventEntityInitial.setEndDate(endDateInitial);
        eventEntityInitial.setColor("deep-purple");

        // Entity after edition
        String startDateStringEdited = "2022-08-03T12:00:30.000+00:00";
        String endDateStringEdited = "2022-08-04T14:00:30.000+00:00";
        Date startDateEdited = dateFormat.parse(startDateStringEdited);
        Date endDateEdited = dateFormat.parse(endDateStringEdited);

        EventEntity eventEntityEdited = new EventEntity();
        eventEntityEdited.setId(1L);
        eventEntityEdited.setLabel("Réunion Kumojin edited");
        eventEntityEdited.setDescription("Réunion client XYZ : démarrage de projet edited.");
        eventEntityEdited.setStartDate(startDateEdited);
        eventEntityEdited.setEndDate(endDateEdited);
        eventEntityEdited.setColor("deep-purple");

        // Mock service
        Mockito.when(eventService.findById(1L)).thenReturn(Optional.of(eventEntityInitial));
        Mockito.when(eventService.save(any())).thenReturn(eventEntityEdited);

        // POST request
        mockMvc
                .perform(
                        post("/api/events/1/edit")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(value.toString())
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.label").value("Réunion Kumojin edited"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Réunion client XYZ : démarrage de projet edited."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(startDateStringEdited))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(endDateStringEdited))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value("deep-purple"))
        ;
    }

    @Test
    public void test_events_edit_404() throws Exception {
        // JSON body data
        JsonObject value =
                Json
                        .createObjectBuilder()
                        .add("label", "Réunion Kumojin")
                        .add("description", "Réunion client XYZ : démarrage de projet.")
                        .add("startDate", "2022-08-03T10:25:30.183+02:00")
                        .add("endDate", "2022-08-04T18:25:30.183+02:00")
                        .add("color", "deep-purple")
                        .build();

        // POST request
        mockMvc
                .perform(
                        post("/api/events/1/edit")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(value.toString())
                )
                .andExpect(status().is(404))
        ;
    }

    @Test
    public void test_events_edit_400_label_missing() throws Exception {
        // JSON body data
        JsonObject value =
                Json
                        .createObjectBuilder()
                        .add("description", "Réunion client XYZ : démarrage de projet.")
                        .add("startDate", "2022-08-03T10:25:30.183+02:00")
                        .add("endDate", "2022-08-04T18:25:30.183+02:00")
                        .add("color", "deep-purple")
                        .build();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        // Entity before edition
        String startDateStringInitial = "2022-08-03T10:25:30.183+02:00";
        String endDateStringInitial = "2022-08-04T18:25:30.183+02:00";
        Date startDateInitial = dateFormat.parse(startDateStringInitial);
        Date endDateInitial = dateFormat.parse(endDateStringInitial);

        EventEntity eventEntityInitial = new EventEntity();
        eventEntityInitial.setId(1L);
        eventEntityInitial.setLabel("Réunion Kumojin");
        eventEntityInitial.setDescription("Réunion client XYZ : démarrage de projet.");
        eventEntityInitial.setStartDate(startDateInitial);
        eventEntityInitial.setEndDate(endDateInitial);
        eventEntityInitial.setColor("deep-purple");

        // Mock service
        Mockito.when(eventService.findById(1L)).thenReturn(Optional.of(eventEntityInitial));

        // POST request
        mockMvc
                .perform(
                        post("/api/events/1/edit")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(value.toString())
                )
                .andExpect(status().is(400))
        ;
    }

    @Test
    public void test_events_edit_400_color_missing() throws Exception {
        // JSON body data
        JsonObject value =
                Json
                        .createObjectBuilder()
                        .add("label", "Réunion Kumojin")
                        .add("description", "Réunion client XYZ : démarrage de projet.")
                        .add("startDate", "2022-08-03T10:25:30.183+02:00")
                        .add("endDate", "2022-08-04T18:25:30.183+02:00")
                        .build();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        // Entity before edition
        String startDateStringInitial = "2022-08-03T10:25:30.183+02:00";
        String endDateStringInitial = "2022-08-04T18:25:30.183+02:00";
        Date startDateInitial = dateFormat.parse(startDateStringInitial);
        Date endDateInitial = dateFormat.parse(endDateStringInitial);

        EventEntity eventEntityInitial = new EventEntity();
        eventEntityInitial.setId(1L);
        eventEntityInitial.setLabel("Réunion Kumojin");
        eventEntityInitial.setDescription("Réunion client XYZ : démarrage de projet.");
        eventEntityInitial.setStartDate(startDateInitial);
        eventEntityInitial.setEndDate(endDateInitial);
        eventEntityInitial.setColor("deep-purple");

        // Mock service
        Mockito.when(eventService.findById(1L)).thenReturn(Optional.of(eventEntityInitial));

        // POST request
        mockMvc
                .perform(
                        post("/api/events/1/edit")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(value.toString())
                )
                .andExpect(status().is(400))
        ;
    }

    @Test
    public void test_events_post_ok() throws Exception {
        // JSON body data
        JsonObject value =
                Json
                        .createObjectBuilder()
                        .add("label", "Réunion Kumojin")
                        .add("description", "Réunion client XYZ : démarrage de projet.")
                        .add("startDate", "2022-08-03T10:25:30.183+02:00") // UTC+02:00
                        .add("endDate", "2022-08-04T18:25:30.183+02:00")
                        .add("color", "deep-purple")
                        .build();

        // API response
        String startDateString = "2022-08-03T08:25:30.183+00:00"; // UTC+00:00
        String endDateString = "2022-08-04T16:25:30.183+00:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date startDate = dateFormat.parse(startDateString);
        Date endDate = dateFormat.parse(endDateString);

        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(1L);
        eventEntity.setLabel("Réunion Kumojin");
        eventEntity.setDescription("Réunion client XYZ : démarrage de projet.");
        eventEntity.setStartDate(startDate);
        eventEntity.setEndDate(endDate);
        eventEntity.setColor("deep-purple");

        // Mock service
        Mockito.when(eventService.save(any())).thenReturn(eventEntity);

        // POST request
        mockMvc
                .perform(
                        post("/api/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(value.toString())
                )
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.label").value("Réunion Kumojin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Réunion client XYZ : démarrage de projet."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(startDateString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(endDateString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value("deep-purple"))
        ;
    }

    @Test
    public void test_events_post_400_label_missing() throws Exception {
        // JSON body data
        JsonObject value =
                Json
                        .createObjectBuilder()
                        .add("description", "Réunion client XYZ : démarrage de projet.")
                        .add("startDate", "2022-08-03T10:25:30.183+02:00")
                        .add("endDate", "2022-08-04T18:25:30.183+02:00")
                        .add("color", "deep-purple")
                        .build();

        // POST request
        mockMvc
                .perform(
                        post("/api/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(value.toString())
                )
                .andExpect(status().is(400))
        ;
    }

    @Test
    public void test_events_post_400_color_missing() throws Exception {
        // JSON body data
        JsonObject value =
                Json
                        .createObjectBuilder()
                        .add("label", "Réunion Kumojin")
                        .add("description", "Réunion client XYZ : démarrage de projet.")
                        .add("startDate", "2022-08-03T10:25:30.183+02:00")
                        .add("endDate", "2022-08-04T18:25:30.183+02:00")
                        .build();

        // POST request
        mockMvc
                .perform(
                        post("/api/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(value.toString())
                )
                .andExpect(status().is(400))
        ;
    }

    @Test
    public void test_events_get_ok() throws Exception {
        String startDateString = "2022-08-03T08:25:30.183+00:00";
        String endDateString = "2022-08-04T16:25:30.183+00:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date startDate = dateFormat.parse(startDateString);
        Date endDate = dateFormat.parse(endDateString);

        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(1L);
        eventEntity.setLabel("Réunion Kumojin");
        eventEntity.setDescription("Réunion client XYZ : démarrage de projet.");
        eventEntity.setStartDate(startDate);
        eventEntity.setEndDate(endDate);
        eventEntity.setColor("deep-purple");

        Mockito.when(eventService.findById(1L)).thenReturn(Optional.of(eventEntity));

        mockMvc.perform(get("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.label").value("Réunion Kumojin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Réunion client XYZ : démarrage de projet."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(startDateString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(endDateString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value("deep-purple"))
        ;
    }

    @Test
    public void test_events_get_404() throws Exception {
        mockMvc.perform(get("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void test_events_delete_ok() throws Exception {
        String startDateString = "2022-08-03T08:25:30.183+00:00";
        String endDateString = "2022-08-04T16:25:30.183+00:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date startDate = dateFormat.parse(startDateString);
        Date endDate = dateFormat.parse(endDateString);

        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(1L);
        eventEntity.setLabel("Réunion Kumojin");
        eventEntity.setDescription("Réunion client XYZ : démarrage de projet.");
        eventEntity.setStartDate(startDate);
        eventEntity.setEndDate(endDate);
        eventEntity.setColor("deep-purple");

        Mockito.when(eventService.findById(1L)).thenReturn(Optional.of(eventEntity));

        mockMvc.perform(post("/api/events/1/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test_events_delete_404() throws Exception {
        mockMvc.perform(post("/api/events/1/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

}
