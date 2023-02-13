package com.stepdefination;

import java.util.ArrayList;
import java.util.List;

import com.base.BaseClass;
import com.endpoints.Endpoints;
import com.payload.address.AddressPayload;
import com.pojo.address.AddUserAddress_Input_Pojo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class TC4_AddressStep extends BaseClass {

	AddressPayload addressPayload = new AddressPayload();

	Response response;

	@Given("User add Header and bearer authorization for accessing address endpoint")
	public void userAddHeaderAndBearerAuthorizationForAccessingAddressEndpoint() {

		List<Header> listHeader = new ArrayList<>();

		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Authorization", "Bearer " + TC1_LoginStep.globalDatas.getLogtoken());
		Header h3 = new Header("Content-Type", "application/json");

		listHeader.add(h1);
		listHeader.add(h2);
		listHeader.add(h3);

		Headers headers = new Headers(listHeader);

		addHeaders(headers);

	}
	
	

	@When("User add request body for add new address {string} , {string} , {string} , {string} , {string} , {string} , {string} and {string}")
	public void userAddRequestBodyForAddNewAddressAnd(String first_name, String last_name, String mobile,
			String apartment, String country, String zipcode, String address, String address_type) {

		AddUserAddress_Input_Pojo addUserAddress_Input_Pojo = addressPayload.addUserAddressPayload(first_name,
				last_name, mobile, apartment, TC1_LoginStep.globalDatas.getStateIdNum(),
				TC1_LoginStep.globalDatas.getCityId(), country, zipcode, address, address_type);

		addBody(addUserAddress_Input_Pojo);

	}

	@When("User send  {string} request for add user address endpoint")
	public void userSendRequestForAddUserAddressEndpoint(String string) {

		response = requestType("POST", Endpoints.ADDUSERADDRESS);

		int actStatusCode = getResponseCode(response);

		TC1_LoginStep.globalDatas.setStatusCode(actStatusCode);

	}

	@Then("User should verify the add user address response message matches {string}")
	public void userShouldVerifyTheAddUserAddressResponseMessageMatches(String string) {

	}

	@Given("User add Header and bearer authorization for accessing update address endpoint")
	public void userAddHeaderAndBearerAuthorizationForAccessingUpdateAddressEndpoint() {

	}

	@When("User add request body for update user address addressId and {string} , {string} , {string} , {string} , {string} , {string} , {string} , {string} , {string} and {string}")
	public void userAddRequestBodyForUpdateUserAddressAddressIdAndAnd(String string, String string2, String string3,
			String string4, String string5, String string6, String string7, String string8, String string9,
			String string10) {

	}

	@When("User send  {string} request for update user address endpoint")
	public void userSendRequestForUpdateUserAddressEndpoint(String string) {

	}

	@Then("User verify the update user address response message matches {string}")
	public void userVerifyTheUpdateUserAddressResponseMessageMatches(String string) {

	}

	@When("User send  {string} request for get user address endpoint")
	public void userSendRequestForGetUserAddressEndpoint(String string) {

	}

	@Then("User verify get user address response message matches {string}")
	public void userVerifyGetUserAddressResponseMessageMatches(String string) {

	}

	@When("User add request body for delete user address address_id")
	public void userAddRequestBodyForDeleteUserAddressAddressId() {

	}

	@When("User send  {string} request for delete user address endpoint")
	public void userSendRequestForDeleteUserAddressEndpoint(String string) {

	}

	@Then("User should verify delete user address response message matches {string}")
	public void userShouldVerifyDeleteUserAddressResponseMessageMatches(String string) {

	}

}
