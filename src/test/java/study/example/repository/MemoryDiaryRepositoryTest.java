package study.example.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import study.example.domain.Diary;

import java.util.List;

class MemoryDiaryRepositoryTest {

    MemoryDiaryRepository repository = new MemoryDiaryRepository();

    @AfterEach
    public void afterEach() {repository.clearStore();}

    @Test
    void save() {
        Diary diary = new Diary();
        diary.setContent("일기장 저장 테스트중 입니다.");

        repository.save(diary);

        Diary result = repository.findByID(diary.getDiaryId()).get();
        System.out.println(result.getContent());
        Assertions.assertThat(result).isEqualTo(diary);

    }

    @Test
    void findALL() {
        Diary diary = new Diary();
        diary.setContent("일기장1");
        repository.save(diary);

        diary = new Diary();
        diary.setContent("일기장2");
        repository.save(diary);

        diary = new Diary();
        diary.setContent("일기장3");
        repository.save(diary);
        diary.setContent("일기장 수정");
        repository.save(diary);

        List<Diary> result = repository.findALL();

        for(Diary i: result){
            System.out.println(i.getContent());
        }

        Assertions.assertThat(result.size()).isEqualTo(4);
    }
}