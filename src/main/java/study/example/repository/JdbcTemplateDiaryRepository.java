package study.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import study.example.domain.Diary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Primary
public class JdbcTemplateDiaryRepository implements DiaryRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateDiaryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        System.out.println("jdbc template repository 사용중");
    }


    @Override
    public Diary save(Diary diary) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("diary").usingGeneratedKeyColumns("diary_id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", diary.getTitle());
        parameters.put("content", diary.getContent());
        parameters.put("record_day", diary.getRecordDay());
        parameters.put("created_at", diary.getCreatedAt());
        parameters.put("user_id", diary.getUserId());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        diary.setDiaryId(key.longValue());
        return diary;
    }

    @Override
    public Optional<Diary> findByID(Long diaryId) {
        List<Diary> result = jdbcTemplate.query("select * from diary where diary_id = ?", diaryRowMapper(), diaryId);
        return result.stream().findAny();
    }

    @Override
    public List<Diary> findALL() {
        return jdbcTemplate.query("select * from diary", diaryRowMapper());
    }

    @Override
    public Diary modify(Diary diary) {
        jdbcTemplate.update("update diary set title = ?, content = ?, record_day = ? where diary_id = ?", diary.getTitle(), diary.getContent(), diary.getRecordDay(), diary.getDiaryId());
        return diary;
    }

    @Override
    public Optional<Diary> removeById(Long diaryId) {
        Optional<Diary> result = findByID(diaryId);
        jdbcTemplate.update("delete from diary where diary_id = ?", diaryId);
        return result;
    }

    @Override
    public void clearStore() {
        jdbcTemplate.update("delete from diary");

    }

    private RowMapper<Diary> diaryRowMapper() {
        return (rs, i) -> {
            Diary diary = new Diary();
            diary.setDiaryId(rs.getLong("diary_id"));
            diary.setTitle(rs.getString("title"));
            diary.setContent(rs.getString("content"));
            diary.setRecordDay(rs.getObject("record_day", LocalDate.class));
            diary.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            diary.setUserId(rs.getLong("user_id"));

            return diary;
        };
    }
}
