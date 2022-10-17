package criTest;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.ToString;

//** View 에 필요한 값을 완성
//=> Criteria 를 이용해서..

@Getter
@ToString
public class PageMaker {
	private int totalRowsCount; // 전체 Row 갯수 (전체 Page수 계산위해 필요)
	private int spageNo; // view에 표시할 첫 pageNo
	private int epageNo; // view에 표시할 끝 pageNo
	// ※ 주의 필드명이 ePage 처럼 두번쨰 알파벳이 대문자인 경우
	//    => setter, getter 는 setsPageNo , getsPageNo 형태로 만들어지기때문에
	//       Lombok.. 등등 사용시 불편 -> 그러므로 대.소문자 섞어사용시 주의.  
	
	private int displayPageNo=3; // 1 page당 표시할 pageNo 갯수
	private int lastPageNo; 
	// => 출력 가능한 마지막 PageNo (totalRowCount, rowsPerPage 로 계산)
	private boolean prev; // 이전 PageBlock 으로
	private boolean next; // 다음 PageBlock 으로
	
	//private Criteria cri; // ver01
	private SearchCriteria cri; // ver02
	
	// ** 필요값 set
	// 1) Criteria
	// Criteria -> ver01, SearchCriteria -> ver02
	public void setCri(SearchCriteria cri) {this.cri=cri;}
	
	// 2) totalRowCount
	public void setTotalRowsCount(int totalRowsCount) {
		this.totalRowsCount=totalRowsCount;
		calcData();
	}
	
	
	// 3) 위 외에 필요값 계산
	// => totalRowsCount 를 이용해서
	//    spageNo, epageNo, prev, next, lastPageNo 계산
	public void calcData() {
		// 3.1) currPage가 속한 페이지블럭의 epageNo 를 계산
	    // => n개씩 출력한다면 ePagNo 는 늘 n의 배수   
	    //    displayPageNo=3 이면 3, 6, 9, 12, ......
	    // => ex) 17 page 요청 , displayPageNo=3, 일때 17이 몇번째 그룹 인지 계산하려면,
	    //        17/3 -> 5 나머지 2 결론은 정수 나누기 후 나머지가 있으면 +1 이 필요함
	    //    -> Math.ceil(17/3) -> Math.ceil(5.73..) -> 6.0 -> 6번쨰 그룹 16,17,18
	    // 즉, 17이 몇번째 그룹 인지 계산하고, 여기에 * displayPageNo 를 하면 됨.   
	      
	    // ** Math.ceil(c) : 매개변수 c 보다 크면서 같은 가장 작은 정수를 double 형태로 반환 
	    //                ceil -> 천장   ,  예) 11/3=3.666..  -> 4
	    // => Math.ceil(12.345) => 13.0
		epageNo = (int)Math.ceil(cri.getCurrPage()/(double)displayPageNo) * displayPageNo;
	    spageNo = (epageNo-displayPageNo) + 1 ;
		
		
		// 3.2) lastPageNo 계산, epageNo 확인
	    lastPageNo=(int)Math.ceil(totalRowsCount/(double)cri.getRowsPerPage()) ;
	    if ( epageNo>lastPageNo ) epageNo=lastPageNo ;
	    
		// 3.3) prev, next
	    prev = spageNo==1 ? false : true;
	    next = epageNo==lastPageNo ? false : true;
		
	} //calcData
	
	// 4) QueryString 만들기
	// ** 패키지 org.springframework.web.util
	// => 웹개발에 필요한 많은 유틸리티 클래스 제공
	// => UriComponents , UriComponentsBuilder
	//    Uri를 동적으로 생성해주는 클래스,
	//    파라미터가 조합된 uri를 손쉽게 만들어줌
	// => ?currPage=7&rowsPerPage=10 이것을 만들어줌
	//    ? 부터 만들어지므로 jsp Code에서 ? 포함하지 않도록 주의    
	   
	// ** ver01
	// => QueryString 자동생성 
	//    ?currPage=4&rowsPerPage=3 
	public String makeQuery(int currPage) {
		UriComponents uriComponents = 
				UriComponentsBuilder.newInstance().
				queryParam("currPage", currPage).
				queryParam("rowsPerPage", cri.getRowsPerPage()).
				build();
		return uriComponents.toString(); //그냥 uriComponents하면 타입이 안맞는다고함, toString()붙이기		
				// 물음표는 자동으로 붙여줌, 이름을 모르니까 currPage 넣어줌
				// currPage=${pageMaker.spageNo-1}가 자동으로 만들어진다고함
		        // 호출하면서 매개변수만 잘 넣어주면 된다~
	} //makeQuery
	
	// ** ver02
	// => uri 에 search 기능 추가 (Paging 시에도 조건이 유지되도록)
	// => ?curPage=1&rowsPerPage=3&searchType=t&keyword=Java
	public String searchQuery(int currPage) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance().
				queryParam("currPage", currPage).
				queryParam("rowsPerPage", cri.getRowsPerPage()).
				queryParam("searchType", cri.getSearchType()).
				queryParam("keyword", cri.getKeyword()).
				build();
		return uriComponents.toString();		
	} //searchQuery
	

} // class
