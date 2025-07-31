package service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Wheater;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.Set;

public class WheaterService {

    private final String apiKey="https://api.weatherstack.com/current?access_key=45d5ec3925886f0fb7ccf92b5224c361&query=";

    public Optional<Wheater> findWheater(String city){ //retorna un optional para evitar null

        try {
            URL url=new URL(apiKey.concat(city));
            HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){

                StringBuilder apiOutput=new StringBuilder();
                String line;
                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((line= reader.readLine())!=null){
                    apiOutput.append(line);
                }
                reader.close();
                JsonObject wheaterFound= JsonParser.parseString(apiOutput.toString()).getAsJsonObject();
                JsonObject location=wheaterFound.get("location").getAsJsonObject();
                String cityName=location.get("name").getAsString();
                String country=location.get("country").getAsString();
                JsonObject current=wheaterFound.get("current").getAsJsonObject();
                String temperature=current.get("temperature").getAsString();
                String humidity=current.get("humidity").getAsString();
                JsonElement descrptionElement=current.get("weather_descriptions");
                String descrption= descrptionElement.getAsString();

                Wheater wheater = new Wheater(cityName,country,humidity,descrption,temperature);

                return Optional.of(wheater);

            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
