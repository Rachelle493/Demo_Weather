package sg.edu.rp.c346.id22036196.demoweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.*;

public class MainActivity extends AppCompatActivity {
ListView lvWeather;
AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvWeather=findViewById(R.id.lvWeather);
        client=new AsyncHttpClient();
    }
    @Override
    protected void onResume() {
        super.onResume();

        //creates new empty Arraylist of weather objects with the variable named alWeather
        ArrayList<Weather> alWeather = new ArrayList<Weather>();

        //connect it to the URL where the weataher data is located
        client.get("https://api.data.gov.sg/v1/environment/2-hour-weather-forecast", new JsonHttpResponseHandler() {
            String area;
            String forecast;
            @Override
            //represents the entire JSON object data that is being recieved
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    //used to reference this "items" array.
                    JSONArray jsonArrItems = response.getJSONArray("items");
                    //reference to the first object
                    JSONObject firstObj = jsonArrItems.getJSONObject(0);
                    JSONArray jsonArrForecasts = firstObj.getJSONArray("forecasts");
                    //for-loop to go through tht entire array and get the weather forecast...
                    //... JSON objcet that is at each position
                    for(int i = 0; i < jsonArrForecasts.length(); i++) {
                        JSONObject jsonObjForecast = jsonArrForecasts.getJSONObject(i);
                        area = jsonObjForecast.getString("area");
                        forecast = jsonObjForecast.getString("forecast");
                        Weather weather = new Weather(area, forecast);
                        alWeather.add(weather);
                    }
                }
                catch(JSONException e){
                }
                //POINT X – Code to display List View
                ArrayAdapter adpater = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, alWeather);
                lvWeather.setAdapter(adpater);

            }//end onSuccess
        });
    }//end onResume

}