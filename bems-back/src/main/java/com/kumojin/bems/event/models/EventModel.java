package com.kumojin.bems.event.models;

import com.kumojin.bems.event.entities.EventEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class EventModel {

    private final Long id;

    @NotBlank(message = "label is mandatory")
    @Size(max = 32, message = "{validation.name.size.too_long}")
    private final String label;

    @Size(max = 150, message = "{validation.name.size.too_long}")
    private final String description;

    @NotNull(message = "startDate is mandatory")
    private final Date startDate;

    @NotNull(message = "endDate is mandatory")
    private final Date endDate;

    @NotBlank(message = "color is mandatory")
    private final String color;

    public EventModel(Long id, String label, String description, Date startDate, Date endDate, String color) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.color = color;
    }

    public EventModel(EventEntity eventEntity) {
        this.id = eventEntity.getId();
        this.label = eventEntity.getLabel();
        this.description = eventEntity.getDescription();
        this.startDate = eventEntity.getStartDate();
        this.endDate = eventEntity.getEndDate();
        this.color = eventEntity.getColor();
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

    public String getColor() {
        return color;
    }
}
