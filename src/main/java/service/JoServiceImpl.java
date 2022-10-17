package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapperInterface.JoMapper;
import vo.JoVO;

@Service
public class JoServiceImpl implements JoService {
	@Autowired
	JoMapper mapper;

    // ** selectList
    @Override
	public List<JoVO> selectList() {
    	return mapper.selectList();
    }
    
	// ** selectOne
    @Override
	public JoVO selectOne(JoVO vo) {
    	return mapper.selectOne(vo);
    }
    
	// ** Update : 수정
	public int update(JoVO vo) {
		return mapper.update(vo);
	}
	
	// ** Insert : 추가
	public int insert(JoVO vo) {
		return mapper.insert(vo);
	}
	
	// ** Delete : 삭제
	public int delete(JoVO vo) {
		return mapper.delete(vo);
	}
    
}
