package vo;

import lombok.Data;

//** VO 작성
//=> private 변수 : Table의 컬럼명과 동일
//=> setter , getter , toString()

@Data
public class BoardVO {
	private int seq;
	private String id;
	private String title;
	private String content;
	private String regdate;
	private int cnt;
	private int root;
	private int step;
	private int indent;
	
} //class
