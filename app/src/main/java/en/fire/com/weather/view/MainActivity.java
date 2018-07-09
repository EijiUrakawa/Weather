package en.fire.com.weather.view;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Objects;

import en.fire.com.weather.R;
import en.fire.com.weather.data.model.Weather;
import en.fire.com.weather.utils.UnitConvert;

public class MainActivity extends AppCompatActivity {
    private Weather weather;


    private TextView temperature;
    private TextView description;
    private TextView wind;
    private TextView pressure;
    private TextView humidity;
    private TextView sunrise;
    private TextView sunset;
    private TextView city;
    private ImageView icon;
    private TextView minTemp;
    private TextView maxTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle data = getIntent().getExtras();
        weather = Objects.requireNonNull(data).getParcelable("weather");
        // Initialize textboxes
        temperature = findViewById(R.id.temperature);
        minTemp = findViewById(R.id.minTempAnswer);
        maxTemp = findViewById(R.id.maxTempAnswer);
        description = findViewById(R.id.description);
        wind = findViewById(R.id.windAnswer);
        pressure = findViewById(R.id.pressureAnswer);
        humidity = findViewById(R.id.humidityAnswer);
        sunrise = findViewById(R.id.sunriseAnswer);
        sunset = findViewById(R.id.sunsetAnswer);
        city = findViewById(R.id.city);
        icon = findViewById(R.id.iconWeather);

    }

    protected void onStart() {
        super.onStart();

        refreshFields();
        setWeatherIcon();
    }

    @SuppressLint("SetTextI18n")
    private void refreshFields() {
        city.setText(weather.getCity().concat(", ").concat(weather.getCountry()));
        wind.setText(weather.getWind());

        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());
        Objects.requireNonNull(getSupportActionBar()).setTitle(weather.getCity().concat(", ").concat(weather.getCountry()));

        // Temperature
        float temp = UnitConvert.convertTemperature(Float.parseFloat(weather.getTemperature()), weather.getCountry());
        temp = Math.round(temp);

        // Min Temperature
        @SuppressWarnings("UnusedAssignment") float minimunTemp = UnitConvert.convertTemperature(Float.parseFloat(weather.getMinTemp()), weather.getCountry());
        minimunTemp = Math.round(temp);

        // Max Temperature
        @SuppressWarnings("UnusedAssignment") float maximumTemp = UnitConvert.convertTemperature(Float.parseFloat(weather.getMaxTemp()), weather.getCountry());
        maximumTemp = Math.round(temp);


        // Wind
        double windValue;
        try {
            windValue = Double.parseDouble(weather.getWind());
        } catch (Exception e) {
            e.printStackTrace();
            windValue = 0;
        }
        windValue = UnitConvert.convertWind(windValue);
        String unit = UnitConvert.unitTemperature(weather.getCountry());
        // Pressure
        double press = UnitConvert.convertPressure((float) Double.parseDouble(weather.getPressure()));

        temperature.setText(new DecimalFormat("0.#").format(temp) + " " + unit);
        minTemp.setText(new DecimalFormat("0.#").format(minimunTemp) + " " + unit);
        maxTemp.setText(new DecimalFormat("0.#").format(maximumTemp) + " " + unit);

        description.setText(weather.getDescription().substring(0, 1).toUpperCase() + weather.getDescription().substring(1));
        wind.setText(windValue + " " + "m/s");
        pressure.setText(new DecimalFormat("0.0").format(press) + " " + "hpa");
        humidity.setText(weather.getHumidity() + " %");
        sunrise.setText(timeFormat.format(weather.getSunrise()));
        sunset.setText(timeFormat.format(weather.getSunset()));


    }


    private void setWeatherIcon() {
        int id = Integer.parseInt(weather.getId()) / 100;

        if (Integer.parseInt(weather.getId()) == 800) {
            icon.setImageResource(R.drawable.icons8_sun_48);

        } else {
            switch (id) {
                case 2:
                    icon.setImageResource(R.drawable.icons8_stormy_weather_48);
                    break;
                case 3:
                    icon.setImageResource(R.drawable.icons8_umbrella_96);
                    break;
                case 7:
                    icon.setImageResource(R.drawable.icons8_clouds_48);
                    break;
                case 8:
                    icon.setImageResource(R.drawable.icons8_partly_cloudy_day_48);
                    break;
                case 5:
                    icon.setImageResource(R.drawable.icons8_rain_48);
                    break;
            }
        }
    }
}
