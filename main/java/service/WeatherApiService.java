package service;

import model.WeatherData;
import org.json.JSONArray;
import org.json.JSONObject;
import util.ApiKeyProvider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class WeatherApiService
{

    private static final String API_KEY = ApiKeyProvider.getApiKey();

    private static final String HISTORICAL_URL_TEMPLATE =
            "https://api.openweathermap.org/data/3.0/onecall/timemachine?lat=%f&lon=%f&dt=%d&units=metric&appid=%s";

    public static List<WeatherData> getHistoricalDataFor10Years(double lat, double lon)
    {
        List<WeatherData> results = new ArrayList<>();

        LocalDate now = LocalDate.now(ZoneOffset.UTC);
        LocalDate startDate = now.minusYears(10);

        LocalDate date = startDate;
        while (!date.isAfter(now))
        {
            try
            {
                long unixTimestamp = date.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
                WeatherData wd = fetchDataForTimestamp(lat, lon, unixTimestamp);
                if (wd != null)
                {
                    results.add(wd);
                }

            } catch (Exception e)
            {
                System.err.println("Error while requesting data for date " + date + ": " + e.getMessage());
            }
            date = date.plusMonths(1);
        }

        return results;
    }

    public static List<WeatherData> getHistoricalDataForYear(double lat, double lon)
    {
        List<WeatherData> results = new ArrayList<>();

        LocalDate now = LocalDate.now(ZoneOffset.UTC);
        LocalDate startDate = now.minusYears(1);

        LocalDate date = startDate;
        while (!date.isAfter(now))
        {
            try
            {
                long unixTimestamp = date.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
                WeatherData wd = fetchDataForTimestamp(lat, lon, unixTimestamp);
                if (wd != null)
                {
                    results.add(wd);
                }
            }
            catch (Exception e)
            {
                System.err.println("Error while requesting data for date " + date + ": " + e.getMessage());
            }
            date = date.plusDays(1);
        }

        return results;
    }

    public static String analyzeWeatherData(List<WeatherData> dataList)
    {
        int rainyDays = 0;
        int sunnyDays = 0;
        double totalWindSpeed = 0.0;
        double totalTemperature = 0.0;

        for (WeatherData wd : dataList)
        {
            if (wd.getPrecipitation() > 0)
            {
                rainyDays++;
            }
            else
            {
                sunnyDays++;
            }
            totalWindSpeed += wd.getWindSpeed();
            totalTemperature += wd.getTemperature();
        }

        int count = dataList.size();
        double avgWindSpeed = count > 0 ? totalWindSpeed / count : 0.0;
        double avgTemperature = count > 0 ? totalTemperature / count : 0.0;

        return String.format(
                "Period: %d months\n" +
                        "Number of rainy days: %d\n" +
                        "Number of sunny days: %d\n" +
                        "Average wind speed: %.2f m/s\n" +
                        "Average temperature: %.2f °C",
                count, rainyDays, sunnyDays, avgWindSpeed, avgTemperature);
    }

    private static WeatherData fetchDataForTimestamp(double lat, double lon, long dt) throws Exception
    {
        String urlStr = String.format(HISTORICAL_URL_TEMPLATE, lat, lon, dt, API_KEY);

        URL url = new URL(urlStr);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200)
        {
            System.err.println("Error API: HTTP " + responseCode + " for dt=" + dt);
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        {
            response.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());

        if (!json.has("data"))
        {
            System.err.println("There is no 'data' field in the response for dt=" + dt);
            return null;
        }

        JSONArray dataArray = json.getJSONArray("data");
        if (dataArray.length() == 0)
        {
            System.err.println("Empty array 'data' for dt=" + dt);
            return null;
        }

        JSONObject dataPoint = dataArray.getJSONObject(0);

        double windSpeed = dataPoint.optDouble("wind_speed", 0.0);
        double windDirection = dataPoint.optDouble("wind_deg", 0.0);

        double precipitation = 0.0;
        if (dataPoint.has("rain"))
        {
            precipitation += dataPoint.getJSONObject("rain").optDouble("1h", 0.0);
        }
        if (dataPoint.has("snow"))
        {
            precipitation += dataPoint.getJSONObject("snow").optDouble("1h", 0.0);
        }

        double cloudiness = dataPoint.optDouble("clouds", 0.0); // в процентах
        double solarRadiation = 1000 * (1 - cloudiness / 100.0);

        double pressure = dataPoint.optDouble("pressure", 1013.25);

        double temperature = dataPoint.optDouble("temp", 0.0);
        return new WeatherData(windSpeed, windDirection, precipitation, solarRadiation, temperature, pressure);
    }
}
