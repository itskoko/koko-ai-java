package com.github.itskoko;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.Gson;

class KokoErrorResponse {
  public String[] errors;
}

class KokoFlagResponse {

}

class KokoModerationResponse {

}

class KokoContentResponse {
  public KokoClassifiers classifiers;
}

class KokoClassifiers {
  public String id;
  public List<KokoClassification> classification;
}

class KokoClassification {
  public String label;
  public Double confidence;
}

public class Koko {
  private static final String ENDPOINT = "https://api.koko.ai";
  public static String auth;

  public static KokoContentResponse trackContent(Map options) {
    String json = Koko.request("/track/content", options);
    Gson gson = new Gson();
    return gson.fromJson(json, KokoContentResponse.class);
  }

  public static KokoFlagResponse trackFlag(Map options) {
    String json = Koko.request("/track/flag", options);
    Gson gson = new Gson();
    return gson.fromJson(json, KokoFlagResponse.class);
  }

  public static KokoModerationResponse trackModeration(Map options) {
    String json = Koko.request("/track/moderation", options);
    Gson gson = new Gson();
    return gson.fromJson(json, KokoModerationResponse.class);
  }

  public static String request(String path, Map options) {
    try {
      URL url = new URL(ENDPOINT + path);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Authorization", Koko.auth);

      Gson gson = new Gson();
      String input = gson.toJson(options);
      OutputStream os = conn.getOutputStream();
      os.write(input.getBytes());
      os.flush();

      boolean isError = conn.getResponseCode() != 200;
      InputStream is = isError ? conn.getErrorStream() : conn.getInputStream();
      String output = new BufferedReader(new InputStreamReader(is))
        .lines().collect(Collectors.joining("\n"));

      conn.disconnect();

      if (isError) {
        KokoErrorResponse response = gson.fromJson(output, KokoErrorResponse.class);
        String message = String.join("\n", response.errors);
        throw new RuntimeException(message);
      }

      return output;
    }

    catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    }

    catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
