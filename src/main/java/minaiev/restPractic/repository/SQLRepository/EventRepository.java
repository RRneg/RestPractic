package minaiev.restPractic.repository.SQLRepository;

import minaiev.restPractic.model.Event;
import minaiev.restPractic.repository.SQLRepository.GenericRepository;

import java.util.List;

public interface EventRepository extends GenericRepository <Event, Integer>{

    List<Event> getEventByUserId(Integer userId);

    void setDelStatus(Integer idFile);
}
