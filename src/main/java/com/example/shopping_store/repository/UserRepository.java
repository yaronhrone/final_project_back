package com.example.shopping_store.repository;


import com.example.shopping_store.model.CustomUser;
import com.example.shopping_store.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static String USERS_TABLE = "users";

    public String saveUser(CustomUser user) {
        String sql = "INSERT INTO " + USERS_TABLE + " (first_name, last_name, username, password, address, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getAddress(), user.getEmail(), user.getPhone());
        return "User saved successfully";

    }

    public CustomUser updateUser(CustomUser user) {
        String sql = "UPDATE " + USERS_TABLE + " SET first_name = ?, last_name = ?,  password = ?, address = ?, phone = ? WHERE username = ?";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getPassword(), user.getAddress(), user.getEmail(), user.getPhone(), user.getUsername());
        return getUserByUsername(user.getUsername());
    }

    public String deleteUser(String username) {
        String sql = "DELETE FROM " + USERS_TABLE + " WHERE username = ?";
        jdbcTemplate.update(sql, username);
        return "User deleted successfully";
    }

    public CustomUser getUserByUsername(String username) {
        try {
            String sql = "SELECT * FROM " + USERS_TABLE + " WHERE username = ?";
            return jdbcTemplate.queryForObject(sql, new UserMapper(), username);
        } catch (Exception e) {
            return null;
        }
    }


    public CustomUser getUserByEmail(String email) {
        String sql = "SELECT * FROM " + USERS_TABLE + " WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new UserMapper(), email);
    }
    public String getAddressHelper(String username) {
        String sql = "SELECT address FROM " + USERS_TABLE + " WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, String.class, username);
    }
}
