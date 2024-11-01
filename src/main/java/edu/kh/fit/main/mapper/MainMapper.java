package edu.kh.fit.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.trainer.dto.Trainer;

@Mapper
public interface MainMapper {

  /**
   * 신규 트레이너 조회
   * @return
   */
  List<Trainer> selectNewTrainerList();

  /**
   * 최신 홈트레이닝/홈짐 조회
   * @return
   */
    List<Board> selectBoardList(int classNo);

    /**
     * 비밀번호 변경
     * @param email
     * @param password
     * @return
     */
	int update(String email, String password);


}
