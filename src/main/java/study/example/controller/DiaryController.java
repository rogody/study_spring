package study.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import study.example.domain.Diary;
import study.example.service.DiaryService;
import study.example.service.UserService;

import java.util.List;

@Controller
public class DiaryController {

    private final DiaryService diaryService;
    private final UserService userService;

    @Autowired
    public DiaryController(DiaryService diaryService, UserService userService){
        this.diaryService = diaryService;
        this.userService = userService;
    }

    @GetMapping("/diary/new")
    public String diaryForm() {return "diary/create-diary-form"; }

    @PostMapping("/diary/new")
    public String createDiary(DiaryForm form){
        DiaryDTO dto = new DiaryDTO();
        dto.setUserId(userService.getCurrentId());
        //유저 로그인 기능이 없으므로 임시로 만드는 master 계정의 userid로 선택
        dto.setRecordDay(form.getRecordDay());
        dto.setContent(form.getContent());
        diaryService.writeDiary(dto);

        return "redirect:/diary";
    }

    @GetMapping("/diary")
    public String list(Model model){
        List<Diary> diaries = diaryService.getAll();
        model.addAttribute("diaries", diaries);
        return "diary/diary-list";
    }
}
