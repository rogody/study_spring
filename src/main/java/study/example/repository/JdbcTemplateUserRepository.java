package study.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import study.example.domain.User;

import javax.sql.DataSource;
import java.util.*;

@Repository
@Primary
public class JdbcTemplateUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("user_id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", user.getUserName());
        parameters.put("password", user.getPassword());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setUserId(key.longValue());
        return user;
    }

    @Override
    public Optional<User> findById(Long userId) {
        List<User> result = jdbcTemplate.query("select * from user where user_id = ? ", userRowMapper(), userId);
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByName(String userName) {
        List<User> result = jdbcTemplate.query("select * from user where username = ? ", userRowMapper(), userName);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from user", userRowMapper());
    }

    @Override
    public void clearStore() {
        jdbcTemplate.update("delete from user");

    }

    private RowMapper<User> userRowMapper(){
        return (rs, i) -> {
            User user = new User();
            user.setUserId(rs.getLong("user_id"));
            user.setUserName(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        };
    }
}
