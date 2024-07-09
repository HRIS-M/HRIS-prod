package com.hris.HRIS.service;

import com.hris.HRIS.model.EventModel;
import com.hris.HRIS.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<EventModel> getAllEvents() {
        return eventRepository.findAll();
    }

    public EventModel saveEvent(EventModel eventModel) {
        return eventRepository.save(eventModel);
    }

    public Optional<EventModel> getEventById(String id) {
        return eventRepository.findById(id);
    }

    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }

    public EventModel updateEvent(String id, EventModel eventModel) {
        Optional<EventModel> optionalEventModel = eventRepository.findById(id);

        if (optionalEventModel.isPresent()) {
            EventModel event = optionalEventModel.get();
            event.setMeta(eventModel.getMeta());
            event.setTitle(eventModel.getTitle());
            event.setStart(eventModel.getStart());
            event.setEnd(eventModel.getEnd());
            event.setAllDay(eventModel.isAllDay());
            event.setDraggable(eventModel.isDraggable());
            event.setColor(eventModel.getColor());
            event.setResizable(eventModel.getResizable());
            event.setActions(eventModel.getActions());

            return eventRepository.save(event);
        }
        return null;
    }
}
