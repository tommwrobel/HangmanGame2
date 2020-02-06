package com.tom.model;

public enum RoundStatement {

    GOOD_ANSWER("NICE ONE!"),
    DUBEL("TRY SOMETHING ELSE!"),
    BAD_ANSWER("NOT GOOD."),
    HELLO("GOOD LUCK!"),
    TIME_OUT("HURRY UP!"),
    WRONG_KEY("WRONG KEY!");

    private String statementText;

    RoundStatement(String messageText) {
        this.statementText = messageText;
    }

    public String getStatement() {
        return statementText;
    }
}
