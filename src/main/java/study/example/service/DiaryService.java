package study.example.service;

import study.example.controller.DiaryDTO;
import study.example.domain.Diary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryService {

    Diary getDiary(Long diaryId);

    List<Diary> getAll();

    Diary writeDiary(DiaryDTO dto);
    //일기 내용 반환

    //일기 내용 수정
    Diary modifyDiary(Long diaryId, DiaryDTO dto);

    Diary removeDiary(Long diaryId);

}
