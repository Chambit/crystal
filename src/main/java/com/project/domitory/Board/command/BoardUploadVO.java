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
public class BoardUploadVO {
    private Integer SU_FILE_NUM;
    private String SU_FILE_NAME; //실제파일명
    private String SU_FILE_PATH; //업로드 될 날짜폴더
    private String SU_FILE_UUID; //랜덤값
    private Integer SUB_SN; //fk
    private String STUD_NO; //fk(필수x)
}
