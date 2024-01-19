package com.domein.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.domein.api.NexonBasicAPI;
import com.domein.api.NexonNameAPI;
import com.domein.entity.SearchData;
import com.domein.util.DateUtility;
import com.domein.util.JsonParser;


@RestController
public class HomeController {

    @Autowired
    private NexonNameAPI nameAPI;
    private NexonBasicAPI basicAPI;
    
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
            String basicResponse = basicAPI.getCharacterInfo(ocid, currentDate);
            
            // 검색 결과를 ResponseEntity에 담아 반환합니다.
            System.out.println("보통정보 : " + basicResponse);
            return ResponseEntity.ok().body(basicResponse);
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 발생 시 클라이언트에게 적절한 에러 메시지와 함께 응답을 반환합니다.
            return ResponseEntity.badRequest().body(searchData.getSearchTerm()+"검색 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

}