package com.project.domitory.Board.service;

import com.project.domitory.Board.command.BoardUploadVO;
import com.project.domitory.Board.command.BoardVO;
import com.project.domitory.Board.util.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface BoardMapper {
    void createBoard(BoardVO board);
    BoardVO getPost(Long BBS_SN);
    void updatePost(BoardVO post);
    void deletePost(Long BBS_SN);
    List<BoardVO> getBoardList();

    public int regist(BoardVO vo); //insert
    public ArrayList<BoardVO> getList(@Param("cri") Criteria cri,@Param("user_id") String user_id);
    public int getTotal(@Param("cri") Criteria cri, @Param("user_id") String user_id); //전체게시글 수
    public void registFile(BoardUploadVO vo);
    public BoardVO getDetail(int user_id);
    public ArrayList<BoardUploadVO> getImgs(int user_id);
    public int update(BoardVO vo);
    public void delete(int prod_id);
}
