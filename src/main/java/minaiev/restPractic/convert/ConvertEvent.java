package minaiev.restPractic.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import minaiev.restPractic.dto.EventDTO;
import minaiev.restPractic.model.Event;
import minaiev.restPractic.model.User;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertEvent {


    private EventDTO convertToEventDTO(Event event) {
        EventDTO eventDTO = EventDTO.builder()
                .id(event.getId())
                .eventStatus(event.getEventStatus().toString())
                .updated(event.getUpdated().toString())
                .created(event.getCreated().toString())
                .userName(event.getUser().getUserName())
                .fileName(event.getFile().getFileName())
                .build();
        return eventDTO;
    }

    private List<EventDTO> convertToListEventDTO(List<Event> events) {
        return events.stream().map(event -> convertToEventDTO(event)).collect(Collectors.toList());
    }

    public String convertEventToJSON(Event event){
        EventDTO eventDTO = convertToEventDTO(event);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(eventDTO);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String convertListEventToJSON(List<Event> events){
        List<EventDTO> eventsDTOS = convertToListEventDTO(events);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(eventsDTOS);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Event convertJSONtoEvent(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Event event = mapper.readValue(json, Event.class);
            return event;
        }
        catch (IOException e){
            return null;
        }
    }
}
