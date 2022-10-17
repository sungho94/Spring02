package mapperInterface;

import java.util.List;

import vo.JoVO;

public interface JoMapper {
	
    // ** selectList
    List<JoVO> selectList();
    
	// ** selectOne
	JoVO selectOne(JoVO vo);
	
	// ** Update
	int update(JoVO vo);
	
	// ** Insert
	int insert(JoVO vo);
	
	// ** Delete
	int delete(JoVO vo);

}
