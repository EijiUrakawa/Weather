package en.fire.com.weather.utils;

public class UnitConvert {


    private static final String US = "US";
    private static final String BAH = "BAH";
    private static final String PLW = "PLW";
    private static final String BIZ = "BIZ";
    private static final String FSM = "FSM";
    private static final String CAY = "CAY";
    private static final String MHL = "MHL";


    public static float convertTemperature(float temperature, String country) {

        if (listFarenheitCountries(country)) {
            return kelvinToFahrenheit(temperature);
        } else {
            return kelvinToCelsius(temperature);
        }
    }


    public static String unitTemperature(String country) {

        if (listFarenheitCountries(country)) {
            return "°F";
        } else {
            return "°C";
        }
    }

    private static boolean listFarenheitCountries(String country) {
        switch (country) {
            case US:
                return true;
            case BAH:
                return true;
            case PLW:
                return true;
            case BIZ:
                return true;
            case FSM:
                return true;
            case CAY:
                return true;
            case MHL:
                return true;
            default:
                return false;

        }

    }

    private static float kelvinToCelsius(float kelvinTemp) {
        return kelvinTemp - 273.15f;
    }

    private static float kelvinToFahrenheit(float kelvinTemp) {
        return (((9 * kelvinToCelsius(kelvinTemp)) / 5) + 32);
    }

    public static float convertPressure(float pressure) {
        return pressure / 10;
    }

    public static double convertWind(double wind) {
        return wind;
    }

}

