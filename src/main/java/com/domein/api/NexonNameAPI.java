package com.domein.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

@Component
public class NexonNameAPI {

    private static final String API_KEY = "test_85caed11944d9a85d870a25f0ce78b62bdffd0203fd5d656b7ffac37c61d2693c1a0eae2a13b1c8b5bf8e5c63ca98838"; // 클래스 변수로 API 키를 선언
    
    public static String getCharacterInfo(String characterName) {
        StringBuffer response = new StringBuffer();
        try {
            String encodedCharacterName = URLEncoder.encode(characterName, StandardCharsets.UTF_8);
            String urlString = "https://open.api.nexon.com/maplestory/v1/id?character_name=" + encodedCharacterName;
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-nxopen-api-key", API_KEY);

            int responseCode = connection.getResponseCode();

            BufferedReader in;
            if (responseCode == 200) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            return "API 요청 중 오류가 발생했습니다: " + e.getMessage();
        }
        return response.toString();
    }
}