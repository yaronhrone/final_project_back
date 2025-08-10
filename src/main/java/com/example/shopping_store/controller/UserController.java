package com.example.shopping_store.controller;

import com.example.shopping_store.model.CustomUser;
import com.example.shopping_store.service.UserService;
import com.example.shopping_store.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> saveUser(@RequestBody CustomUser user) {
        System.out.println("user controller " + user);
        try {

            String result = userService.saveUser(user);
            System.out.println(result + "result");
            if (result.contains("successfully")){
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update")
    public ResponseEntity<CustomUser> updateUser( @RequestHeader (value = "Authorization") String token, @RequestBody CustomUser updatedUser) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            updatedUser.setUsername(username);

            if (updatedUser.getFirstName() == null || updatedUser.getLastName() == null || updatedUser.getEmail() == null) {
                return new ResponseEntity("User not updated, first name, last name and email are required", HttpStatus.BAD_REQUEST);
            }
            CustomUser userFromDB = userService.getUserByUsername(updatedUser.getUsername());
            if (!userFromDB.getEmail().equals(updatedUser.getEmail())) {
                CustomUser userWithTheSameEmail = userService.getUserByEmail(updatedUser.getEmail());
                if (userWithTheSameEmail != null) {
                    return new ResponseEntity("User not updated, This email already exist in the system.", HttpStatus.BAD_REQUEST);
                }
            }
            CustomUser user = userService.updateUser(updatedUser);
            if (user == null) {
                return new ResponseEntity("User not updated. this user does not exist in the system.", HttpStatus.BAD_REQUEST);
            }

            CustomUser user1 = userService.updateUser(updatedUser);
            if (user1 == null) {
                return new ResponseEntity("User not updated. this user does not exist in the system.", HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok(user1);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestHeader (value = "Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            String result = userService.deleteUser(username);
            if (result.contains("successfully")) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CustomUser> getUserByUsername(@RequestHeader (value = "Authorization") String token ) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);
            CustomUser user = userService.getUserByUsername(username);


                return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
