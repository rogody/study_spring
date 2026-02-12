package study.example.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import study.example.domain.Diary;

import java.util.List;

class MemoryDiaryRepositoryTest {

    MemoryDiaryRepository diaryRepository = new MemoryDiaryRepository();

    @AfterEach
    public void afterEach() {diaryRepository.clearStore();}

    @Test
    void save() {
        Diary diary = new Diary();
        diary.setContent("일기장 저장 테스트중 입니다.");

        diaryRepository.save(diary);

        Diary result = diaryRepository.findByID(diary.getDiaryId()).get();
        System.out.println(result.getContent());
        Assertions.assertThat(result).isEqualTo(diary);

    }

    @Test
    void findALL() {
        Diary diary = new Diary();
        diary.setContent("일기장1");
        diaryRepository.save(diary);

        diary = new Diary();
        diary.setContent("일기장2");
        diaryRepository.save(diary);

        diary = new Diary();
        diary.setContent("일기장3");
        diaryRepository.save(diary);
        diary.setContent("일기장 수정");
        diaryRepository.save(diary);

        List<Diary> result = diaryRepository.findALL();

        for(Diary i: result){
            System.out.println(i.getContent());
        }

        Assertions.assertThat(result.size()).isEqualTo(4);
    }
}