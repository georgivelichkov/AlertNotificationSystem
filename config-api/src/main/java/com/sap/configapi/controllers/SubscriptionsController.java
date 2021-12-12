package com.sap.configapi.controllers;

import com.sap.model.DtoConverter;
import com.sap.model.dto.PagedResponseDto;
import com.sap.model.dto.SubscriptionDto;
import com.sap.model.exceptions.response.PageRequestOutOfBounds;
import com.sap.model.exceptions.response.ResultsRequestOutOfBounds;
import com.sap.model.exceptions.subscriptions.SubscriptionNotFoundException;
import com.sap.model.services.SubscriptionsService;
import com.sap.model.utils.PagedResponseUtils;
import com.sap.persistence.entities.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionsController {

    private static final Logger LOG = LoggerFactory.getLogger(SubscriptionsController.class);
    private final SubscriptionsService service;

    @Autowired
    public SubscriptionsController(SubscriptionsService service) {
        this.service = service;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PagedResponseDto<Subscription>> listConditions(@RequestParam(value = "page",required = false) Integer pageRequest,
                                                                         @RequestParam(value = "results", required = false) Integer resultsRequest ) throws PageRequestOutOfBounds, ResultsRequestOutOfBounds {
        LOG.info("SubscriptionsController: Requesting a list of all subscriptions!!!");

        PagedResponseUtils utils = PagedResponseUtils.newInstance(pageRequest, resultsRequest, service)
                                                     .validateInput();

        PagedResponseDto<Subscription> response = PagedResponseDto.builder()
                .withMetaData(utils.createMetaData())
                .withResults( service.pageOf(utils.getPageRequest(), utils.getResultsRequest()).getContent() )
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Subscription> createSubscription(@Valid @RequestBody SubscriptionDto request) throws SubscriptionNotFoundException {
        LOG.info("SubscriptionsController: Request for creating subscription!!!");

        Subscription subscription = DtoConverter.toSubscription(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(subscription));
    }

    @DeleteMapping(value = "/{subscription_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteSubscription(@PathVariable String subscription_id) throws SubscriptionNotFoundException {
        String message = "SubscriptionsController: Requesting delete operation for subscription with id:".concat(subscription_id).concat("!!!");
        LOG.info(message);

        service.findAndDelete(subscription_id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @GetMapping(value = "/{subscription_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Subscription> findSubscriptionById(@PathVariable String subscription_id) throws SubscriptionNotFoundException {
        String message = "SubscriptionsController: Requesting information for subscription with id:".concat(subscription_id).concat("!!!");
        LOG.info(message);

        return ResponseEntity.status(HttpStatus.OK).body(service.get(subscription_id));
    }
}
