package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import criTest.Criteria;
import criTest.SearchCriteria;
import mapperInterface.BoardMapper;
import vo.BoardVO;

//** interface 자동완성 
//=> Alt + Shift + T  
//=> 또는 마우스우클릭 PopUp Menu 의  Refactor - Extract Interface...

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardMapper mapper;
	// ** Mybatis 적용 (interface 방식)
	// => interface BoardMapper 를 통해서 
	//    BoardMapper.xml 의 SQL구문 접근  
	
	// BoardDAO dao; => Mapper 로 교체
	// = -> @Autowired
	// new BoardDAO() -> @Repository 
	
	// ** Criteria PageList
	@Override // ver02
	public List<BoardVO> searchList(SearchCriteria cri) {
		return mapper.searchList(cri);
	}
	public int searchCount(SearchCriteria cri) {
		return mapper.searchCount(cri);
	}
	
	
	@Override // ver01
	public List<BoardVO> criList(Criteria cri) {
		return mapper.criList(cri);
	}
	public int criTotalCount() {
		return mapper.criTotalCount();
	}
	
	// ** selectList
	@Override
	public List<BoardVO> selectList() {
		return mapper.selectList();
	}
	// ** selectOne
	@Override
	public BoardVO selectOne(BoardVO vo) {
		return mapper.selectOne(vo);
	}
	// ** Insert
	@Override
	public int insert(BoardVO vo) {
		return mapper.insert(vo);
	}
	// ** Update
	@Override
	public int update(BoardVO vo) {
		return mapper.update(vo);
	}
	// ** Delete
	@Override
	public int delete(BoardVO vo) {
		return mapper.delete(vo);
	}
	// ** 조회수 증가
	@Override
	public int countUp(BoardVO vo) {
		return mapper.countUp(vo);
	}
	
	// ** 답글 등록
	@Override
	public int rinsert(BoardVO vo) {
		int result = mapper.rinsert(vo);
		if ( result>0 )
			System.out.println("** stepUpdate Count => "+ mapper.stepUpdate(vo));
		else result=0;
		return result;
	}

} //class
