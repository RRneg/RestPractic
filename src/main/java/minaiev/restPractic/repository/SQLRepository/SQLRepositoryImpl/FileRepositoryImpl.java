package minaiev.restPractic.repository.SQLRepository.SQLRepositoryImpl;

import minaiev.restPractic.model.File;
import minaiev.restPractic.repository.SQLRepository.FileRepository;
import minaiev.restPractic.util.SQLUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FileRepositoryImpl implements FileRepository {
    @Override
    public File getById(Integer id) {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        File file = session.get(File.class, id);
        transaction.commit();
        session.close();
        return file;
    }

    @Override
    public File update(File file) {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(file);
        transaction.commit();
        session.close();
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(File.class, id));
        transaction.commit();
        session.close();
    }

    @Override
    public List<File> getAll() {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        List files = session.createQuery("FROM files").list();
        session.close();
        return null;
    }

    @Override
    public File save(File file) {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        file.setId((Integer)session.save(file));
        transaction.commit();
        session.close();
        return null;
    }
}
