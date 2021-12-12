package com.sap.configapi.controllers;

import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.model.DtoConverter;
import com.sap.model.dto.ActionDto;
import com.sap.model.dto.PagedResponseDto;
import com.sap.model.exceptions.actions.ActionNotFoundException;
import com.sap.model.exceptions.response.PageRequestOutOfBounds;
import com.sap.model.exceptions.response.ResultsRequestOutOfBounds;
import com.sap.model.services.ActionsService;
import com.sap.model.utils.PagedResponseUtils;
import com.sap.persistence.entities.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/actions")
public class ActionsController {

    private static final Logger LOG = LoggerFactory.getLogger(ActionsController.class);
    private final ActionsService service;

    @Autowired
    public ActionsController(ActionsService service) {
        this.service = service;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PagedResponseDto<Action>> listActions(@RequestParam(value = "page",required = false) Integer pageRequest,
                                                                @RequestParam(value = "results", required = false) Integer resultsRequest )
                                                                                    throws PageRequestOutOfBounds, ResultsRequestOutOfBounds {
        LOG.info("ActionController: Requesting a list of all actions!!!");

        PagedResponseUtils utils = PagedResponseUtils
                                    .newInstance(pageRequest, resultsRequest, service)
                                    .validateInput();

        LOG.info(Arrays.toString(service.pageOf(utils.getPageRequest(), utils.getResultsRequest()).getContent().toArray()));

        PagedResponseDto<Action> response = PagedResponseDto.builder()
                                                .withMetaData(utils.createMetaData())
                                                .withResults( service.pageOf(utils.getPageRequest(), utils.getResultsRequest()).getContent() )
                                                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Action> createAction(@NotNull @Valid @RequestBody ActionDto request) throws ActionNotFoundException {
        LOG.info("ActionController: Request for creating action!!!");

        Action action = DtoConverter.toAction(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(action));
    }

    @DeleteMapping(value = "/{action_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteAction(@PathVariable String action_id) throws ActionNotFoundException {
        String message = "ActionController: Requesting delete operation for action with id:".concat(action_id).concat("!!!");
        LOG.info(message);

        service.findAndDelete(action_id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{action_id}", produces = "application/json")
    public ResponseEntity<Action> findActionById(@PathVariable(name = "action_id") String action_id) throws ActionNotFoundException {
        String message = "ActionController: Requesting information for action with id:".concat(action_id).concat("!!!");
        LOG.info(message);

        return ResponseEntity.status(HttpStatus.OK).body(service.get(action_id));
    }

    @GetMapping("/hello-token")
    public Map<String, String> message(@AuthenticationPrincipal Token token) {
        Map<String, String> result = new HashMap<>();
        result.put("grant type", token.getGrantType());
        result.put("client id", token.getClientId());
        result.put("subaccount id", token.getSubaccountId());
        result.put("logon name", token.getLogonName());
        result.put("family name", token.getFamilyName());
        result.put("given name", token.getGivenName());
        result.put("email", token.getEmail());
        result.put("authorities", String.valueOf(token.getAuthorities()));
        result.put("scopes", String.valueOf(token.getScopes()));

        return result;
    }
    

}
