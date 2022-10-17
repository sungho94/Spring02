package com.ncs.green;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import criTest.PageMaker;
import criTest.SearchCriteria;
import service.MemberService;
import vo.MemberVO;


// ** Bean 생성하는 @
// Java : @Component
// Spring 세분화 됨
// => @Controller,  @Service,  @Repository
//===================================================================================
//** Spring 의 redirect ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//=> mv.addObject 에 보관한 값들을 퀴리스트링의 parameter로 붙여 전달해줌 
// 그러므로 전달하려는 값들을 mv.addObject 로 처리하면 편리.
// 단, 브라우져의 주소창에 보여짐.

//** RedirectAttributes
//=> Redirect 할 때 파라메터를 쉽게 전달할 수 있도록 지원하며,
// addAttribute, addFlashAttribute, getFlashAttribute 등의 메서드가 제공됨.
//=> addAttribute
//   - url에 퀴리스트링으로 파라메터가 붙어 전달됨. 
//    - 그렇기 때문에 전달된 페이지에서 파라메터가 노출됨.
//=> addFlashAttribute
//   - Redirect 동작이 수행되기 전에 Session에 값이 저장되고 전달 후 소멸된다.
//    - Session을 선언해서 집어넣고 사용 후 지워주는 수고를 덜어주고,
//   - url에 퀴리스트링으로 붙지 않기때문에 깨끗하고 f5(새로고침)에 영향을 주지않음.  
//   - 주의사항 
//     받는쪽 매핑메서드의 매개변수로 parameter 를 전달받는 VO가 정의되어 있으면
//     이 VO 생성과 관련된 500 발생 하므로 주의한다.
//     ( Test : JoController 의 jupdate 성공시 redirect:jdetail )
//     단, VO로 받지 않는 경우에는 url에 붙여 전달하면서 addFlashAttribute 사용가능함        

//=> getFlashAttribute
//   - insert 성공 후 redirect:jlist 에서 Test (JoController, 결과는 null)
//   - 컨트롤러에서 addFlashAttribute 가 session에 보관한 값을 꺼내는것은 좀더 확인이 필요함 

//** redirect 로 한글 parameter 전달시 한글깨짐
//=> 한글깨짐이 발생하는경우 사용함.
//=> url 파라메터 로 전달되는 한글값 을 위한 encoding
//   - String message = URLEncoder.encode("~~ member 가 없네용 ~~", "UTF-8");
//     mv.setViewName("redirect:mlist?message="+message);  
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

@Controller
public class MemberController {
	@Autowired
	MemberService service;
	
	// ** Criteria PageList
	// => ver02 : SearchCriteria cri
	@RequestMapping(value="/mcrilist")
	public ModelAndView mcrilist(HttpServletRequest request, 
			HttpServletResponse response, ModelAndView mv, SearchCriteria cri, PageMaker pageMaker) {
		// 1) Criteria 처리
        cri.setSnoEno();		
		
	    // 2) 서비스처리
	    // => List 처리
	    mv.addObject("banana", service.searchList(cri)); // ver02
    	
    	// 3) View 처리 => PageMaker
	    pageMaker.setCri(cri);
	    pageMaker.setTotalRowsCount(service.searchCount(cri)); // ver02 : 조건과 일치하는 Rows 갯수
	    mv.addObject("pageMaker",pageMaker);
	    
    	mv.setViewName("/member/mCriList");
    	return mv;
	}//mcrilist
	
	// ** MemberList
	@RequestMapping(value="/mlist")
	public ModelAndView mlist(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		// ** MemberList
		
		List<MemberVO> list = new ArrayList<MemberVO>();
    	list = service.selectList();
    	if ( list!=null ) {
    		mv.addObject("banana", list);  // request.setAttribute(...) 와 동일효과
    	}else {
    		mv.addObject("message", "~~ 출력 자료가 없습니다 ~~");
    	}
    	mv.setViewName("/member/memberList");
    	return mv;
	}
	
