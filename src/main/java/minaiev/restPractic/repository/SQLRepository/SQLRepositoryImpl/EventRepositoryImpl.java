package minaiev.restPractic.repository.SQLRepository.SQLRepositoryImpl;

import minaiev.restPractic.model.Event;
import minaiev.restPractic.model.EventStatus;
import minaiev.restPractic.model.File;
import minaiev.restPractic.util.SQLUtil;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventRepositoryImpl {

    public List<Event> getEventByUserId(Integer userId) {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String qv = String.format("FROM events E WHERE E.user_id = %d ", userId);
        Query query = session.createQuery(qv);
        List eventsByUserId = query.list();
        session.close();
        return eventsByUserId;
    }

    public Event getEventByFileId(Integer fileId) {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String qv = String.format("FROM events E WHERE E.file_id = %d ", fileId);
        Query query = session.createQuery(qv);
        Event event = (Event) query.getSingleResult();
        return event;

    }

    public void setDelStatus(Integer idFile) {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String qv = String.format("FROM events E WHERE E.file_id = %d", idFile);
        Event event = (Event) session.createQuery(qv);
        event.setEventStatus(EventStatus.DELETED);
        session.update(event);
        transaction.commit();
        session.close();
    }

    public void save(File file, Integer userId) {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Event event = new Event();
        event.setEventStatus(EventStatus.ACTIVE);
        event.setChangeMade(new Date());
        event.setUserId(userId);
        event.setFileId(file.getId());
        session.save(event);
        transaction.commit();
        session.close();

    }

}
