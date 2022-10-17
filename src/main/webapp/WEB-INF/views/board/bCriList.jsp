<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>** Board Cri_PageList Spring_MVC2 **</title>
    <link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
    <script src="resources/myLib/jquery-3.2.1.min.js"></script>
    <script>
    // ** JS에서 함수사용
    // => 최상위 객체 window는 생략가능
    //     window.document.write("....");
    // => jQuery 호출
    //    - window.jQuery('선택자_id,class,Tag,..').click(function(){..........})
    //    - 늘 사용해야되는 jQuery 대신 $ 기호를 사용
    //      $('#searchBtn').click(function(){....
    // => ready 이벤트  
    //    - script 구문이 body보다 위쪽에 위치하면 Tag 인식이 불가능함
    //    - Body가 Tag들을 모두 load 한 후 실행 되도록 이벤트를 적용
    //    - js: window.onload
    //    - jQ: ready -> $(document).ready(function() {...});
    //                -> 이때 "(document).ready" 는 생략가능
    //                -> 아래의 $(function()){...}이 나오게 됨
    //
    
    
    
    $(function() { // $(function() { } ; -> ready 이벤트를 줄여쓴것
    	// 1) SearchType이 '전체' 면 Keyword 클리어
    	$('#searchType').change(function(){
    		if( $(this).val()=='n') $('#keyword').val(''); //매개변수에 '' 넣어서 클리어
    	}); //change
    	
        // 2) 검색 후 요청 
        // => 검색조건 입력 후 첫 Page 요청
        //    이때는 서버에 searchType, keyword 가 전달되기 이전이므로 makeQuery 메서드사용
        // => self.location="bcrilist?currPage=?????" : 해당 요청을 서버로 전달    
              
        // ** self.location   
        // 1) location 객체 직접사용 Test : url로 이동, 히스토리에 기록됨
        // 2) location 객체의 메서드
        // => href, replace('...'), reload() 
        
    	$('#searchBtn').click(function(){
    	    self.location="bcrilist"
    	        +"${pageMaker.makeQuery(1)}"
    	        +"&searchType="
    	        +$('#searchType').val()
    	        +"&keyword="
    	        +$('#keyword').val()
    	
        }); // searchBtn_click
    }) ; //ready
    </script>
</head>

<!-- *********************************************************************************** -->

<body>
<h2>** Board Cri_PageList Spring_MVC2 **</h2>
<br>
<c:if test="${not empty message}">
    ${message}<br>
</c:if>
<hr>
<div id="searchBar">
    <select name="searchType" id="searchType">
    <!-- <option value="n" selected> 을 조건 (cri.searchType 의 값) 에 따라 작성하기 위한 삼항식 
         => value="n" : ~~~~.cri.searchType==null ? 'selected':''  첫화면 출력시 초기값 으로 selected
      -->
        <option value="n" ${pageMaker.cri.searchType==null ? 'selected' : '' }>전체</option>
        <option value="t" ${pageMaker.cri.searchType=='t' ? 'selected' : '' }>Title</option>
        <option value="c" ${pageMaker.cri.searchType=='c' ? 'selected' : '' }>Content</option>
        <option value="i" ${pageMaker.cri.searchType=='i' ? 'selected' : '' }>ID(글쓴이)</option>
        <option value="r" ${pageMaker.cri.searchType=='r' ? 'selected' : '' }>RegDate</option>
        <option value="tc" ${pageMaker.cri.searchType=='tc' ? 'selected' : '' }>Title or Content</option>
        <option value="tci" ${pageMaker.cri.searchType=='tci' ? 'selected' : '' }>Title or Content or ID</option>
    </select>
    <input type="text" name="keyword" id="keyword" value="${pageMaker.cri.keyword}">
    <button id="searchBtn">search</button>
</div>
<br><hr>

<!-- **************************************************************************************************** -->

