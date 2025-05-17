package com.example.strandrecommender.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuestionnaireController {

    static class Question {
        public String key;
        public String text;
        public List<String> strands;

        public Question(String key, String text, List<String> strands) {
            this.key = key;
            this.text = text;
            this.strands = strands;
        }
    }

    private static final List<Question> QUESTIONS = List.of(
        new Question("q1", "I enjoy solving math and science problems.", List.of("STEM")),
        new Question("q2", "I am interested in business and entrepreneurship.", List.of("ABM")),
        new Question("q3", "I like expressing myself through writing, speaking or arts.", List.of("HUMSS", "GAS")),
        new Question("q4", "I am interested in working abroad in the future.", List.of("TVL")),
        new Question("q5", "I want to help people as a teacher, counselor or public servant.", List.of("HUMSS"))
    );

    @GetMapping("/questionnaires")
    public List<Map<String, Object>> getQuestions() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Question q : QUESTIONS) {
            Map<String, Object> map = new HashMap<>();
            map.put("key", q.key);
            map.put("text", q.text);
            result.add(map);
        }
        return result;
    }

    @PostMapping("/strand/recommend")
    public Map<String, Integer> recommendStrands(@RequestBody Map<String, Integer> answers) {
        Map<String, Integer> scores = new HashMap<>();
        for (Question q : QUESTIONS) {
            int answerScore = answers.getOrDefault(q.key, 2) - 2; // Neutral = 0, Agree = +1, Strongly Agree = +2
            for (String strand : q.strands) {
                scores.put(strand, scores.getOrDefault(strand, 0) + answerScore);
            }
        }
        return scores;
    }
}
