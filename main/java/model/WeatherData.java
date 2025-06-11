package model;

public class WeatherData
{
    private double windSpeed;
    private double windDirection;

    private double precipitation;

    private double solarRadiation;
    private double temperature;

    private double pressure;

    public WeatherData() {}

    public WeatherData(double windSpeed, double windDirection, double precipitation, double solarRadiation, double temperature, double pressure) {
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.precipitation = precipitation;
        this.solarRadiation = solarRadiation;
        this.temperature = temperature;
        this.pressure = pressure;
    }

    public double getPressure() { return pressure; }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public double getSolarRadiation() {
        return solarRadiation;
    }

    @Override
    public String toString() {
        return String.format("Ветер: %.2f м/с (%.1f°), Осадки: %.2f мм, Солнечная радиация: %.2f Вт/м²",
                windSpeed, windDirection, precipitation, solarRadiation);
    }
}
