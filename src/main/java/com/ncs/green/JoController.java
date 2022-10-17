package com.ncs.green;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import criTest.SearchCriteria;
import service.JoService;
import service.MemberService;
import vo.JoVO;

@Controller
public class JoController {

	@Autowired
	JoService service;
	
	@Autowired
	MemberService mservice;
	
	@RequestMapping(value = "jlist")
	public ModelAndView jlist(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv, RedirectAttributes rttr) {
		// ** RedirectAttributes 의 addFlashAttribute 로 전달된 값 확인
		// => insert 에서 : rttr.addFlashAttribute("mytest", "addFlashAttribute 메서드 Test");
		// => 좀더 확인 필요함
		System.out.println("************ Test1 => " + rttr.getFlashAttributes());
		System.out.println("************ Test2 => " + request.getSession().getAttribute("mytest"));
		
		
        List<JoVO> list = new ArrayList<JoVO>();
        list = service.selectList();
		
        if (list!=null) {
        	mv.addObject("banana", list);
        } else {
        	mv.addObject("message", "~~ 출력 자료가 없습니다 ~~");
        }
	    
    	mv.setViewName("/jo/joList");
    	return mv;
	
	} //joList
	
	// ** JoDetail
	// => 아랫쪽에 조원목록 출력
	// => memjo Table에서 selectOne  ->  orange 
	// => member Table에서 조건검색 jno=#{jno}  ->  banana 
	@RequestMapping(value = "/jdetail", method=RequestMethod.GET)
	public ModelAndView jdetail(HttpServletRequest request, HttpServletResponse response,
			ModelAndView mv, JoVO vo, SearchCriteria cri) {
	
	    // ** 수정 성공후 redirect 요청으로 전달된 경우 message 처리
	    if ( request.getParameter("message")!=null &&  request.getParameter("message").length() > 0 )
	       mv.addObject("message", request.getParameter("message")) ;
	    System.out.println("***** jno 전달확인 => "+vo.getJno());
		
		// 1. 요청분석
		String uri = "/jo/joDetail";
		
		// 2. Service 처리
		vo=service.selectOne(vo);
		
    	if ( vo!=null ) {
            // 2.1) 수정요청 확인
    		if ("U".equals(request.getParameter("jCode")))
    			uri = "/jo/jupdateForm";
    	    else {
    		    // 조원목록 읽어오기
    	    	// => 조별로 조회가 가능한 searchList 메서드를 활용함
                // 1 Page 만 있으면 되므로 기본값을 지정함. 
    	  		// 단, RowsPerPage 는 현재 Paging 은 하지 않기때문에 큰값을 지정함. 
    	  				
    	    	cri.setRowsPerPage(30); // 현재 Paging 은 하지 않기때문에 큰값을 지정
    	    	
    	    	cri.setCurrPage(1);
    	    	cri.setSnoEno();
    	    	cri.setKeyword(Integer.toString(vo.getJno()));
    	    	cri.setSearchType("j"); //mapper.xml의 keyVal 중 j
    	    	mv.addObject("banana",mservice.searchList(cri));
    	    	
    	    	
    	    }
    	
    	    // 2.2) 결과전달
    	    mv.addObject("apple", vo);
    	} else mv.addObject("message", "~~ 조번호에 해당하는 자료가 없습니다. ~~");
    	
    	mv.setViewName(uri);
    	return mv;
    	
	} //jodetail

//================================================================================	

