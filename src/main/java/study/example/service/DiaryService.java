package study.example.service;

import study.example.domain.Diary;

import java.util.Optional;

public interface DiaryService {

    Diary getDiary(Long diaryId);

    Diary writeDiary(Long userId, String content);
    //일기 내용 반환

    //일기 내용 수정
    Diary modifyDiary(Long diaryId, String content);

    Diary removeDiary(Long diaryId);

}
