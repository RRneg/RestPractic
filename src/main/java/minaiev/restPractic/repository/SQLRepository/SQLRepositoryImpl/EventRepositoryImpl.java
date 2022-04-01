package minaiev.restPractic.repository.SQLRepository.SQLRepositoryImpl;

import minaiev.restPractic.model.Event;
import minaiev.restPractic.util.SQLUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class EventRepositoryImpl  {

    public List<Event> getEventByUserId(Integer userId) {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String qv = String.format("FROM events E WHERE E.user_id = %d", userId);
        Query query = session.createQuery(qv);
        List eventsByUserId = query.list();
        session.close();
        return eventsByUserId;
    }


    public List<Event> getEventByFileId(Integer fileId) {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String qv = String.format("FROM events E WHERE E.file_id = %d", fileId);
        Query query = session.createQuery(qv);
        List eventsByFileId = query.list();
        session.close();
        return eventsByFileId;
    }
}
