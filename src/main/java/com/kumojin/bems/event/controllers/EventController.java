package com.kumojin.bems.event.controllers;


import com.kumojin.bems.event.entities.EventEntity;
import com.kumojin.bems.event.models.EventModel;
import com.kumojin.bems.event.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<EventModel>> getEvents() {
        List<EventModel> eventModels = new ArrayList<>();
        for (EventEntity eventEntity : eventService.findAll()) {
            eventModels.add(new EventModel(eventEntity));
        }
        return new ResponseEntity<>(eventModels, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<EventModel> getEvent(@PathVariable("id") Long id) {
        EventEntity eventEntity = eventService.findById(id).orElse(null);
        if (eventEntity != null) {
            return new ResponseEntity<>(new EventModel(eventEntity), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {"application/json"})
    public void postEvent(@Valid @RequestBody EventModel eventModel) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setDescription(eventModel.getDescription());
        eventEntity.setLabel(eventModel.getLabel());
        eventEntity.setStartDate(eventModel.getStartDate());
        eventEntity.setEndDate(eventModel.getEndDate());
        eventService.create(eventEntity);
    }

}
