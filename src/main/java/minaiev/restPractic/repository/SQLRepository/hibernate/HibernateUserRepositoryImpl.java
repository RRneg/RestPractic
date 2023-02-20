package minaiev.restPractic.repository.SQLRepository.hibernate;

import minaiev.restPractic.model.User;
import minaiev.restPractic.repository.SQLRepository.UserRepository;
import minaiev.restPractic.util.SQLUtil;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateUserRepositoryImpl implements UserRepository {
    @Override
    public User getById(Integer id) {
        try (Session session = SQLUtil.getSession()) {
            User user = session.get(User.class, id);
            session.close();
            return user;
            }
        catch (SessionException e){
            return null;
        }
    }

    @Override
    public User update(User user) {
        try (Session session = SQLUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE User U SET user_name = :userName WHERE U.id = :id");
            query.setParameter("userName", user.getUserName());
            query.setParameter("id", user.getId());
            query.executeUpdate();
            transaction.commit();
            session.close();
            return user;
        }
        catch (SessionException e){
            return null;
        }
    }

    @Override
    public void deleteById(Integer id) throws SessionException{
                try (Session session = SQLUtil.getSession();) {
                    Transaction transaction = session.beginTransaction();
                    User user = session.get(User.class, id);
                    session.delete(user);
                    transaction.commit();
                    session.close();
                }
                catch (SessionException e){
                    //doSomething
                }
       }

    @Override
    public List<User> getAll() {
        try (Session session = SQLUtil.getSession();){
            List<User> users = session.createQuery("FROM User").list();
            session.close();
            return users;
        }
        catch (SessionException e){
        return null;
        }
    }

    @Override
    public User save(User user) {
        try (Session session = SQLUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            user.setId((Integer) session.save(user));
            transaction.commit();
            session.close();
            return user;
        }
        catch (SessionException e){
            return null;
        }
    }
}
