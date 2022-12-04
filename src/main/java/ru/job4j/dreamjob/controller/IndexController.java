package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

import static ru.job4j.dreamjob.util.GetUserNameSession.getUserName;

@Controller
@ThreadSafe
public class IndexController {

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        getUserName(model, session);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}