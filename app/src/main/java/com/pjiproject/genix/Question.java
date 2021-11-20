package com.pjiproject.genix;

public class Question {

    /* ATTRIBUTES */
    private String question, correctAnswer, userAnswer;


    /* CONSTRUCTOR */
    public Question(String question, String correctAnswer, String userAnswer) {

        this.question = question;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;

    }

    /* GETTERS */
    public String getQuestion() { return question; }

    public String getCorrectAnswer() { return correctAnswer; }

    public String getUserAnswer() { return userAnswer; }

}
