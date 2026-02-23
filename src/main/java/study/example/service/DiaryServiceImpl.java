package study.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.example.controller.DiaryDTO;
import study.example.domain.Diary;
import study.example.repository.DiaryRepository;
import study.example.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, UserRepository userRepository) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Diary getDiary(Long diaryId) {
        return diaryRepository.findByID(diaryId).orElse(null);
    }

    @Override
    public List<Diary> getAll() {
        return diaryRepository.findALL();
    }

    @Override
    public Diary writeDiary(DiaryDTO dto) {
        userRepository.findById(dto.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 user id 입니다."));
        Diary newDiary = new Diary();
        newDiary.setUserId(dto.getUserId());
        newDiary.setContent(dto.getContent());
        newDiary.setRecordDay(dto.getRecordDay());
        newDiary.setCreatedAt(LocalDateTime.now());
        diaryRepository.save(newDiary);

        return newDiary;
    }

    @Override
    public Diary modifyDiary(Long diaryId, DiaryDTO dto) {

        Diary updateDiary = diaryRepository.findByID(diaryId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일기장 id 입니다."));
        updateDiary.setContent(dto.getContent());
        updateDiary.setRecordDay(dto.getRecordDay());
        diaryRepository.save(updateDiary);

        return updateDiary;
    }

    @Override
    public Diary removeDiary(Long diaryId) {
        return diaryRepository.removeById(diaryId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일기장 id 입니다. "));

    }
}
