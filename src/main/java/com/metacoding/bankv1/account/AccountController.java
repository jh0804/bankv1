package com.metacoding.bankv1.account;

import com.metacoding.bankv1.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AccountController {

    private final AccountService accountService;
    private final HttpSession session; // IoC 컨테이너에 들어있는 session DI

    @PostMapping("/account/transfer")
    public String transfer(AccountRequest.TransferDTO transferDTO) {
        //공통 부가 로직 - aop, intercepter, security filter
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요"); // 로그인X시 아예 못들어오도록

        accountService.계좌이체(transferDTO, sessionUser.getId());

        return "redirect:/"; // TODO : 계좌상세 페이지로 redirect 수정
    }

    // history/transfer-form : 기록/이체는 약간 어색할수도
    @GetMapping("/account/transfer-form")
    public String transferForm() {
        //공통 부가 로직
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요"); // 로그인X시 아예 못들어오도록

        return "account/transfer-form";
    }

    @GetMapping("/account")
    public String account(HttpServletRequest request) {
        //자기 계좌만 조회되어야 한다!
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요"); // 로그인X시 아예 못들어오도록

        // 핵심 로직
        List<Account> accountList = accountService.나의계좌목록(sessionUser.getId());
        request.setAttribute("models", accountList);
        return "account/list";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/account/save-form")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요"); // 로그인X시 아예 못들어오도록

        //핵심 로직
        return "account/save-form";
    }

    @PostMapping("/account/save")
    public String save(AccountRequest.SaveDTO saveDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser"); // 안에 뭐가 들어있을지 모르니까 Object
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해주세요"); // 로그인X시 아예 못들어오도록

        // 핵심 로직
        accountService.계좌생성(saveDTO, sessionUser.getId());
        return "redirect:/account";
    }
}
