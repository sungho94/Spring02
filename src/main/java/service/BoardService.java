package service;

import java.util.List;

import criTest.Criteria;
import criTest.SearchCriteria;
import vo.BoardVO;

public interface BoardService {
	
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

	// ** Insert
	int insert(BoardVO vo);

	// ** Update
	int update(BoardVO vo);

	// ** Delete
	int delete(BoardVO vo);

	// ** 조회수증가
	int countUp(BoardVO vo);

	// ** 답글 등록
	int rinsert(BoardVO vo);

}