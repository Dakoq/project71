package com.domein.util;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtility {

    public static String getDateBasedOnInput(int inputNumber, String customDate) {
        LocalDate date;

        switch (inputNumber) {
            case 1:
                // 현재 날짜에서 하루를 뺍니다.
                date = LocalDate.now().minusDays(1);
                break;
            case 14:
                // 사용자가 직접 입력한 날짜를 반환합니다.
                try {
                    // 입력된 날짜의 유효성을 검증합니다.
                    LocalDate.parse(customDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    return customDate;
                } catch (Exception e) {
                    return "잘못된 날짜 형식입니다.";
                }
            default:
                if (inputNumber >= 2 && inputNumber <= 13) {
                    // 입력받은 번호에 해당하는 달 전의 날짜를 계산합니다.
                    date = LocalDate.now().minusMonths(inputNumber - 1);
                } else {
                    // 잘못된 입력에 대한 처리입니다.
                    return "잘못된 입력입니다.";
                }
        }

        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

//    public static void main(String[] args) {
//        // 예시 사용법:
//        System.out.println(getDateBasedOnInput(1, "")); // 현재 날짜에서 하루 전
//        System.out.println(getDateBasedOnInput(2, "")); // 1달 전
//        System.out.println(getDateBasedOnInput(14, "2019-01-01")); // 사용자 정의 날짜
//        // ... 나머지 경우들에 대한 호출
//    }
}