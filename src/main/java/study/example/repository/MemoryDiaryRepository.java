package study.example.repository;

import org.springframework.stereotype.Repository;
import study.example.domain.Diary;

import java.util.*;

@Repository
public class MemoryDiaryRepository implements DiaryRepository{
    private static Map<Long, Diary> diaryMem = new HashMap<>();
    private static long sequence = 1L;


    @Override
    public Diary save(Diary diary) {
        if(diary.getDiaryId()==null)
            diary.setDiaryId(sequence++);
        diaryMem.put(diary.getDiaryId(), diary);
        return diary;
    }

    @Override
    public Optional<Diary> findByID(Long diaryId) {
        return Optional.ofNullable(diaryMem.get(diaryId));
    }

    @Override
    public List<Diary> findALL() {
        return new ArrayList<Diary>(diaryMem.values());
    }

    @Override
    public Diary modify(Diary diary) {
        if(diary.getDiaryId()==null)
            throw new IllegalArgumentException("diaryId does not exists");
        diaryMem.replace(diary.getDiaryId(), diary);
        return diary;
    }

    @Override
    public Optional<Diary> removeById(Long diaryId) {
        return Optional.ofNullable(diaryMem.remove(diaryId));

    }

    @Override
    public void clearStore() {
        diaryMem.clear();
    }
}
