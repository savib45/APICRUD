package com.savibpractice.apiPractice.repository;

import com.savibpractice.apiPractice.domain.User;
import com.savibpractice.apiPractice.exceptions.AuthException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private static final String SQL_CREATE="INSERT INTO practiceTable(firstname,lastname,email,password) VALUES (?,?,?,?)";
    private static final String SQL_COUNT_BY_EMAIL="SELECT COUNT(*) FROM practiceTable WHERE email=?";
    private static final String SQL_FIND_BY_ID="SELECT user_id,firstName,lastname,email,password FROM practiceTable WHERE user_id=?";
    private static final String SQL_FIND_BY_EMAIL="SELECT user_id,firstName,lastname,email,password FROM practiceTable WHERE email=?";
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws AuthException {
        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder=new GeneratedKeyHolder();
            jdbcTemplate.update(connection ->{
                        PreparedStatement ps=connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1,firstName);
                        ps.setString(2,lastName);
                        ps.setString(3,email);
                        ps.setString(4,hashedPassword);
                        return ps;
                    },keyHolder);
            return (Integer) keyHolder.getKeys().get("user_id");
        }catch (Exception e){
            throw new AuthException("Invalid detail.Failed to create account");
        }

    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws AuthException {
       try{
        User user=jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL,new Object[]{email},userRowMapper);
        if (!BCrypt.checkpw(password, user.getPassword()))
            throw new AuthException("Invalid and password");
        return user;
       }catch (Exception e){
           throw new AuthException("Invalid email and password");
       }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email},Integer.class);
    }

    @Override
    public User findBy(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId},userRowMapper);
    }
    private RowMapper<User> userRowMapper=((rs,rowNum)->{
        return new User(rs.getInt("user_id"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("password"));
    });
}
