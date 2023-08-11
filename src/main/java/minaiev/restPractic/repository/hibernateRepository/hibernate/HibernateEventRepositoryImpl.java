package minaiev.restPractic.repository.hibernateRepository.hibernate;

import minaiev.restPractic.model.Event;
import minaiev.restPractic.repository.hibernateRepository.EventRepository;
import minaiev.restPractic.util.SQLUtil;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateEventRepositoryImpl implements EventRepository {


    public void setDelStatus(Integer fileId) throws SessionException{
            Session session = SQLUtil.getSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE Event E SET event_status = 'DELETED' WHERE E.id = :fileId");
            query.setParameter("fileId", fileId);
            query.executeUpdate();
            transaction.commit();
            session.close();
    }

    public Event save(Event event) {
        try (Session session = SQLUtil.getSession()){
        Transaction transaction = session.beginTransaction();
        event.setId((Integer) session.save(event));
        transaction.commit();
        session.close();
        return event;
    }
        catch (SessionException e){
            return null;
        }
    }


    @Override
    public Event getById (Integer id){
        try (Session session = SQLUtil.getSession()){
            Event event = session.get(Event.class, id);
            return event;
        }
        catch (SessionException e){
            return null;
        }

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
        try(Session session = SQLUtil.getSession()) {
            List<Event> events = session.createQuery("FROM Event").list();
            return events;
        }
        catch (SessionException e) {
            return null;
        }
    }

}
