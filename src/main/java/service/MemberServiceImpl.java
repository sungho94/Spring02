package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import criTest.SearchCriteria;
import mapperInterface.MemberMapper;
import vo.MemberVO;

//** Service
//=> 요청클래스 와 DAO클래스 사이의 연결(완충지대) 역할
//=> 요청클래스 와 DAO클래스 사이에서 변경사항이 생기더라도 서로 영향   받지않도록해주는 역할
//   결합도는 낮추고, 응집도는 높인다

//** interface 자동완성 
//=> Alt + Shift + T  
//=> 또는 마우스우클릭 PopUp Menu 의  Refactor - Extract Interface...

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberMapper mapper; // Mybatis 적용후
	// ** Mybatis interface 방식 적용
	// => MemberMapper 의 인스턴스를 스프링이 생성해주고 이를 주입받아 실행함
	//    단, 설정화일에 <mybatis-spring:scan base-package="mapperInterface"/> 반드시 추가해야함
	//    MemberDAO => mapperInterface 사용으로 MemberMapper 가 역할 대신함
	
//	MemberDAO dao;    // Mybatis 적용전
	
    // ** SearchCriteria PageList
    @Override
	public List<MemberVO> searchList(SearchCriteria cri) {
    	return mapper.searchList(cri);
    }
    @Override
	public int searchCount(SearchCriteria cri) {
        return mapper.searchCount(cri);
    }
//==============================================================    
    // ** selectList
    @Override
	public List<MemberVO> selectList() {
    	return mapper.selectList();
    }
    
    // ** selectOne
    @Override
	public MemberVO selectOne(MemberVO vo) {
    	return mapper.selectOne(vo);
    }
    
    // ** Insert
    @Override
	public int insert(MemberVO vo) {
    	return mapper.insert(vo);
    }
    
    // ** Update
    @Override
	public int update(MemberVO vo) {
    	return mapper.update(vo);
    }
    
    // ** Delete
    @Override
	public int delete(MemberVO vo) {
    	return mapper.delete(vo);
    }

}//class
