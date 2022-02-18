package com.savibpractice.apiPractice.repository;

import com.savibpractice.apiPractice.domain.Category;
import com.savibpractice.apiPractice.domain.User;
import com.savibpractice.apiPractice.exceptions.BadRequestException;
import com.savibpractice.apiPractice.exceptions.ResourcesNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
@Repository
public class CategoryRepositoryImpl implements CategoryRepository{
    private static final String SQL_FIND_BY_ID = "SELECT C.category_id, C.user_id,C.title, C.description,COALESCE(SUM(T.amount),0)from transactionTable T RIGHT OUTER JOIN categoryTable C ON C.category_id= T.category_id WHERE C.user_id =? AND C.category_id =? GROUP BY C.category_id";
    private static final String SQL_CREATE ="INSERT INTO categoryTable(user_id, title, description) VALUES (?,?,?)";
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<Category> findAll(Integer userId) throws ResourcesNotFoundException {
        return null;
    }

    @Override
    public Category findById(Integer userId, Integer categoryId) throws ResourcesNotFoundException {
        try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, categoryId},categoryRowMapper);
        }catch (Exception e){
            throw new ResourcesNotFoundException("Category not found");
        }
    }

    @Override
    public Integer create(Integer userId, String title, String description) throws BadRequestException {
        try{
            KeyHolder keyHolder= new GeneratedKeyHolder();
            jdbcTemplate.update(connection ->{
                PreparedStatement ps= connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,userId);
                ps.setString(2,title);
                ps.setString(3,description);
                ps.execute();
                return ps;
            },keyHolder);
            return (Integer) keyHolder.getKeys().get("category_id");
        }catch (Exception e){
            throw new BadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Category category) throws BadRequestException {

    }

    @Override
    public void removeBy(Integer userId, Integer categoryId) {

    }
    private RowMapper<Category> categoryRowMapper= (rs, rowNum) -> {
        return new Category(
                rs.getInt("category_id"),
                rs.getInt("user_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getDouble("total_expense"));
    };
}
