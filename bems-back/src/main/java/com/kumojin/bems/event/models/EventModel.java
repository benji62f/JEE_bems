package com.kumojin.bems.event.models;

import com.kumojin.bems.event.entities.EventEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class EventModel {

    private final Long id;

    @NotBlank(message = "label is mandatory")
    private final String label;

    private final String description;

    @NotNull(message = "startDate is mandatory")
    private final Date startDate;

    @NotNull(message = "endDate is mandatory")
    private final Date endDate;

    public EventModel(Long id, String label, String description, Date startDate, Date endDate) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EventModel(EventEntity eventEntity) {
        this.id = eventEntity.getId();
        this.label = eventEntity.getLabel();
        this.description = eventEntity.getDescription();
        this.startDate = eventEntity.getStartDate();
        this.endDate = eventEntity.getEndDate();
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
