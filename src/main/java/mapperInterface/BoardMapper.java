package mapperInterface;

import java.util.List;

import criTest.Criteria;
import criTest.SearchCriteria;
import vo.BoardVO;

public interface BoardMapper {
	
	// ** Criteria PageList
	// => ver02
	List<BoardVO> searchList(SearchCriteria cri);
	int searchCount(SearchCriteria cri);
	
	// => ver01
	List<BoardVO> criList(Criteria cri);
	int criTotalCount();
	
	// ** selectList
	List<BoardVO> selectList();
	// ** selectOne
	BoardVO selectOne(BoardVO vo);
	// ** Insert: 새글등록 
	int insert(BoardVO vo);
	// ** Update: 글수정
	int update(BoardVO vo);
	// ** Delete
	int delete(BoardVO vo);
	// ** 조회수 증가
	int countUp(BoardVO vo);
	
	// ** Reply Insert
	int stepUpdate(BoardVO vo);
	int rinsert(BoardVO vo);
	
} //interface
