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

    public static Integer user_Id;

    public static Integer getUser_Id() {
        return user_Id;
    }

    public static void setUser_Id(Integer user_Id) {
        HibernateFileRepositoryImpl.user_Id = user_Id;
    }

    @Override
    public File getById(Integer id) {
        try (Session session = SQLUtil.getSession()) {
            File file = session.get(File.class, id);
            session.close();
            return file;
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
            session.close();
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
            String st = String.format("FROM File as file" +
                    "inner join Event as event " +
                    "inner join User as user " +
                    "where user.id = %d", HibernateFileRepositoryImpl.getUser_Id());
            List<File> files = session.createQuery(st).list();
            session.close();
            return files;
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
            session.close();
            return file;
        }
        catch (SessionException e){
            return null;
        }
    }


}
