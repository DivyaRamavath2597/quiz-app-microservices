package com.service.question_service.service;

import com.service.question_service.model.Question;
import com.service.question_service.model.QuestionWrapper;
import com.service.question_service.model.Response;
import com.service.question_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionRepository.save(question);
        try {
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("failure", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions = questionRepository.findRandomQuestionsByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        // Fetch the questions based on the IDs
        for (Integer id : questionIds) {
            Optional<Question> questionOptional = questionRepository.findById(id);
            if (questionOptional.isPresent()) {
                questions.add(questionOptional.get());
            } else {
                // Handle the case where a question isn't found
                System.out.println("Question not found for ID: " + id);
            }
        }

        // Create wrappers for the questions
        for (Question question : questions) {
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            // Add the wrapper to the list
            wrappers.add(wrapper);
        }
        // Return the list of QuestionWrappers
        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for (Response response : responses) {
            System.out.println("Checking response for ID: " + response.getId());
            System.out.println("User's response: " + response.getResponse());
            Optional<Question> optionalQuestion = questionRepository.findById(response.getId());
            if (optionalQuestion.isPresent()) {
                Question question = optionalQuestion.get();
                System.out.println("Correct answer from DB: " + question.getRightAnswer());
                if (Objects.equals(response.getResponse(), question.getRightAnswer())) {
                    System.out.println("✅ Correct answer!");
                    right++;
                } else {
                    System.out.println("❌ Wrong answer.");
                }
            } else {
                System.out.println("⚠️ No question found for ID: " + response.getId());
            }
        }
        System.out.println("Total correct answers: " + right);
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
