package com.hris.HRIS.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Getter
@Setter
@ToString

@Document(collection = "holiday")
public class HolidayModel {
    @Id
    private String id;

    @Field("meta")
    private EventModel.Meta meta;

    private String title;
    private String start;
    private boolean allDay;
    private boolean draggable;

    @Field("color")
    private EventModel.EventColor color;

    @Field("resizable")
    private EventModel.EventResizable resizable;

    @Field("actions")
    private List<EventModel.EventAction> actions;

    // Embedded classes for complex types
    @Getter
    @Setter
    @ToString
    public static class Meta {
        private String userId;
        private String organizationId;
    }

    @Getter
    @Setter
    @ToString
    public static class EventColor {
        private String primary;
        private String secondary;
        private String secondaryText; // New field
    }

    @Getter
    @Setter
    @ToString
    public static class EventResizable {
        private boolean beforeStart;
        private boolean afterEnd;
    }

    @Getter
    @Setter
    @ToString
    public static class EventAction {
        private String label;
        private String a11yLabel;
        private String onClick;
    }
}
