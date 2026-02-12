    package study.example.repository;

    import org.assertj.core.api.Assertions;
    import org.junit.jupiter.api.AfterEach;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.context.annotation.Bean;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.transaction.annotation.Transactional;
    import study.example.controller.DiaryDTO;
    import study.example.domain.Diary;
    import study.example.domain.User;
    import study.example.service.DiaryService;
    import study.example.service.DiaryServiceImpl;

    import static org.junit.jupiter.api.Assertions.*;
    @SpringBootTest
    class JdbcTemplateDiaryRepositoryTest {

        @Autowired
        DiaryService diaryService;

        @Autowired
        DiaryRepository diaryRepository;

        @Autowired
        UserRepository userRepository;

        @Test
        @Transactional
        void writeDiary(){

            User user = new User();
            user.setUserName("test");
            user = userRepository.save(user);

            DiaryDTO dto = new DiaryDTO();
            dto.setUserId(user.getUserId());
            dto.setContent("일기장 서비스 테스트중");
            Diary diary = diaryService.writeDiary(dto);

            Diary retrieveDiary = diaryService.getDiary(diary.getDiaryId());
            System.out.println(retrieveDiary.getContent());

            Assertions.assertThat(diary.getContent()).isEqualTo(retrieveDiary.getContent());
        }

    }