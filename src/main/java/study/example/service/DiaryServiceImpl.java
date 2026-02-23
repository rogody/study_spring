package study.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.example.controller.DiaryDTO;
import study.example.domain.Diary;
import study.example.domain.User;
import study.example.repository.DiaryRepository;
import study.example.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final AiService aiService;

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, UserRepository userRepository, AiService aiService) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
        this.aiService = aiService;
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
        String title;
        try {
            title = aiService.summarize(dto.getContent());
            newDiary.setTitle(title);
        }
        catch(Exception e)
        {
            title = "null";
            System.out.println("ai 답변 생성 중 error 발생" + e);
        }

        return diaryRepository.save(newDiary);
    }

    @Override
    public Diary modifyDiary(Long diaryId, String content) {

        Diary updateDiary = diaryRepository.findByID(diaryId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일기장 id 입니다."));
        updateDiary.setContent(content);
        diaryRepository.save(updateDiary);

        return updateDiary;
    }

    @Override
    public Diary removeDiary(Long diaryId) {
        return diaryRepository.removeById(diaryId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일기장 id 입니다. "));

    }


}
