package edu.kh.fit.admin.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Admin {
	
	private int adminNo;
	private String email;
	private String pw;
	private String nickname;
	private String delFl;
	
}
