package com.kumojin.bems.event.controllers;


import com.kumojin.bems.event.entities.EventEntity;
import com.kumojin.bems.event.models.EventModel;
import com.kumojin.bems.event.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<EventModel>> getEvents(@RequestParam("start") String start, @RequestParam("end") String end) throws ParseException {
        List<EventModel> eventModels = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date startDate = dateFormat.parse(start);
        Date endDate = dateFormat.parse(end);
        for (EventEntity eventEntity : eventService.findByStartDateOrEndDateBetween(startDate, endDate)) {
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

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public ResponseEntity<EventModel> deleteEvent(@PathVariable("id") Long id) {
        EventEntity eventEntity = eventService.findById(id).orElse(null);
        if (eventEntity != null) {
            eventService.delete(eventEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public ResponseEntity<EventModel> editEvent(@PathVariable("id") Long id, @Valid @RequestBody EventModel eventModel) {
        EventEntity eventEntity = eventService.findById(id).orElse(null);
        if (eventEntity != null) {
            eventEntity.setLabel(eventModel.getLabel());
            eventEntity.setDescription(eventModel.getDescription());
            eventEntity.setStartDate(eventModel.getStartDate());
            eventEntity.setEndDate(eventModel.getEndDate());
            eventEntity.setColor(eventModel.getColor());

            return new ResponseEntity<>(new EventModel(eventService.save(eventEntity)), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<EventModel> postEvent(@Valid @RequestBody EventModel eventModel) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setDescription(eventModel.getDescription());
        eventEntity.setLabel(eventModel.getLabel());
        eventEntity.setStartDate(eventModel.getStartDate());
        eventEntity.setEndDate(eventModel.getEndDate());
        eventEntity.setColor(eventModel.getColor());

        return new ResponseEntity<>(new EventModel(eventService.save(eventEntity)), HttpStatus.OK);
    }

}
