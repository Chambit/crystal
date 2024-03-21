package com.project.domitory.Board.controller;

import com.project.domitory.Board.command.BoardUploadVO;
import com.project.domitory.Board.service.BoardService;
import com.project.domitory.Board.command.BoardVO;
import com.project.domitory.Board.util.Criteria;
import com.project.domitory.Board.util.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    @Qualifier("boardService")
    private BoardService boardService;

    @Value("${project.upload.path}")
    private String uploadPath;


@GetMapping("/board")
public String list(Model model,Criteria cri, HttpSession session) {
    String user_id = (String)session.getAttribute("user_id");
    //목록을 가지고 나와서 데이터를 담고 나감
    ArrayList<BoardVO> list = boardService.getList(cri,user_id); //데이터
    int total=boardService.getTotal(cri,user_id);
    System.out.println(total);
    PageVO pageVO = new PageVO(cri, total); //페이지네이션
    System.out.println("이거 실행 되나요? 1");

    model.addAttribute("list", list);
    model.addAttribute("pageVO", pageVO);
    System.out.println(list.toString());
    System.out.println(user_id);
    System.out.println(cri);
    System.out.println(total);
    return "board/board";
}
    @GetMapping("/boardReg")
    public String reg() {
        System.out.println("그럼 이건 타?");
        return "board/boardReg";
    }

    // 상품등록요청
    @PostMapping("/boardForm")
    public String boardForm(BoardVO vo,
                            RedirectAttributes ra,
                            @RequestParam("file") List<MultipartFile> list,
                            HttpSession session) {

        //vo.setSTUD_NO((String)session.getAttribute("user_id"));
        vo.setSTUD_NO("admin");
        // 공백인 이미지는 제거
        list = list.stream().filter(m -> !m.isEmpty()).collect(Collectors.toList());
        // 이미지 타입인지 검사
        for (MultipartFile file : list) {
            if (!file.getContentType().contains("image")) {
                ra.addFlashAttribute("msg", "이미지 파일만 등록가능");
                return "redirect:/board/board";
            }
        }
        // 3. 이미지를 올린 경우는 서비스로 위임
        int result = boardService.regist(vo, list); // vo는 상품데이터, list에 파일 데이터
        if (result == 1) { // 성공
            ra.addFlashAttribute("msg", "등록 완료"); // 리다이렉트 시에 1회성 데이터
        } else { // 실패
            ra.addFlashAttribute("msg", "등록 실패");
        }

        return "redirect:/board/board";
    }
    @GetMapping("/boardDetail")
    public String detail(@RequestParam("sub_sn") int user_id,
                         Model model) {
        System.out.println("헤헤~");
        BoardVO vo = boardService.getDetail(user_id); //게시글 내용

        ArrayList<BoardUploadVO> imgs = boardService.getImgs(user_id); //이미지들
        for(BoardUploadVO vo2 : imgs) {
            System.out.println(vo2.toString());
        }
        model.addAttribute("vo", vo);
        model.addAttribute("imgs", imgs);
        System.out.println(imgs);


        return "/board/boardDetail";
    }

    @GetMapping("/display")
    public @ResponseBody ResponseEntity<byte[]> display(@RequestParam("filename") String filename, @RequestParam("filepath") String filepath,
                                                        @RequestParam("fileuuid") String fileuuid){
        String path = uploadPath + "/" + filepath + "/" + fileuuid + "_" + filename;
        File file = new File(path);

        byte[] arr = null;

        try {
            arr = FileCopyUtils.copyToByteArray(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(arr, HttpStatus.OK);
    }

    //업데이트요청
    @PostMapping("/updateForm")
    public String updateForm(BoardVO vo,
                             RedirectAttributes ra) {

        //1. 화면에서 넘오는 값을 받습니다.
        //2. 서비스에 update메서드를 생성
        //3. enddate, prod_name, price, count, discount, 설명, 내용을 업데이트
        //4. 업데이트 성공실패 여부를 저장해서 목록화면으로 이동.
        int result = boardService.update(vo);

        if(result == 1) {
            ra.addFlashAttribute("msg", "정상 처리되었습니다");
        } else {
            ra.addFlashAttribute("msg", "수정에 실패했습니다");
        }


        return "redirect:/product/productList";
    }

}

