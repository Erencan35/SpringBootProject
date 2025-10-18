package com.tpe.SpringBootProject.controller;

import com.tpe.SpringBootProject.dto.UserRegisterDTO;
import com.tpe.SpringBootProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register") //http://localhost:8080/user/register + POST + JSON Body
    public ResponseEntity<Map<String, Object>> register(@RequestBody @Valid UserRegisterDTO dto){
        return new ResponseEntity<>(userService.saveUser(dto), HttpStatus.CREATED);
    }
}
