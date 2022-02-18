package com.savibpractice.apiPractice.resources;

import com.savibpractice.apiPractice.Constants;
import com.savibpractice.apiPractice.domain.User;
import com.savibpractice.apiPractice.domain.UserDto;
import com.savibpractice.apiPractice.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody UserDto userMap) {
        String email = userMap.getEmail();
        String password = userMap.getPassword();
        User user = userService.validateUser(email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserDto userMap) {
        String firstName = userMap.getFirstName();
        String lastName = userMap.getLastName();
        String email = userMap.getEmail();
        String password = userMap.getPassword();
        User user = userService.registerUser(firstName, lastName, email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }
    private Map<String, String> generateJWTToken(User user){
        long timestamp=System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp)).setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("userId",user.getUserId())
                .claim("email",user.getEmail())
                .claim("firstName",user.getFirstName())
                .claim("lastName",user.getLastName())
                .compact();
        Map<String, String> map =new HashMap<>();
        map.put("token",token);
        return map;
    }
}