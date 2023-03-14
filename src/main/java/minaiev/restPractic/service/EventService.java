package minaiev.restPractic.service;

import minaiev.restPractic.model.Event;
import minaiev.restPractic.model.User;
import minaiev.restPractic.repository.hibernateRepository.hibernate.HibernateEventRepositoryImpl;

import java.util.List;

public class EventService {

private final HibernateEventRepositoryImpl eventRepository = new HibernateEventRepositoryImpl();


    public Event save(Event event){
        return event;
    }

    public List<Event> getEventByUserId(Integer userId){
        return eventRepository.getEventByUserId(userId);
    }

    public void setDelStatus(Integer fileId){
        eventRepository.setDelStatus(fileId);
    }

    public Event getEventByFileId(Integer fileId){

        return eventRepository.getById(fileId);
    }




}
