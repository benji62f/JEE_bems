package com.kumojin.bems.event.repositories;

import com.kumojin.bems.event.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findByStartDateBetweenOrEndDateBetween(Date startDateRangeStart, Date startDateRangeEnd, Date endDateRangeStart, Date endDateRangeEnd);
}