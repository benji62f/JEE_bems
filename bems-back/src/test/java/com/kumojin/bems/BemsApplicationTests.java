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
    public void test_events_post_ok() throws Exception {
        // Input data UTC+02
        JsonObject value =
                Json
                        .createObjectBuilder()
                        .add("label", "Réunion Kumojin")
                        .add("description", "Réunion client XYZ : démarrage de projet.")
                        .add("startDate", "2022-08-03T10:25:30.183+02:00")
                        .add("endDate", "2022-08-04T18:25:30.183+02:00")
                        .add("color", "deep-purple")
                        .build();

        // Output data UTC+00
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
        // Input data UTC+02
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
        // Input data UTC+02
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