<table width=100%> 
	<tr bgcolor="Gold" height="30">
		<th>Seq</th><th>Title</th><th>I D</th><th>RegDate</th><th>조회수</th>
	</tr>
    <c:if test="${not empty banana}">
        <c:forEach var="board" items="${banana}">
        <tr height="30">
            <td>${board.seq}</td>
            
            <td><!-- 답글 등록후 indent 에 따른 들여쓰기 => 답글인 경우에만 적용  9.26일 추가-->
                <c:if test="${board.indent > 0}">
                    <c:forEach begin="1" end="${board.indent}">
                        <span>&nbsp;&nbsp;</span>
                    </c:forEach>
                    <span style="color:hotpink">re..</span>
                </c:if>
            
            <!-- 로그인 한 경우에만 title 클릭하면 content를 볼 수 있도록 함
                  => bdetail을 실행함 -->
            <c:if test="${not empty loginID}"> <!-- 로그인 했을때 -->
                <a href="bdetail?seq=${board.seq}">${board.title}</a>
            </c:if>
            <c:if test="${empty loginID}"> <!-- 로그인 하지 않았을때 -->
                ${board.title}
            </c:if>
            </td>
            
            <td><!-- admin일때만 ID를 누를 수 있게 하기 -->
            <c:if test="${loginID=='admin'}">
            <a href="mdetail?id=${board.id}">${board.id}</a>
            </c:if>
            <c:if test="${loginID!='admin'}">
                ${board.id}
            </c:if>
            </td>
            
            <td>${board.regdate}</td><td>${board.cnt}</td>
        </tr>
        </c:forEach>
	</c:if>
</table>
<hr>
<!-- Cri_Page -->
<div align="center">

    <!-- First, Prev -->
    <c:choose>
        <c:when test="${pageMaker.prev && pageMaker.spageNo>1}">
        <!-- New_ver01_Cri : pageMaker.makeQuery(1) -->
        <!-- New_ver02_SearchCri : pageMaker.searchQuery(1) -->
            <a href="bcrilist${pageMaker.searchQuery(1)}">FP</a>&nbsp;
            <a href="bcrilist${pageMaker.searchQuery(pageMaker.spageNo-1)}">&lt;</a>&nbsp;&nbsp;
            
            <!-- OLD_version
                 => EL 은 주석내에 있어도 JSP가 처리하면서 변수명 등에 오류있으면 500 발생할 수 있음.
            <a href="bcrilist?currPage=1&rowsPerPage=5">FP</a>&nbsp;
            <a href="bcrilist?currPage=${pageMaker.spageNo-1}&rowsPerPage=5">&lt;</a>&nbsp;&nbsp; 
            -->
        </c:when>
        <c:otherwise>
            <font color="Gray">FF&nbsp; &lt; &nbsp;</font>
        </c:otherwise>
    </c:choose>

<!-- Display PageNo -->
<c:forEach var="i" begin="${pageMaker.spageNo}" end="${pageMaker.epageNo}">
    <c:if test="${i==pageMaker.cri.currPage}">
        <font size="5" color="Orange">${i}</font>&nbsp;
    </c:if>
    <c:if test="${i!=pageMaker.cri.currPage}">
        <!-- New_ver01_Cri : pageMaker.makeQuery(1) -->
        <!-- New_ver02_SearchCri : pageMaker.searchQuery(1) -->
        <a href="bcrilist${pageMaker.searchQuery(i)}">${i}</a>&nbsp;
        
        <!-- OLD_version
        <a href="bcrilist?currPage=${i}&rowsPerPage=5">${i}</a>&nbsp; -->
    </c:if>
</c:forEach>

<!-- Next, Last -->
    <c:choose>
        <c:when test="${pageMaker.next && pageMaker.epageNo>0}">
        <!-- New_ver01_Cri : pageMaker.makeQuery(1) -->
        <!-- New_ver02_SearchCri : pageMaker.searchQuery(1) -->
            <a href="bcrilist${pageMaker.searchQuery(pageMaker.epageNo+1)}">&nbsp;&gt;</a>
            <a href="bcrilist${pageMaker.searchQuery(pageMaker.lastPageNo)}">&nbsp;LP</a>
            
            
            <!-- OLD_version
            <a href="bcrilist?currPage=${pageMaker.epageNo+1}&rowsPerPage=5">&gt;</a>&nbsp;&nbsp;
            <a href="bcrilist?currPage=${pageMaker.lastPageNo}&rowsPerPage=5">LP</a>&nbsp;  -->
        </c:when>
        <c:otherwise>
            <font color="Gray">&nbsp; &gt; &nbsp;LP</font>
        </c:otherwise>
    </c:choose>

</div>

<hr>
<c:if test="${not empty loginID}"> 
&nbsp;&nbsp;<a href="binsertf">새글등록</a>
</c:if>
&nbsp;&nbsp;<a href="javascript:history.go(-1)">이전으로</a>
&nbsp;&nbsp;<a href="home">[Home]</a>

</body>
</html>