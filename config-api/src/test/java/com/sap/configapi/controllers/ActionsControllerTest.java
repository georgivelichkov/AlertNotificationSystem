package com.sap.configapi.controllers;

import com.sap.configapi.config.AbstractTest;
import com.sap.model.dto.ActionDto;
import com.sap.model.dto.PagedResponseDto;
import com.sap.model.factories.BuilderFactory;
import com.sap.model.services.ActionsService;
import com.sap.persistence.entities.Action;
import com.sap.persistence.enums.ActionType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ActiveProfiles("test")
public class ActionsControllerTest extends AbstractTest {

    private static final String ACTIONS_URL = "/actions/";
    private static final String ACTIONS_URL_BY_ID = "/actions/{action_id}";
    private static final String TEST_ACTION_UUID = "f60a7916-5d09-11e9-8647-d663bd873d93";
    private static Action expectedActionA;
    private static Action expectedActionB;
    private ActionsService service;


    @Autowired
    public void setActionService(ActionsService service) {
        this.service = service;
    }

    @Before
    public void setUp() {
        super.setUp();
        expectedActionA = createTestAction("Action A");
        expectedActionB = createTestAction("Action B");
    }

    @Test
    public void givenRequest_whenListOfActions_thenReturnListAndGetOK() throws Exception {

        service.save(expectedActionA);
        service.save(expectedActionB);

        //Given user has requested the list
        MvcResult mvcResult = mvc.perform(get(ACTIONS_URL+"?results=2")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        //And request response code is 200
        assertThat(mvcResult.getResponse().getStatus(), is(200));
        //Then I should return a list of actions
        String content = mvcResult.getResponse().getContentAsString();

        PagedResponseDto<Action> response = super.mapFromJson(content, PagedResponseDto.class);

        assertThat(response.getResults(), hasSize(2));
        assertThat(response.getResults(), containsInAnyOrder(expectedActionA, expectedActionB));
    }

    @Test
    public void givenDto_whenActionCreate_thenReturnCreatedActionAndGetCreatedStatus() throws Exception {

        //Given user has send a request for creating action
        ActionDto dto = new ActionDto("Action A has some description",ActionType.EMAIL, "Action A","example@example.com");

        String inputJson = super.mapToJson(dto);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ACTIONS_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        //And request response code is 201
        assertThat(mvcResult.getResponse().getStatus(), is(201));

        //Then I should return a action with the user parameters
        String content = mvcResult.getResponse().getContentAsString();
        Action response = super.mapFromJson(content, Action.class);

        assertThat(response.getName(), is(dto.getName()));
        assertThat(response.getDescription(), is(dto.getDescription()));
        assertThat(response.getType(), is(dto.getType()));
        assertThat(response.getAttribute(), is(dto.getAttribute()));
    }

    @Test
    public void givenDto_whenActionDeleted_thenGetNoContent() throws Exception {

        service.save(expectedActionB);
        service.save(expectedActionA);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(ACTIONS_URL_BY_ID, expectedActionA.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        //And response code is 204
        assertThat(mvcResult.getResponse().getStatus(), is(204));

        //Then the action should be deleted and the response should be no content
        assertThat(mvcResult.getResponse().getContentAsString().trim().length(), is(0));
    }

    @Test
    public void givenString_whenFindById_thenReturnAction() throws Exception {

        service.save(expectedActionB);
        service.save(expectedActionA);

        MvcResult mvcResult = mvc.perform(get(ACTIONS_URL_BY_ID, expectedActionA.getId())).andReturn();
        assertThat(mvcResult.getResponse().getStatus(), is(200));

        String content = mvcResult.getResponse().getContentAsString();

        Action action = super.mapFromJson(content, Action.class);
        assertTrue(action.getName().length() > 0);
        assertTrue(action.getDescription().length() > 0);
        assertTrue(action.getType().getKey().length() > 0);
        assertTrue(action.getAttribute().length() > 0);

        assertThat(action.getName(), is(expectedActionA.getName()));
        assertThat(action.getDescription(), is(expectedActionA.getDescription()));
        assertThat(action.getAttribute(), is(expectedActionA.getAttribute()));
        assertThat(action.getType(), is(expectedActionA.getType()));
    }


    private Action createTestAction(String name) {
        return BuilderFactory.action()
                .withName(name)
                .withType(ActionType.SLACK)
                .withDescription(name + "has some description")
                .withAttribute("example@example.com").build();
    }

}