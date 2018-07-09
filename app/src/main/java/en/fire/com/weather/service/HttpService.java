package en.fire.com.weather.service;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

abstract class HttpService extends AsyncTask<String, String, TaskOutput> {
    private final String longitude;
    private final String latidude;
    private final String apikey;

    HttpService(String latitude, String longitude, String apikey) {
        this.latidude = latitude;
        this.longitude = longitude;
        this.apikey = apikey;
    }


    @Override
    protected TaskOutput doInBackground(String... params) {

        TaskOutput output = new TaskOutput();
        String response = "";
        if (this.longitude != null || this.latidude != null) {
            try {

                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=" + this.latidude + "&lon=" + this.longitude + "&appid=" + apikey);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                    BufferedReader r = new BufferedReader(inputStreamReader);

                    String line;
                    while ((line = r.readLine()) != null) {
                        response += line + "\n";
                    }
                    close(r);
                    urlConnection.disconnect();
                    // Background work finished successfully
                    Log.i("Task", "done successfully");
                    output.taskResult = TaskResult.SUCCESS;
                }
                if (TaskResult.SUCCESS.equals(output.taskResult)) {
                    // Parse JSON data
                   parseResponse(response);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    protected abstract void parseResponse(String response);


    private static void close(Closeable x) {
        try {
            if (x != null) {
                x.close();
            }
        } catch (IOException e) {
            Log.e("IOException Data", "Error occurred while closing stream");
        }
    }
}