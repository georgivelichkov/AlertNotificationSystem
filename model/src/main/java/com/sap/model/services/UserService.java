package com.sap.model.services;

import com.sap.model.exceptions.users.UserNotFoundException;
import com.sap.model.interfaces.IDAOService;
import com.sap.persistence.entities.User;
import com.sap.persistence.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class UserService implements IDAOService<User> {

    private IUsersRepository repo;

    @Override
    public User get(String id) throws UserNotFoundException {
        return repo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id:" +id+" not found!"));
    }

    @Override
    public User getByName( String name) throws UserNotFoundException {
        return repo.findByUsername(name).orElseThrow(() -> new UserNotFoundException("User not found " + name));
    }

    @Override
    public Page<User> pageOf(int page, int results) {
        return repo.findAll(PageRequest.of(page, results));
    }

    @Override
    public List<User> listAll() {
        return repo.findAll();
    }

    @Override
    public User save(User user) {
        return repo.saveAndFlush(user);
    }

    @Override
    public void delete(User user) {
        repo.delete(user);
    }

    @Override
    public void findAndDelete(String id) throws UserNotFoundException {
        User user = get(id);
        delete(user);
    }

    @Override
    public long count() {
        return repo.count();
    }

    @Autowired
    public void setRepo(IUsersRepository repo) {
        this.repo = repo;
    }
}
