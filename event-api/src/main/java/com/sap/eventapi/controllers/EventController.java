package com.sap.eventapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sap.messagebroker.MessageBrokerService;
import com.sap.messagebroker.QueueType;
import com.sap.model.dto.EventDto;
import com.sap.model.dto.PagedResponseDto;
import com.sap.model.exceptions.events.EventNotFoundException;
import com.sap.model.exceptions.response.PageRequestOutOfBounds;
import com.sap.model.exceptions.response.ResultsRequestOutOfBounds;
import com.sap.model.services.EventsService;
import com.sap.model.utils.PagedResponseUtils;
import com.sap.persistence.entities.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventsService service;
    private final MessageBrokerService messageBroker;
    private Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    public EventController(EventsService service, MessageBrokerService messageBrokerService) {
        this.service = service;
        this.messageBroker = messageBrokerService;
    }


    @GetMapping(value = "/" , produces = "application/json")
    public ResponseEntity<PagedResponseDto<Event>> listEvents(@RequestParam(value = "page", required = false) Integer pageRequest,
                                                              @RequestParam(value = "results", required = false) Integer resultsRequest) throws PageRequestOutOfBounds, ResultsRequestOutOfBounds {
        logger.info("EventAPI: Requesting list of all events!!!!");

        PagedResponseUtils utils = PagedResponseUtils.newInstance(pageRequest, resultsRequest, service)
                                                     .validateInput();

        PagedResponseDto<Event> response = PagedResponseDto.builder()
                .withMetaData(utils.createMetaData())
                .withResults( service.pageOf(utils.getPageRequest(), utils.getResultsRequest()).getContent() )
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity createEvent(@Valid @RequestBody EventDto request) throws JsonProcessingException {
        logger.info("EventAPI: Incoming events!!!!");
        messageBroker.sendEventToQueue(request, QueueType.EVENT);
        return ResponseEntity.status(HttpStatus.OK).body("Event was sent to queue!!!");
    }

    @GetMapping(value = "/{event_id}", produces = "application/json")
    public ResponseEntity<Event> findEventById(@PathVariable String event_id) throws EventNotFoundException {
        logger.info("EventAPI: Requesting info about events with id: "+event_id+"!!!");

        return ResponseEntity.status(HttpStatus.OK).body(service.get(event_id));
    }

}
