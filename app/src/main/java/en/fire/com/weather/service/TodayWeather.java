package en.fire.com.weather.service;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import en.fire.com.weather.data.model.Weather;
import en.fire.com.weather.view.MyEventListener;

public class TodayWeather extends HttpService {
    private final Weather weather;
    private final MyEventListener activity;

    public TodayWeather(String latitude, String longitude, Weather weather, MyEventListener activity, String apikey) {
        super(latitude, longitude, apikey);
        this.weather = weather;
        this.activity = activity;
    }


    @Override
    protected void parseResponse(String response) {
        parseTodayJson(response);
    }


    private void parseTodayJson(String result) {
        try {
            JSONObject reader = new JSONObject(result);

            final String code = reader.optString("cod");
            if ("404".equals(code)) {
                return;
            }

            String city = reader.getString("name");
            String country = "";
            JSONObject countryObj = reader.optJSONObject("sys");
            if (countryObj != null) {
                country = countryObj.getString("country");
                weather.setSunrise(countryObj.getString("sunrise"));
                weather.setSunset(countryObj.getString("sunset"));
            }
            weather.setCity(city);
            weather.setCountry(country);


            JSONObject main = reader.getJSONObject("main");

            weather.setTemperature(main.getString("temp"));
            weather.setMinTemp(main.getString("temp_min"));
            weather.setMaxTemp(main.getString("temp_max"));
            weather.setDescription(reader.getJSONArray("weather").getJSONObject(0).getString("description"));
            weather.setId(reader.getJSONArray("weather").getJSONObject(0).getString("id"));
            JSONObject windObj = reader.getJSONObject("wind");
            weather.setWind(windObj.getString("speed"));
            weather.setPressure(main.getString("pressure"));
            weather.setPressure(main.getString("pressure"));
            weather.setHumidity(main.getString("humidity"));

            activity.onEventCompleted();
        } catch (JSONException e) {
            Log.e("JSONException Data", result);
            e.printStackTrace();
        }

    }


    @Override
    protected void onPostExecute(TaskOutput output) {


    }

}