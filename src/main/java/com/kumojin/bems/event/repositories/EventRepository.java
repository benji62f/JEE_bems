package com.kumojin.bems.event.repositories;

import com.kumojin.bems.event.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

}