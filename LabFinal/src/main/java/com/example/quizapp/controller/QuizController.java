package com.example.quizapp.controller;

import com.example.quizapp.model.Score;
import com.example.quizapp.repository.ScoreRepository;
import com.example.quizapp.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuizController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ScoreRepository scoreRepo;

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/start-quiz")
    public String startQuiz(@RequestParam String studentId, HttpSession session, Model model) {
        if (studentRepo.existsByStudentId(studentId)) {
            session.setAttribute("studentId", studentId);
            return "quiz"; // This loads quiz.html
        } else {
            model.addAttribute("error", "এই আইডি সঠিক নয়" );
            return "login";
        }
    }

    @PostMapping("/submit-quiz")
    public String submitQuiz(@RequestParam("q1") String answer1,
                             @RequestParam("q2") String answer2,
                             HttpSession session,
                             Model model) {

        String studentId = (String) session.getAttribute("studentId");

        int correct = 0;

        // ✅ Correct answers based on chemistry quiz.html
        if ("H2O".equals(answer1)) correct++;       // Q1 correct answer
        if ("atomic".equals(answer2)) correct++;    // Q2 correct answer

        int totalScore = correct * 15; // Each question 15 marks

        // Save score in database
        scoreRepo.save(new Score(studentId, totalScore));

        // Send score to result.html
        model.addAttribute("score", totalScore);
        return "result";
    }
}
