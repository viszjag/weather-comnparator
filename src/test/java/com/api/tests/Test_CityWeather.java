package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import java.util.Properties;

import org.testng.annotations.Test;

import com.utils.TestUtil;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Test_CityWeather {
	static Properties CONFIG= TestUtil.initConfig();

	public static String baseURI = CONFIG.getProperty("baseURI");
	public static String apiKey = CONFIG.getProperty("apiKey");
	public static String city = CONFIG.getProperty("city");
	public static String temperatureUnit = CONFIG.getProperty("temperatureUnit");
	public static JsonPath response = null;
	public static String units = "metric";//default to metric(celsius)

	@Test(priority = 1)
	public static void getCityWeather() {
		if(temperatureUnit.equalsIgnoreCase("celsius"))
			units = "metric";//celsius
		else if(temperatureUnit.equalsIgnoreCase("fahrenheit"))
			units = "imperial";//fahrenheit

		Response res =
				given()
				.when()
					.get(baseURI+"find?q="+city+"&appid="+apiKey+"&units="+units+"&mode=json")
				.then()
					.statusCode(200)
					.body("list[0].name", equalToIgnoringCase(city))
					.log().all()
					.extract().response();
		
		response = res.getBody().jsonPath();
	}

}
