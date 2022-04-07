package minaiev.restPractic.repository.SQLRepository.SQLRepositoryImpl;

import minaiev.restPractic.model.User;
import minaiev.restPractic.repository.SQLRepository.UserRepository;
import minaiev.restPractic.util.SQLUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User getById(Integer id) {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public User update(User user) {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public void deleteById(Integer id) {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAll() {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        List users = session.createQuery("FROM users").list();
        session.close();
        return users;
    }

    @Override
    public User save(User user) {
        Session session = SQLUtil.getSession();
        Transaction transaction = session.beginTransaction();
        user.setId((Integer) session.save(user));
        transaction.commit();
        session.close();
        return user;
    }
}
