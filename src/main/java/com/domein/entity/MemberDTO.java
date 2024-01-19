package com.domein.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class MemberDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mb_seq;// 회원일련번호
	//@Column(name = "member_id", nullable = false, length = 50)
	@Column(nullable = false, length = 100)
	private String mb_id;					// 아이디
	
	@Column(nullable = false, length = 50)
	private String mb_nm;					// 이름
	
	@Column(nullable = false)
	private String mb_type;					// 가입유형 카카오, 사이트회원
	
	@Column(nullable = false)
	private String mb_stat;					// 유저구분 NORMAL(일반유저), WARNING(정책위반유저), STOP(정지유저)
	
	@Column(nullable = false, length = 100)
	private String mb_pwd;					// 비밀번호
	
	@Column(nullable = false)
	private String mb_gender;  				// 성별
	
	@Column(nullable = false, length = 8)
	private LocalDate mb_birth;				// 생년월일
	
	@Column(nullable = false, length = 20) 
	private String mb_phone;				// 휴대전화
	
	@Column(nullable = false, length = 100)
	private String mb_email;				// 아이디, 비밀번호 찾을때 사용할 이메일(기본값:아이디)
	
	@Column(nullable = false)
	private String mb_svc_ag;				// 서비스약관동의 Y N
	
	@Column(nullable = false)
	private String mb_info_ag;				// 개인정보처리동의 Y N
	
	@Column(nullable = false)
	private String mb_user_yn;				// 사용여부 Y N (접속하고 있는지 확인 하는 용도)
	
	@Column(nullable = false, length = 255)
	private String mb_itrs;					// 관심분야 설정(거래, 코디, 사냥, 보스)
	
	@Column(nullable = false)
	private LocalDateTime mb_start_date;			// 가입날짜
	
	@Column(nullable = false)
	private LocalDateTime mb_chg_date;				// 정보수정 날짜
	
	private int mb_char;					// 메이플 캐릭터
		
	
}
