package com.sap.model.services;

import com.sap.model.exceptions.events.EventNotFoundException;
import com.sap.model.interfaces.IDAOService;
import com.sap.persistence.entities.Event;
import com.sap.persistence.repositories.IEventRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class EventsService implements IDAOService<Event> {

    private IEventRepository repo;

    @Override
    public Page<Event> pageOf(int page, int results) {
        return repo.findAll(PageRequest.of(page, results));
    }

    @Override
    public List<Event> listAll() {
        return repo.findAll();
    }

    @Override
    public Event get(String id) throws EventNotFoundException {
        return repo.findById(new ObjectId(id)).orElseThrow(() -> new EventNotFoundException("Event not found!!!"));
    }

    @Override
    public Event getByName( String name) throws EventNotFoundException {
        return repo.findByType(name).orElseThrow(() -> new EventNotFoundException("Event not found " + name));
    }

    @Override
    public Event save(Event event) {
        return repo.save(event);
    }


    @Override
    public void delete(Event event) {
        repo.delete(event);
    }

    @Override
    public void findAndDelete(String id) throws EventNotFoundException {
        Event event = get(id);
        delete(event);
    }

    @Override
    public long count() {
        return repo.count();
    }

    @Autowired
    public void setRepo(IEventRepository repo) {
        this.repo = repo;
    }
}
