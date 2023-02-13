package com.stepdefination;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;

import com.base.BaseClass;
import com.endpoints.Endpoints;
import com.golbal.GlobalDatas;
import com.pojo.login.Login_Output_Pojo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class TC1_LoginStep extends BaseClass {

	Response response;

	static GlobalDatas globalDatas = new GlobalDatas();

	@Given("User add header")
	public void userAddHeader() {

		addHeader("accept", "application/json");

	}

	@When("User add basic authendication for login")
	public void userAddBasicAuthendicationForLogin() throws FileNotFoundException, IOException {

		addBasicAuth(getPropertyFileValue("username"), getPropertyFileValue("password"));

	}

	@When("User send {string} request for login endpoint")
	public void userSendRequestForLoginEndpoint(String type) {

		response = requestType(type, Endpoints.POSTMANBASICAUTHLOGIN);

		int statusCode = getResponseCode(response);

		globalDatas.setStatusCode(statusCode);

	}

	@Then("User shoud verify login response body firstName present as {string} and get the logtoken saved")
	public void userShoudVerifyLoginResponseBodyFirstNamePresentAsAndGetTheLogtokenSaved(String expFirstName) {

		Login_Output_Pojo loginpage = response.as(Login_Output_Pojo.class);

		String actFirstName = loginpage.getData().getFirst_name();
		
		System.out.println(actFirstName);

		Assert.assertEquals("verify first name", expFirstName, actFirstName);

		String logtoken = loginpage.getData().getLogtoken();

		System.out.println(logtoken);

		globalDatas.setLogtoken(logtoken);

	}

}
