package minaiev.restPractic.service;

import minaiev.restPractic.model.User;
import minaiev.restPractic.repository.hibernateRepository.hibernate.HibernateUserRepositoryImpl;

import java.util.List;

public class UserService {
    private final HibernateUserRepositoryImpl userRepository = new HibernateUserRepositoryImpl();


    public User save(User user) {
        return userRepository.save(user);
    }


    public User getById(Integer id) {
        return userRepository.getById(id);
    }


    public User update(User user) {

        return userRepository.update(user);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }
}
