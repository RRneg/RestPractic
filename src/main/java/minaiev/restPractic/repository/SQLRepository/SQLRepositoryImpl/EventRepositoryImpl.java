package minaiev.restPractic.repository.SQLRepository.SQLRepositoryImpl;

import minaiev.restPractic.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventRepositoryImpl  {

    public List<Event> getEventByUserId(Integer userId) {
        List<Event> eventsByUserId  = new ArrayList<>();
        return eventsByUserId;
    }


    public List<Event> getEventByFileId(Integer fileId) {
        List<Event> eventsByFileId = new ArrayList<>();
        return eventsByFileId;
    }
}
