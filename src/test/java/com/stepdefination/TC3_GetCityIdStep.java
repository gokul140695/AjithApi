package com.stepdefination;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.base.BaseClass;
import com.endpoints.Endpoints;
import com.payload.address.AddressPayload;
import com.pojo.address.CityList;
import com.pojo.address.CityList_Output_Pojo;
import com.pojo.address.CityList_Input_Pojo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class TC3_GetCityIdStep extends BaseClass {
	Response response;

	AddressPayload addressPayload = new AddressPayload();

	@Given("User add headers for city list")
	public void userAddHeadersForCityList() {

		List<Header> listHeader = new ArrayList<>();

		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Content-Type", "application/json");

		listHeader.add(h1);
		listHeader.add(h2);

		Headers headers = new Headers(listHeader);

		addHeaders(headers);
	}

	@When("User add request body for state id")
	public void userAddRequestBodyForStateId() {

		CityList_Input_Pojo cityList_Input_Pojo = addressPayload
				.getCityIdPayload(TC1_LoginStep.globalDatas.getStateId());

		addBody(cityList_Input_Pojo);

	}

	@When("User send {string} request for city list endpoint")
	public void userSendRequestForCityListEndpoint(String type) {

		response = requestType(type, Endpoints.GETCITYLIST);

		int actStatusCode = getResponseCode(response);

		TC1_LoginStep.globalDatas.setStatusCode(actStatusCode);

	}

	@Then("User should verify city list response message matches {string} and saved city id")
	public void userShouldVerifyCityListResponseMessageMatchesAndSavedCityId(String expCityName) {

		CityList_Output_Pojo cityList_Output_Pojo = response.as(CityList_Output_Pojo.class);

		ArrayList<CityList> cityLists = cityList_Output_Pojo.getData();

		for (CityList eachCityList : cityLists) {

			String actCityName = eachCityList.getName();

			if (actCityName.equals("Kodambakkam")) {
				
				int cityId = eachCityList.getId();

				TC1_LoginStep.globalDatas.setCityId(cityId);
				

				Assert.assertEquals("verify city name as Kodambakkam",expCityName,actCityName);

			}

		}

	}

}
