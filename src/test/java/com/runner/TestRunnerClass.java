package com.runner;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.base.BaseClass;
import com.reports.Reporting;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

/**
 * 
 * @author ELCOT
 * @see Used to run  over all excecution partin this class
 * @date 26-01-2023
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features="src\\test\\resources",glue="com.stepdefination",plugin= {"pretty","json:target\\index.json"},publish=true,
                  dryRun=false,snippets=SnippetType.CAMELCASE,monochrome=true,stepNotifications=true,tags=("@Login"))
public class TestRunnerClass extends BaseClass {

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @see Used to to generate jvm report
	 */
	@AfterClass
	public static void reportClass() throws FileNotFoundException, IOException {
		
		Reporting.generateJvmreport(getProjectPath()+getPropertyFileValue("jsonpath"));
		
		

		
	}
	
}
		
