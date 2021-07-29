package model.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.*;
import com.google.gson.reflect.*;

public class WeatherProcessing {
	
	private static String API_KEY = "96820d212742dbd4f13dd2ad83a3f2ea";
	
	/**
	 * parse json
	 * @param str json to be parsed for getting infos
	 * @return  map containing a value of type Object for every String key
	 */
	public static Map<String, Object> jsonToMap(String str){
		Map<String, Object> map = new Gson().fromJson(
				str, new TypeToken<HashMap<String, Object>>() {}.getType()
				);
		return map;
	}
	
	/**
	 * read input stream from the urlConnection line by line and form string containing all the infos
	 * @param latitude city latitude
	 * @param longitude city longitude
	 * @return string containing the json infos in a string format
	 */
	public static String getCityWeatherInfos(float latitude, float longitude) {
		String urlString = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude 
				+ "&lon=" + longitude + "&appid=" + API_KEY;
		
		StringBuilder result = new StringBuilder();
		
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			rd.close();
			System.out.println(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}	
			String weatherInfos = result.toString();
			
			return weatherInfos;
	}
	
	/**
	 * parse the weather field from the string containing infos
	 * @param weatherInfos the input string
	 * @return the weather from the city (eg. rain, clouds)
	 */
	public static String getWeather(String weatherInfos) {
		JSONObject weatherAsJson = new JSONObject(weatherInfos);
		
		JSONArray arr = weatherAsJson.getJSONArray("weather");
		
		String weather = arr.getJSONObject(0).getString("main");
		//System.out.println(weather);
		return weather;
	}
	
	/**
	 * convert from kelvin temperature into celsius temperature
	 * @param kelvinTemperature the temperature from city in kelvin
	 * @return temperature in celsius degrees
	 */
	public static double convertKelvinToCelsius(double kelvinTemperature) {
		double celsiusTemperature = kelvinTemperature - 273;
		
		return celsiusTemperature;
	}
	
	/**
	 * get the temperature from the city
	 * create a map from the input
	 * create a map from the "main" key where the temp Object is
	 * get the temperature as a double Object, convert in celsius and round at 2 zecimals
	 * @param weatherInfos the string containing the infos
	 * @return the rounded temperature in celsius
	 */
	public static double getTemperature(String weatherInfos) {
		Map<String, Object> respMap = jsonToMap(weatherInfos.toString());
		Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
		
		Object temp = mainMap.get("temp");
		
		double temperatureInKelvin = (double)temp;
		
		double temperatureInCelsius = convertKelvinToCelsius(temperatureInKelvin);
		double rounded = BigDecimal.valueOf(temperatureInCelsius)
				.setScale(2, RoundingMode.HALF_UP)
				.doubleValue();
		return rounded;

	}
	
	/**
	 * get the humidity from the city
	 * @param weatherInfos the string containing the infos
	 * @return humidity as a rounded double value
	 */
	public static double getHumidity(String weatherInfos) {
		Map<String, Object> respMap = jsonToMap(weatherInfos.toString());
		Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
		
		Object humidityObject = mainMap.get("humidity");
		
		double humidity = (double)humidityObject;
		
		double rounded = BigDecimal.valueOf(humidity)
				.setScale(2, RoundingMode.HALF_UP)
				.doubleValue();
		return rounded;

	}
	
	/**
	 * get the wind speed from the city
	 * @param weatherInfos the string containing the infos
	 * @return wind as a rounded double value
	 */
	public static double getWind(String weatherInfos) {
		Map<String, Object> respMap = jsonToMap(weatherInfos.toString());
		Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());
		
		Object windObject = windMap.get("speed");
		
		double windSpeed = (double)windObject;
		
		double rounded = BigDecimal.valueOf(windSpeed)
				.setScale(2, RoundingMode.HALF_UP)
				.doubleValue();
		return rounded;

	}
	
}
