package com.domein.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

@Component
public class NexonBasicAPI {

    private static final String API_KEY = "test_85caed11944d9a85d870a25f0ce78b62bdffd0203fd5d656b7ffac37c61d2693c1a0eae2a13b1c8b5bf8e5c63ca98838"; // 클래스 변수로 API 키를 선언

    // 캐릭터 기본정보
    public static String getCharacterInfo(String ocid, String date) {
        StringBuffer response = new StringBuffer();
        try {
            String encodedOcid = URLEncoder.encode(ocid, StandardCharsets.UTF_8);
            String encodedDate = URLEncoder.encode(date, StandardCharsets.UTF_8);
            String urlString = "https://open.api.nexon.com/maplestory/v1/character/basic?ocid=" + encodedOcid + "&date=" + encodedDate;
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
            // 로깅 또는 오류 메시지 강화
            e.printStackTrace();
            return "API 요청 중 오류가 발생했습니다: " + e.toString();
        }
        return response.toString();
    }
    
    // 캐릭터 인기도 
    public String getCharacterPopularity(String ocid, String date) {
        StringBuffer response = new StringBuffer();
        try {
        	String encodedOcid = URLEncoder.encode(ocid, StandardCharsets.UTF_8);
            String encodedDate = URLEncoder.encode(date, StandardCharsets.UTF_8);
            String urlString = "https://open.api.nexon.com/maplestory/v1/character/popularity?ocid=" + encodedOcid + "&date=" + encodedDate;
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
            e.printStackTrace();
            return "인기도 정보 조회 중 오류가 발생했습니다: " + e.toString();
        }
        return response.toString();
    }
    
    // 캐릭터 종합 능력치 
	public String getCharacterStat(String ocid, String date) {
		
      StringBuffer response = new StringBuffer();
        try {
        	String encodedOcid = URLEncoder.encode(ocid, StandardCharsets.UTF_8);
            String encodedDate = URLEncoder.encode(date, StandardCharsets.UTF_8);
            String urlString = "https://open.api.nexon.com/maplestory/v1/character/stat?ocid=" + encodedOcid + "&date=" + encodedDate;
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
            e.printStackTrace();
            return "캐릭터 종합 능력치 정보 조회 중 오류가 발생했습니다: " + e.toString();
        }
        return response.toString();
    }
	// 캐릭터 장비 조회(캐쉬장비 제외)
	public String getCharacterItemEquipment(String ocid, String date) {
		StringBuffer response = new StringBuffer();
        try {
        	String encodedOcid = URLEncoder.encode(ocid, StandardCharsets.UTF_8);
            String encodedDate = URLEncoder.encode(date, StandardCharsets.UTF_8);
            String urlString = "https://open.api.nexon.com/maplestory/v1/character/item-equipment?ocid=" + encodedOcid + "&date=" + encodedDate;
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
            e.printStackTrace();
            return "캐릭터 장비 조회 중 오류가 발생했습니다: " + e.toString();
        }
        return response.toString();
    }
	

	// 랭킹 정보 조회
	public static String getRankingInfo(String date, String worldName, String className) {
	    StringBuffer response = new StringBuffer();
	    try {
	        String encodedDate = URLEncoder.encode(date, StandardCharsets.UTF_8);
	        String encodedWorldName = URLEncoder.encode(worldName, StandardCharsets.UTF_8);
	        String encodedClassName = URLEncoder.encode(className, StandardCharsets.UTF_8);
	        
	        // 랭킹 정보를 가져오는 API URL 구성
	        String urlString = "https://open.api.nexon.com/maplestory/v1/ranking/overall"
	            + "?date=" + encodedDate
	            + "&world_name=" + encodedWorldName
	            + "&class=" + encodedClassName;
	        
	        URL url = new URL(urlString);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setRequestProperty("x-nxopen-api-key", API_KEY);

	        // 서버로부터의 응답 코드 확인
	        int responseCode = connection.getResponseCode();

	        BufferedReader in;
	        if (responseCode == 200) { // 성공적인 응답
	            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        } else { // 오류 응답 처리
	            in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
	        }

	        // 응답 데이터 읽기
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "랭킹 정보를 가져오는 중 오류가 발생했습니다: " + e.toString();
	    }
	    return response.toString();
	}

	

}