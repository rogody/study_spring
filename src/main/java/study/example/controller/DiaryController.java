package study.example.controller;

import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.example.domain.Diary;
import study.example.service.DiaryService;
import study.example.service.UserService;

import java.time.LocalDate;
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
    public String createDiary(
            @RequestParam LocalDate recordDay,
            @RequestParam String content
            ){
        DiaryDTO dto = new DiaryDTO();
        dto.setUserId(userService.getCurrentId());
        //유저 로그인 기능이 없으므로 임시로 만드는 master 계정의 userid로 선택
        dto.setRecordDay(recordDay);
        dto.setContent(content);
        diaryService.writeDiary(dto);

        return "redirect:/diary";
    }

    @GetMapping("/diary")
    public String list(Model model){
        List<Diary> diaries = diaryService.getAll();
        model.addAttribute("diaries", diaries);
        return "diary/diary-list";
    }

    @GetMapping("/diary/edit/{diaryId}")
    public String editForm(
            @PathVariable Long diaryId,
            Model model){
        Diary diary = diaryService.getDiary(diaryId);
        model.addAttribute("diary", diary);
        return "diary/edit-diary";
    }

    @PostMapping("diary/edit/{diaryId}")
    public String editDiary(
            @PathVariable Long diaryId,
            @RequestParam LocalDate recordDay,
            @RequestParam String content
    )
    {
        DiaryDTO dto = new DiaryDTO();
        dto.setUserId(userService.getCurrentId());
        dto.setRecordDay(recordDay);
        dto.setContent(content);
        diaryService.modifyDiary(diaryId, dto);
        return "redirect:/diary";
    }

    @PostMapping("diary/delete/{diaryId}")
    public String deleteDiary(@PathVariable Long diaryId)
    {
        diaryService.removeDiary(diaryId);
        return "redirect:/diary";
    }


}