	// ** Update : 조 수정하기
	@RequestMapping(value = "/jupdate", method=RequestMethod.POST)
	public ModelAndView jupdate(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, JoVO vo) {
		// 1. 요청분석
		// => Update 성공: joDetail.jsp
	    //           실패: 재수정 유도 -> jupdateForm.jsp
		
		String uri = "redirect:jdetail"; //ver02
		
		// ** Spring의 redirect
		// => mv.addObject에 보관한 값들을 퀴리스트링의 parameter로 붙여 전달해줌
		//    그러므로 전달하려는 값들을 mv.addObject로 처리하면 편리.
		//    단, 브라우저의 주소창에 보여진다는 단점이 있음.
		
		// String uri = "redirect:joDetail?jno="+vo.getJno(); //ver01
		// 단, 위 처럼 redirect 에서 parameter를 사용하여 전달하면서 RedirectAttributes rttr 사용시 오류 발생
		// jdetail 메서드의 매개변수에서 vo로 전달된 prameter 를 받는경우에 오류 발생함
		// vo로 받지 않는경우에는 퀴리스트링으로 전달하면서 RedirectAttributes rttr 은 사용가능함.
		
		// ** RedirectAttributes : Redirect 할 때 파라메터를 쉽게 전달할 수 있도록 지원함.
		// => addAttribute : URL에 파라메터가 붙어 전달되게 된다. 
		//    				 그렇기 때문에 전달된 페이지에서 파라메터가 노출됨.
		// => addFlashAttribute : Redirect 동작이 수행되기 전에 Session에 값이 저장되고 전달 후 소멸된다.
		//    					  Session을 선언해서 집어넣고 사용 후 지워주는 수고를 덜어준다.
		//                        (insert 성공 후 redirect:jlist에서 Test)                          
		 	
		mv.addObject("apple", vo);
		// => Update 성공/실패 모두 출력시 필요하므로
		
		// 2. Service 처리
		
    	if (service.update(vo)>0) {
    		//rttr.addFlashAttribute("message", "~~조 수정 성공~~");
    		mv.addObject("jno", vo.getJno());
    		mv.addObject("jname", vo.getJname());
    		mv.addObject("message", "~~ 조 수정 성공 ~~");
    		
    	} else {
    		mv.addObject("message", "~~ 조 수정 실패, 다시 수정해주세요 ~~");
    	    uri = "/jo/jupdateForm";
    	}
    	// 3. 결과(ModelAndView) 전달
    	mv.setViewName(uri);
    	return mv;
	} //jupdate
	
//==================================================================================
	
	// ** Insert : 조 추가하기
	@RequestMapping(value="/jinsertf")
	public ModelAndView binsertf(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		mv.setViewName("/jo/jinsertForm");
		return mv;
	}
	
	@RequestMapping(value="/jinsert", method=RequestMethod.POST)
	public ModelAndView jinsert(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv, JoVO vo, RedirectAttributes rttr) {
		// 1) 요청분석
		// => insert 성공 : jlist (redirect 요청, message 전달)
		//    insert 실패 : jinsertForm.jsp  
		String uri = "redirect:jlist";
		
		// 2. Service 처리
		
		if (service.insert(vo)>0) {
			rttr.addFlashAttribute("message", "~~ 새 조 등록 성공 ~~");
			rttr.addFlashAttribute("mytest", "addFlashAttribute 메서드 Test");
		} else {
			mv.addObject("message", "~~ 새 조 등록 실패, 다시 하세요 ~~");
		    uri = "/jo/jinsertForm";
	    }
		
		// 3. 결과(ModelAndView) 전달
		mv.setViewName(uri);
		return mv;
		
	} // jinsert
	
	// ** Delete : 조 삭제하기
	@RequestMapping(value="/jdelete")
	public ModelAndView jdelete(HttpServletRequest request, HttpServletResponse response, 
			ModelAndView mv, JoVO vo, RedirectAttributes rttr) {
		// 1. 요청분석
		// => Delete 성공: redirect:jlist
		//           실패: message 표시, redirect:jdetail
		String uri = "redirect:jlist";
		
		// 2. Service 처리
		if (service.delete(vo)>0) {
			rttr.addFlashAttribute("message", "~~ 조 삭제 성공 ~~");
		} else {
			rttr.addFlashAttribute("message", "~~ 조 삭제 실패, 다시 하세요 ~~");
		    uri = "redirect:jdetail?jno="+vo.getJno();
	    }
		
		// 3. 결과(ModelAndView) 전달
		mv.setViewName(uri);
		return mv;
	} //jdelete
	
	
}//class
