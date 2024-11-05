package com.learn.jwt.controller;

import com.learn.jwt.entity.UserInfo;
import com.learn.jwt.payload.AuthRequest;
import com.learn.jwt.service.IUserInfoService;
import com.learn.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;


    @GetMapping("/welcome")
    public String hello() {
        return "Hello Aadil ";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo) {
        userInfoService.addUserInfo(userInfo);
        return "User Added Successfully";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody AuthRequest authRequest) {
        if (authRequest.password() == null || authRequest.password().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.userName(), authRequest.password()));
        if (authenticate.isAuthenticated()) {
            Map<String, Object> claims = new HashMap<>();
            return jwtService.generateToken(claims, authRequest.userName());
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }


    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('USER_ROLES') or hasAuthority('ADMIN_ROLES')")
    public String logoutUser(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = null;
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }
//        blackList.blackListToken(token);
        return "You have successfully logged out !";
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasAuthority('ADMIN_ROLES') or hasAuthority('USER_ROLES')")
    public List<UserInfo> getAllUsers() {
        return userInfoService.getAllUsers();
    }

    @GetMapping("/getUser/{id}")
    @PreAuthorize("hasAuthority('USER_ROLES')")
    public UserInfo getUserById(@PathVariable Integer id) {
        return userInfoService.getUserById(id);
    }

}
