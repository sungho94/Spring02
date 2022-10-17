package mapperInterface;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import criTest.SearchCriteria;
import vo.MemberVO;

public interface MemberMapper {
	
	// ** JUnit Test
	int totalCount();
	// ** @으로 SQL 구문 처리(~Mapper.xml 필요없음)
	@Select("select * from member where id != 'admin'")
	List<MemberVO> selectList2();
	
	// ** SearchCriteria PageList
	List<MemberVO> searchList(SearchCriteria cri);
	int searchCount(SearchCriteria cri);
	
    // ** selectList
    List<MemberVO> selectList();
    
    // ** selectOne
    MemberVO selectOne(MemberVO vo);
    
    // Join => Insert
    int insert(MemberVO vo);
    
	// ** Update
    int update(MemberVO vo);
    
	// ** Delete
    int delete(MemberVO vo);

} //interface
