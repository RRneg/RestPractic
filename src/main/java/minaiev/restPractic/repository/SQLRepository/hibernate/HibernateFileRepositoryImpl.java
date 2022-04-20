package minaiev.restPractic.repository.SQLRepository.hibernate;

import minaiev.restPractic.model.File;
import minaiev.restPractic.repository.SQLRepository.EventRepository;
import minaiev.restPractic.repository.SQLRepository.FileRepository;
import minaiev.restPractic.repository.SQLRepository.GenericRepository;
import minaiev.restPractic.util.SQLUtil;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateFileRepositoryImpl implements FileRepository {
    @Override
    public File getById(Integer id) {
        try (Session session = SQLUtil.getSession()) {
            return session.get(File.class, id);
        }
        catch (SessionException e){
            return null;
        }
    }

    @Override
    public File update(File file) {
        try (Session session = SQLUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(file);
            transaction.commit();
            return file;
        }
        catch (SessionException e){
            return null;
        }
    }

    @Override
    public void deleteById(Integer id) throws SessionException{
        EventRepository eventRepository = new HibernateEventRepositoryImpl();
        eventRepository.setDelStatus(id);
    }

    @Override
    public List<File> getAll() {
        try (Session session = SQLUtil.getSession()) {
            return session.createQuery("FROM File").list();
        }
        catch (SessionException e) {
            return null;
        }
    }

    @Override
    public File save(File file) {
        try (Session session = SQLUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            file.setId((Integer) session.save(file));
            transaction.commit();
            return file;
        }
        catch (SessionException e){
            return null;
        }
    }
}
