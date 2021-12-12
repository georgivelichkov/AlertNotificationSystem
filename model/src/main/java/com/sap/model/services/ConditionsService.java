package com.sap.model.services;

import com.sap.model.exceptions.conditions.ConditionNotFoundException;
import com.sap.model.interfaces.IDAOService;
import com.sap.persistence.entities.Condition;
import com.sap.persistence.repositories.IConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class ConditionsService implements IDAOService<Condition> {

    private IConditionRepository conditionRepo;

    @Override
    public Condition get(String id) throws ConditionNotFoundException {
        return conditionRepo.findById(id).orElseThrow( () -> new ConditionNotFoundException("Condition not found!!!"));
    }

    @Override
    public Condition getByName( String name) throws ConditionNotFoundException {
        return conditionRepo.findByName(name).orElseThrow(() -> new ConditionNotFoundException("Condition not found " + name));
    }

    @Override
    public Page<Condition> pageOf(int page, int results) {
        return conditionRepo.findAll(PageRequest.of(page, results));
    }

    @Override
    public List<Condition> listAll() {
        List<Condition> conditions = new ArrayList<>();
        conditionRepo.findAll().forEach(conditions::add);

        return conditions;
    }

    @Override
    public Condition save(Condition condition)  {
        return conditionRepo.save(condition);
    }


    @Override
    public void delete(Condition condition) {
        conditionRepo.delete(condition);
    }

    @Override
    public void findAndDelete(String id) throws ConditionNotFoundException {
        Condition condition = get(id);
        delete(condition);
    }

    @Override
    public long count() {
        return conditionRepo.count();
    }

    @Autowired
    public void setConditionRepo(IConditionRepository conditionRepo) {
        this.conditionRepo = conditionRepo;
    }
}