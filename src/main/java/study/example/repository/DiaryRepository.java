package study.example.repository;

import study.example.domain.Diary;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository {

    Diary save(Diary diary);

    Optional<Diary> findByID(Long diaryId);

    List<Diary> findALL();

    Optional<Diary> removeById(Long diaryId);

    void clearStore();
}
