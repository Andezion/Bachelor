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

public class WeatherApiService {

    private static final String API_KEY = ApiKeyProvider.getApiKey();

    // Шаблон URL для исторических данных по времени (timemachine)
    private static final String HISTORICAL_URL_TEMPLATE =
            "https://api.openweathermap.org/data/3.0/onecall/timemachine?lat=%f&lon=%f&dt=%d&units=metric&appid=%s";

    /**
     * Получение исторических данных за 10 лет по координатам с шагом 1 месяц.
     */
    public static List<WeatherData> getHistoricalDataFor10Years(double lat, double lon) {
        List<WeatherData> results = new ArrayList<>();

        LocalDate now = LocalDate.now(ZoneOffset.UTC);
        LocalDate startDate = now.minusYears(10);

        LocalDate date = startDate;
        while (!date.isAfter(now)) {
            try {
                long unixTimestamp = date.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
                WeatherData wd = fetchDataForTimestamp(lat, lon, unixTimestamp);
                if (wd != null) {
                    results.add(wd);
                }
                // Пауза для предотвращения превышения лимитов API (примерно 1.1 секунды)
                Thread.sleep(1100);
            } catch (Exception e) {
                System.err.println("Ошибка при запросе данных за дату " + date + ": " + e.getMessage());
            }
            date = date.plusMonths(1);
        }

        return results;
    }

    public static List<WeatherData> getHistoricalDataForYear(double lat, double lon) {
        List<WeatherData> results = new ArrayList<>();

        LocalDate now = LocalDate.now(ZoneOffset.UTC);
        LocalDate startDate = now.minusYears(1);

        LocalDate date = startDate;
        while (!date.isAfter(now)) {
            try {
                long unixTimestamp = date.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
                WeatherData wd = fetchDataForTimestamp(lat, lon, unixTimestamp);
                if (wd != null) {
                    results.add(wd);
                }
                //Thread.sleep(1100);
            } catch (Exception e) {
                System.err.println("Ошибка при запросе данных за дату " + date + ": " + e.getMessage());
            }
            date = date.plusDays(1);
        }

        return results;
    }

    public static String analyzeWeatherData(List<WeatherData> dataList) {
        int rainyDays = 0;
        int sunnyDays = 0;
        double totalWindSpeed = 0.0;
        double totalTemperature = 0.0;

        for (WeatherData wd : dataList) {
            if (wd.getPrecipitation() > 0) {
                rainyDays++;
            } else {
                sunnyDays++;
            }
            totalWindSpeed += wd.getWindSpeed();
            totalTemperature += wd.getTemperature();
        }

        int count = dataList.size();
        double avgWindSpeed = count > 0 ? totalWindSpeed / count : 0.0;
        double avgTemperature = count > 0 ? totalTemperature / count : 0.0;

        return String.format(
                "Период: %d месяцев\n" +
                        "Количество дождливых дней: %d\n" +
                        "Количество солнечных дней: %d\n" +
                        "Средняя скорость ветра: %.2f м/с\n" +
                        "Средняя температура: %.2f °C",
                count, rainyDays, sunnyDays, avgWindSpeed, avgTemperature);
    }


    /**
     * Запрос данных для одной даты (timestamp) и парсинг аггрегированных данных в WeatherData.
     */
    private static WeatherData fetchDataForTimestamp(double lat, double lon, long dt) throws Exception {
        String urlStr = String.format(HISTORICAL_URL_TEMPLATE, lat, lon, dt, API_KEY);
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            System.err.println("Ошибка API: HTTP " + responseCode + " для dt=" + dt);
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());

        if (!json.has("data")) {
            System.err.println("В ответе нет поля 'data' для dt=" + dt);
            return null;
        }

        JSONArray dataArray = json.getJSONArray("data");
        if (dataArray.length() == 0) {
            System.err.println("Пустой массив 'data' для dt=" + dt);
            return null;
        }

        JSONObject dataPoint = dataArray.getJSONObject(0);

        double windSpeed = dataPoint.optDouble("wind_speed", 0.0);
        double windDirection = dataPoint.optDouble("wind_deg", 0.0);

        double precipitation = 0.0;
        if (dataPoint.has("rain")) {
            precipitation += dataPoint.getJSONObject("rain").optDouble("1h", 0.0);
        }
        if (dataPoint.has("snow")) {
            precipitation += dataPoint.getJSONObject("snow").optDouble("1h", 0.0);
        }

        // Солнечную радиацию временно ставим 0, т.к. в timemachine её нет
        double solarRadiation = 0.0;
        double temperature = dataPoint.optDouble("temp", 0.0);
        return new WeatherData(windSpeed, windDirection, precipitation, solarRadiation, temperature);
    }
}
