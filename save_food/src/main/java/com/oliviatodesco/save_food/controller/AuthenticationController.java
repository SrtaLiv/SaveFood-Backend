package com.oliviatodesco.save_food.controller;


import com.oliviatodesco.save_food.dto.AuthLoginRequestDTO;
import com.oliviatodesco.save_food.dto.RegisterUserDto;
import com.oliviatodesco.save_food.service.UserDetailsServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthLoginRequestDTO userRequest) {
        return new ResponseEntity<>(this.userDetailsService.loginUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid RegisterUserDto userRequest) {
        return new ResponseEntity<>(this.userDetailsService.signupUser(userRequest), HttpStatus.OK);
    }

   /* @PostMapping("/logout")
    public ResponseEntity logout() {
        return new ResponseEntity<>();
    }
*/

}