package com.project.domitory.Board.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardVO {
    private Integer SUB_SN; //게시글 번호
    private String STUD_NO; //작성자
    private String SUB_NM; //제목
    private String SUB_CN; //내용
    private LocalDateTime SUB_REG_YMD; //날짜
    private String SUB_CLSF; //진행상태
    private String SUB_ATCH_FILE_NM; //첨부파일

}
