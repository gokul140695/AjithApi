package com.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.base.BaseClass;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

/**
 * 
 * @author ELCOT
 * @see Used to generate the jvm report
 * @date 26-01-2023
 *
 */
public class Reporting extends BaseClass{
	
	public static void generateJvmreport(String jsonFile) throws FileNotFoundException, IOException {
		
		

		File file = new File(getProjectPath() +getPropertyFileValue("jvmpath"));
		
		Configuration configuration=new Configuration(file, "AdactinHotel");
		
		configuration.addClassifications("Browser", "chrome");
		configuration.addClassifications("version", "108");
		configuration.addClassifications("os", "win10");
		configuration.addClassifications("sprint", "34");
		
	List<String> jsonFiles=new ArrayList<String>();
	
	jsonFiles.add(jsonFile);
	
	ReportBuilder builder=new ReportBuilder(jsonFiles, configuration);
	
	builder.generateReports();
	
		
	}
	


}
