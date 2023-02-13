package com.stepdefination;

import java.util.ArrayList;

import org.junit.Assert;

import com.base.BaseClass;
import com.endpoints.Endpoints;
import com.pojo.address.StateList;
import com.pojo.address.StateList_Output_Pojo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class TC2_GetStateIdStep extends BaseClass {

	Response response;

	@Given("User add headers for to state list")
	public void userAddHeadersForToStateList() {

		addHeader("accept", "application/json");
	}

	@When("User send {string} request for state list endpoint")
	public void userSendRequestForStateListEndpoint(String type) {

		response = requestType(type, Endpoints.GETSTATELIST);

		int statusCode = getResponseCode(response);

		TC1_LoginStep.globalDatas.setStatusCode(statusCode);

	}

	@Then("User should verify the state list response message matches {string} and saved state id")
	public void userShouldVerifyTheStateListResponseMessageMatchesAndSavedStateId(String expStateName) {

		StateList_Output_Pojo stateList_Output_Pojo = response.as(StateList_Output_Pojo.class);

		ArrayList<StateList> stateList = stateList_Output_Pojo.getData();

		for (StateList eachStateList : stateList) {

			String actStateName = eachStateList.getName();

			if (actStateName.equals("Tamil Nadu")) {
                     
				int stateIdNum = eachStateList.getId();
				
				String stateId = String.valueOf(stateIdNum);
				
				TC1_LoginStep.globalDatas.setStateIdNum(stateIdNum);
				
				TC1_LoginStep.globalDatas.setStateId(stateId);
				
				Assert.assertEquals("verify state name as Tamil Nadu",expStateName, actStateName);
				
			    break;      
			
			}

		}
	}
}
