package com.utils;

public class WeatherComparator {

	private String condition = null;
	private String wind = null;
	private float humidity;
	private float tempInDegrees;
	private float tempInFahrenheit;

	public String getCondition() {
		return condition;
	}

	public String getWind() {
		return wind;
	}

	public float getHumidity() {
		return humidity;
	}

	public float getTempInDegrees() {
		return tempInDegrees;
	}

	public float getTempInFahrenheit() {
		return tempInFahrenheit;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public void setTempInDegrees(float tempInDegrees) {
		this.tempInDegrees = tempInDegrees;
	}

	public void setTempInFahrenheit(float tempInFahrenheit) {
		this.tempInFahrenheit = tempInFahrenheit;
	}

	public static boolean compareWithVariance(float value1, float value2, float variance) {
		if (Math.abs(value1 - value2) <= variance)
			return true;
		else
			return false;
	}

}
