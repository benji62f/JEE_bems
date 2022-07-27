package com.kumojin.bems.event.services;

import com.kumojin.bems.event.entities.EventEntity;
import com.kumojin.bems.event.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Optional<EventEntity> findById(Long id) {
        return eventRepository.findById(id);
    }

    public List<EventEntity> findAll() {
        return eventRepository.findAll();
    }

    public void create(EventEntity eventEntity) {
        this.eventRepository.save(eventEntity);
    }
}