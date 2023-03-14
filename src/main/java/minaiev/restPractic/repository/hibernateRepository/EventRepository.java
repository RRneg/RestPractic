package minaiev.restPractic.repository.hibernateRepository;

import minaiev.restPractic.model.Event;

import java.util.List;

public interface EventRepository extends GenericRepository <Event, Integer>{

    List<Event> getEventByUserId(Integer userId);

    void setDelStatus(Integer idFile);
}
