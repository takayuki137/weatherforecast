package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;

    public void button(View view){
        DownloadTask task = new DownloadTask();
        String text = editText.getText().toString();
        Log.i("aaaaaaaa",text);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+text+"&appid=a7ed436f9ff15392aa322849c775f894";
        try {
            task.execute(url);
        }catch (Exception e){
            e.printStackTrace();
        }
        //https://api.openweathermap.org/data/2.5/weather?q=tokyo&appid=a7ed436f9ff15392aa322849c775f894


        Log.i("clickerd","clicked");

    }

    public class DownloadTask extends AsyncTask<String,Void,String> {
        String result = "";
        @Override
        //URL読み込み
        protected String doInBackground(String... urls) {

            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }//読み込み完了



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                String weatherInfo = jsonObject.getString("weather");

                Log.i("Weather content", weatherInfo);

                JSONArray arr = new JSONArray(weatherInfo);

                for (int i=0; i < arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);

                    textView.setText(jsonPart.getString("main"));
                    textView.setText(jsonPart.getString("description"));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.edit);
        textView=findViewById(R.id.textView2);




    }
}