	// ** MemberDetail
	@RequestMapping(value="/mdetail")
	public ModelAndView mdetail(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, MemberVO vo) {
		// => Mapping 메서드 : 매개변수로 지정된 객체에 request_ParameterName 과 일치하는 컬럼(setter)존재하면 자동으로 set
		//    vo.setId(request.getParameter("id")  필요없게됨.
		
		// 1. 요청분석
		// => 요청구분 (요청에 따라 id 가져오기)
		//    로그인 후 내정보 보기 : session.getAttr~~
		//    관리자가 memberList에서 선택 : getParameter...)
		
		if (vo.getId()==null || vo.getId().length()<1) {
			// => parameter id의 값이 없으면 session에서 가져온다.
			HttpSession session = request.getSession(false);
			if ( session!=null && session.getAttribute("loginID") !=null ) 
				vo.setId((String)session.getAttribute("loginID")) ;
			else {
				request.setAttribute("message", "=> 출력할 ID가 없음, 로그인후 이용하세요 ~~"); //session이 없다
			}
		} // getParameter_else
		
		String uri = "/member/memberDetail";
		
		// 2. Service 처리
		// => Service 에서 selectOne
		vo=service.selectOne(vo);
		if (vo!=null) {
			mv.addObject("apple", vo);
			// ** Update 요청이면 updateForm.jsp로 나가야한다.
			if ("U".equals(request.getParameter("jCode")))
				uri ="/member/updateForm";    
		}else { // 없는 ID
			mv.addObject("message", "~~"+request.getParameter("id")+"님의 자료는 존재하지 않습니다 ~~");
		}
    	mv.setViewName(uri);
    	return mv;
	}
	
	// ** Login & Logout
	@RequestMapping(value="/loginf")
	public ModelAndView loginf(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		mv.setViewName("/member/loginForm");
		return mv;
	}

