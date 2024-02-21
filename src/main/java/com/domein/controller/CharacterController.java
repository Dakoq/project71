package com.domein.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.domein.api.NexonBasicAPI;
import com.domein.api.NexonNameAPI;
import com.domein.dto.SearchData;
import com.domein.util.DateUtility;
import com.domein.util.JsonParser;



@RestController
public class CharacterController {

    @Autowired
    private NexonNameAPI nameAPI;
    
    @Autowired
    private NexonBasicAPI basicAPI;
    
    // 검색 컴포넌트
    @PostMapping("/api/search")
    public ResponseEntity<?> search(@RequestBody SearchData searchData) {
        
        try {
            String characterName = searchData.getSearchTerm(); // 검색하고자 하는 캐릭터명
            String response = nameAPI.getCharacterInfo(characterName); // ocid 토큰 response
            System.out.println("토큰값: " + response);
            
            String ocid = JsonParser.parseOcid(response);
            System.out.println("ocid: " + ocid);
            
            if (ocid == null || ocid.isEmpty()) {
                throw new Exception("캐릭터 정보를 찾을 수 없습니다.");
            }
            
            DateUtility dateUtil = new DateUtility();
            String currentDate = dateUtil.getDateBasedOnInput(1, "");
            
            // 캐릭터 기본정보 조회
            String basicResponse = basicAPI.getCharacterInfo(ocid, currentDate);
            
            // 캐릭터 인기도 조회
            String popularityResponse = basicAPI.getCharacterPopularity(ocid, currentDate);
            
            // 캐릭터 종합 능력치 조회
            String finalStatResponse = basicAPI.getCharacterStat(ocid, currentDate);
            
            // 캐릭터 장비조회(캐쉬장비제외)
            String itemEquipmentResponse = basicAPI.getCharacterItemEquipment(ocid, currentDate);
            
            // JSON 객체로 결합
            JSONObject resultJson = new JSONObject();
            resultJson.put("basicInfo", new JSONObject(basicResponse));
            resultJson.put("popularityInfo", new JSONObject(popularityResponse));
            resultJson.put("finalStatInfo", new JSONObject(finalStatResponse));
            resultJson.put("itemEquipmentInfo", new JSONObject(itemEquipmentResponse));


            // 검색 결과를 ResponseEntity에 담아 반환합니다.
            return ResponseEntity.ok().body(resultJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 발생 시 클라이언트에게 적절한 에러 메시지와 함께 응답을 반환합니다.
            return ResponseEntity.badRequest().body(searchData.getSearchTerm()+" 검색 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    
    // 랭킹 컴포넌트
    @GetMapping("/api/ranking/{date}/{worldName}/{className}")
    public ResponseEntity<?> getRankingInfo(
    		@PathVariable("date") String date, 
            @PathVariable("worldName") String worldName, 
            @PathVariable("className") String className
            ) {
		    	// 캐릭터 이름을 저장할 리스트를 선언합니다.
		    	ArrayList<String> characterNames = new ArrayList<>();
		        ArrayList<String> ocids = new ArrayList<>();
		        ArrayList<String> basicResponses = new ArrayList<>();
		    	 try {
		    	        // Nexon API로부터 랭킹 정보를 불러오는 로직을 구현하세요.
		    	        // 정적 메소드 호출 방식으로 변경
			    		 String rankingResponse = NexonBasicAPI.getRankingInfo(date, worldName, className);
			    		 JSONObject jsonResponse = new JSONObject(rankingResponse);
			    		 JSONArray rankingArray = jsonResponse.getJSONArray("ranking");
			    		 
			    		// 랭킹 1등부터 3등까지의 캐릭터 이름을 배열에 저장
			    	        for (int i = 0; i < rankingArray.length() && i < 3; i++) {
			    	            JSONObject rankingEntry = rankingArray.getJSONObject(i);
			    	            String characterName = rankingEntry.getString("character_name");
			    	            characterNames.add(characterName);

			    	            // 각 캐릭터 이름으로 정보 조회
			    	            String response = nameAPI.getCharacterInfo(characterName);
			    	            String ocid = JsonParser.parseOcid(response);
			    	            if (ocid != null && !ocid.isEmpty()) {
			    	                ocids.add(ocid);
			    	            }
			    	        }
			    		 

			             DateUtility dateUtil = new DateUtility();
			             String currentDate = dateUtil.getDateBasedOnInput(1, "");
			             
			             // 랭킹 1~10위 기본정보 조회
			             for (String ocid : ocids) {
			            	 System.out.println("랭킹ocid : " + ocid);
			            	 System.out.println("랭킹조회날짜 : " + currentDate);
			                 String basicResponse = basicAPI.getCharacterInfo(ocid, currentDate);
			                 System.out.println("기본정보배열 : " + basicResponse);
			                 basicResponses.add(basicResponse);
			             }


//			             // 캐릭터 인기도 조회
//			             String popularityResponse = basicAPI.getCharacterPopularity(ocid_1, currentDate);
//			             // 캐릭터 종합 능력치 조회
//			             String finalStatResponse = basicAPI.getCharacterStat(ocid_1, currentDate);
//			             // 캐릭터 장비조회(캐쉬장비제외)
//			             String itemEquipmentResponse = basicAPI.getCharacterItemEquipment(ocid_1, currentDate);
			    		 
			          // JSON 객체로 변환하여 랭킹 정보를 담아 반환
			             JSONObject rankingJson = new JSONObject();
			             System.out.println(rankingJson);
			             rankingJson.put("ranking", new JSONArray(basicResponses));
			             return ResponseEntity.ok().body(rankingJson.toString());

			         } catch (Exception e) {
			             e.printStackTrace();
			             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			                     .body("랭킹 정보 조회 중 오류가 발생했습니다: " + e.getMessage());
			         }
		    	}
    
    

}