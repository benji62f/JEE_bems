package com.kumojin.bems.event.services;

import com.kumojin.bems.event.entities.EventEntity;
import com.kumojin.bems.event.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Optional<EventEntity> findById(Long id) {
        return eventRepository.findById(id);
    }

    public List<EventEntity> findByStartDateOrEndDateBetween(Date rangeStart, Date rangeEnd) {
        return eventRepository.findByStartDateBetweenOrEndDateBetween(rangeStart, rangeEnd, rangeStart, rangeEnd);
    }

    public EventEntity save(EventEntity eventEntity) {
        return this.eventRepository.save(eventEntity);
    }

    public void delete(EventEntity eventEntity) {
        this.eventRepository.delete(eventEntity);
    }
}