	@RequestMapping(value="/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		// 1) request 처리
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		MemberVO vo = new MemberVO();
		String uri = "/member/loginForm";
		
		// 2) service 처리
		vo.setId(id);
		vo = service.selectOne(vo);
		if ( vo != null ) { 
			// ID 는 일치 -> Password 확인
			if ( vo.getPassword().equals(password) ) {
				// Login 성공 -> login 정보 session에 보관, home
				request.getSession().setAttribute("loginID", id);
				request.getSession().setAttribute("loginName", vo.getName());
				uri="home" ;
			}else {
				// Password 오류
				mv.addObject("message", "~~ Password 오류,  다시 하세요 ~~");
			}
		}else {	// ID 오류
			mv.addObject("message", "~~ ID 오류,  다시 하세요 ~~");
		} //else
		mv.setViewName(uri);
		return mv;
	} 
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		// ** session 인스턴스 정의 후 삭제하기
    	// => 매개변수: 없거나, true, false
    	// => false : session 이 없을때 null 을 return;
		HttpSession session = request.getSession(false);
    	if (session!=null) session.invalidate();
    	mv.addObject("message", "~~ 로그아웃 되었습니다 ~~"); 
    	mv.setViewName("home");
		return mv;
	}
	
	// ** Join : 회원가입
	@RequestMapping(value="/joinf")
	public ModelAndView joinf(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		mv.setViewName("/member/joinForm");
		return mv;
	} //Join
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	// => 매핑네임과 method 가 일치하는 요청만 처리함
	public ModelAndView join(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, MemberVO vo) {
		// 1. 요청분석
    	// => Service 준비
		// => request, 한글처리
		// => Spring : 한글(web.xml , Filter) , request (매핑메서드의 매개변수선언으로 처리)
		// => Join 성공 : 로그인유도 loginForm
		//         실패 : joinForm    
		String uri = "/member/loginForm";
		
		// 2. Service 처리
		if ( service.insert(vo)>0 ) {
			mv.addObject("message", "~~ 회원가입 성공, 로그인후 이용하세요 ~~");
		}else {
			mv.addObject("message", "~~ 회원가입 실패, 다시 하세요 ~~");
			uri = "/member/joinForm";
		}
		
		// 3. 결과(ModelAndView) 전달 
		mv.setViewName(uri);
		return mv;
	} //
	
	//** Update : 내정보 수정하기
    @RequestMapping(value="/mupdate", method = RequestMethod.POST)
	public ModelAndView mupdate(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, MemberVO vo) {
	    // 1. 요청분석
    	// => Update 성공 : 내정보 표시 -> memberDetail.jsp
    	//           실패 : 재수정 유도 -> updateForm.jsp
    	String uri = "/member/memberDetail";
    	mv.addObject("apple",vo);
    	// => Update 성공.실패 모두 출력시 필요하므로
    	
    	// 2. Service 처리
    	if (service.update(vo)>0) {
    		mv.addObject("message", "~~ 회원정보 수정 성공 ~~");
    	} else {
    		mv.addObject("message", "~~ 회원정보 수정 실패, 다시 하세요 ~~");
    		uri = "/member/updateForm";
    	}
    	
		mv.setViewName(uri);
		return mv;
    	
	} //Update
    
    //** Delete : 회원탈퇴
    @RequestMapping(value="/mdelete")
	public ModelAndView mdelete(HttpServletRequest request, HttpServletResponse response, 
			ModelAndView mv, MemberVO vo, RedirectAttributes rttr) {
	    // 1. 요청분석
        // => Delete 성공 : session 무효화, home.jsp
        //           실패 : message, home.jsp 
        // => 삭제대상 확인 : LoginID or 관리자가 삭제하는 경우 (request.getParameter)
    	
        String id = null;
        HttpSession session = request.getSession(false);
        // => 메서드내에서 session을 사용가능 하도록 정의
        //    삭제성공 후 session 무효화 하기위함.
        if ( session!=null && session.getAttribute("loginID") !=null ) {
    	    // ** 삭제가능
    	    id = (String)session.getAttribute("loginID") ;
    	    // => 본인 탈퇴 : loginID 이용한 삭제
    	    //    그러나 id는 관리자가 아니어야함.
    	    //    관리자 작업인경우 : 이미 vo에 삭제할 ID가 set 되어 있음
    	    //    관리자 작업이아닌경우 : session에서 get한 ID를 vo에 set
            if (!"admin".equals(id) ) vo.setId(id) ;

    	    // 2. Service 처리
    	    if (service.delete(vo)>0) {
    		    //성공:session 무효화, home.jsp
    		    //mv.addObject("message", "~~ 회원탈퇴 성공 ~~");
    		    rttr.addFlashAttribute("message", "~~ 회원탈퇴 성공, 1개월 후 재가입 가능합니다 ~~");
    		
    		    //본인이 탈퇴하는 경우에만 session 무효화
    	        if (!"admin".equals(id)) session.invalidate();
    	    } else {
    		    //mv.addObject("message", "~~ 회원탈퇴 실패, 다시 하세요 ~~");
    	        rttr.addFlashAttribute("message", "~~ 회원탈퇴 실패, 다시 하세요 ~~");
    	    }//service
    	
        }else {
    	    // ** session 정보가 없어 삭제가 불가능한 경우
    	    // => session is null
    	    rttr.addFlashAttribute("message", "~~ 삭제할 ID가 없음, 로그인후 이용하세요 ~~");
        } //session 확인_if
    	
    	
    	
    	// 3. 결과(ModelAndView) 전달
    	// => 성공 & 실패
    	//    redirect home 이 바람직 (단, message 처리 주의)
    	//    웹브라우저 주소창의 주소가 home이 표시 되도록
		mv.setViewName("redirect:home");
		return mv;
    	
	} //Update


} //class
