package com.sap.configapi.controllers;

import com.sap.model.DtoConverter;
import com.sap.model.dto.ConditionDto;
import com.sap.model.dto.PagedResponseDto;
import com.sap.model.exceptions.conditions.ConditionNotFoundException;
import com.sap.model.exceptions.response.PageRequestOutOfBounds;
import com.sap.model.exceptions.response.ResultsRequestOutOfBounds;
import com.sap.model.services.ConditionsService;
import com.sap.model.utils.PagedResponseUtils;
import com.sap.persistence.entities.Condition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/conditions")
public class ConditionsController {

    private static final Logger LOG = LoggerFactory.getLogger(ConditionsController.class);
    private final ConditionsService service;

    @Autowired
    public ConditionsController(ConditionsService service) {
        this.service = service;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PagedResponseDto<Condition>> listConditions(@RequestParam(value = "page",required = false) Integer pageRequest,
                                                                      @RequestParam(value = "results", required = false) Integer resultsRequest ) throws PageRequestOutOfBounds, ResultsRequestOutOfBounds {
        LOG.info("ConditionsController: Requesting a list of all conditions!!!");

        PagedResponseUtils utils = PagedResponseUtils.newInstance(pageRequest, resultsRequest, service)
                                            .validateInput();

        PagedResponseDto<Condition> response = PagedResponseDto.builder()
                .withMetaData(utils.createMetaData())
                .withResults( service.pageOf(utils.getPageRequest(), utils.getResultsRequest()).getContent() )
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Condition> createCondition(@Valid @RequestBody ConditionDto dto) {
        LOG.info("ConditionsController: Request for creating condition!!!");

        Condition condition = DtoConverter.toCondition(dto);

        service.save(condition);

        return ResponseEntity.status(HttpStatus.CREATED).body(condition);
    }

    @DeleteMapping(value = "/{condition_id}", produces = "application/json")
    public ResponseEntity<?> deleteCondition(@PathVariable String condition_id) throws ConditionNotFoundException {
        String message = "ConditionController: Requesting delete operation for condition with id:".concat(condition_id).concat("!!!");
        LOG.info(message);

        service.findAndDelete(condition_id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{condition_id}", produces = "application/json")
    public ResponseEntity<Condition> findConditionById(@PathVariable String condition_id) throws ConditionNotFoundException {
        String message = "ConditionController: Requesting information for action with id:".concat(condition_id).concat("!!!");
        LOG.info(message);

        return ResponseEntity.status(HttpStatus.OK).body(service.get(condition_id));
    }
}
