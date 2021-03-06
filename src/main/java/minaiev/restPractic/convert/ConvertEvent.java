package minaiev.restPractic.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import minaiev.restPractic.dto.EventDTO;
import minaiev.restPractic.model.Event;


import java.util.List;
import java.util.stream.Collectors;

public class ConvertEvent {


    public EventDTO convertToEventDTO(Event event) {
        EventDTO eventDTO = EventDTO.builder()
                .id(event.getId())
                .eventStatus(event.getEventStatus().toString())
                .updated(event.getUpdated().toString())
                .created(event.getCreated().toString())
                .userName(event.getUser().getUserName())
                .fileName(event.getFile().getFileName())
                .filePath(event.getFile().getFilePath()).build();
        return eventDTO;
    }

    public List<EventDTO> convertToListEventDTO(List<Event> events) {
        return events.stream().map(event -> convertToEventDTO(event)).collect(Collectors.toList());
    }

    public String convertEventDTOToJSON(EventDTO eventDTO){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(eventDTO);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String convertListEventDTOToJSON(List<EventDTO> eventsDTOS){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(eventsDTOS);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
