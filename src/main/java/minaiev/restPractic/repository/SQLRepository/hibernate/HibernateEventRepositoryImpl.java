package minaiev.restPractic.repository.SQLRepository.hibernate;

import minaiev.restPractic.model.Event;
import minaiev.restPractic.model.EventStatus;
import minaiev.restPractic.model.File;
import minaiev.restPractic.model.User;
import minaiev.restPractic.repository.SQLRepository.EventRepository;
import minaiev.restPractic.util.SQLUtil;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class HibernateEventRepositoryImpl implements EventRepository {

    public List<Event> getEventByUserId(Integer userId) {
        try (Session session = SQLUtil.getSession()){
        Query query = session.createQuery("FROM Event E WHERE E.user_id = :userId");
        return query.list();
        }
        catch (SessionException e){
        return null;
        }
    }

    public void setDelStatus(Integer idFile) {
        try(Session session = SQLUtil.getSession()){
        Transaction transaction = session.beginTransaction();
        Event event = (Event) session.createQuery("FROM Event E WHERE E.file_id = :idFile");
        event.setEventStatus(EventStatus.DELETED);
        session.update(event);
        transaction.commit();
        }
        catch (SessionException e){
        }
    }

    public Event save(Event event) {
        try (Session session = SQLUtil.getSession()){
        Transaction transaction = session.beginTransaction();
        event.setId((Integer) session.save(event));
        transaction.commit();
        return event;
    }
        catch (SessionException e){
            return null;
        }
    }


    @Override
    public Event getById(Integer integer) {
        return null;
    }

    @Override
    public Event update(Event event) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public List<Event> getAll() {
        return null;
    }

}
