package com.sap.model.services;

import com.sap.model.exceptions.actions.ActionNotFoundException;
import com.sap.model.interfaces.IDAOService;
import com.sap.persistence.entities.Action;
import com.sap.persistence.repositories.IActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActionsService implements IDAOService<Action> {

    private IActionRepository actionRepo;

    @Override
    public Page<Action> pageOf(int page, int results) {
        return actionRepo.findAll(PageRequest.of(page, results));
    }

    @Override
    public List<Action> listAll() {
        List<Action> actions = new ArrayList<>();
        actionRepo.findAll().forEach(actions::add);

        return actions;
    }

    @Override
    public Action get(String id) throws ActionNotFoundException {
        return actionRepo.findById(UUID.fromString(id)).orElseThrow(() -> new ActionNotFoundException("Action not found!!! ID:" +id));
    }

    @Override
    public Action getByName( String name) throws ActionNotFoundException {
        return actionRepo.findByName(name).orElseThrow(() -> new ActionNotFoundException("Action not found " + name));
    }

    @Override
    public Action save(Action action) {
        return actionRepo.save(action);
    }

    @Override
    public void delete(Action action) {
        actionRepo.delete(action);
    }

    @Override
    public void findAndDelete(String id) throws ActionNotFoundException {
        Action action = get(id);
        delete(action);
    }

    @Override
    public long count() {
        return actionRepo.count();
    }

    @Autowired
    public void setActionRepo(IActionRepository actionRepo) {
        this.actionRepo = actionRepo;
    }
}
