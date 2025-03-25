package com.metacoding.bankv1.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/logout")
    public String logout() {
        // 세션은 보통 통으로 버린다. (removeAttribute()는 특정 key만 버림)
        session.invalidate();
        return "redirect:/";
    }

    // 로그인만 예외로 @Post (조회시에도)
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO) {
        User sessionUser = userService.로그인(loginDTO); // service로부터 받은 user를 세션에 저장
        session.setAttribute("sessionUser", sessionUser); // stateful
        // getAttribute할때 sessionId로 구분된 sessionUser key의 value를 꺼낸다.
        return "redirect:/";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        // System.out.println(joinDTO); // toString()이 실행됨
        userService.회원가입(joinDTO); // joinDTO 안에 username, password, fullname
        return "redirect:/login-form";
    }
}
