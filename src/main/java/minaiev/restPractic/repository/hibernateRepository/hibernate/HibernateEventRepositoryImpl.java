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

    public List<Event> getEventByUserId(Integer userId) {
        try (Session session = SQLUtil.getSession()){
        Query query = session.createQuery("FROM Event E WHERE E.user_id = :userId")
                .setParameter("userId", userId);
        List<Event> events = query.list();
        session.close();
        return events;
        }
        catch (SessionException e){
        return null;
        }
    }

    public void setDelStatus(Integer fileId) throws SessionException{
            Session session = SQLUtil.getSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE Event E SET event_status 'DELETED' WHERE E.file_id = :fileId");
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
    public Event getById(Integer fileId) {
        try (Session session = SQLUtil.getSession()) {
            return (Event) session.createQuery("FROM Event E WHERE E.file_id = :fileId").
                    setParameter("fileId", fileId).getSingleResult();
        }
        catch (SessionException e) {
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
        return null;
    }

}
