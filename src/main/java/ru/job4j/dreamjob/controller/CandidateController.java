package ru.job4j.dreamjob.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.repository.CandidateStore;

public class CandidateController {

    private final CandidateStore candidateStore = CandidateStore.instOf();

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", candidateStore.findAll());
        return "candidates";
    }
}
