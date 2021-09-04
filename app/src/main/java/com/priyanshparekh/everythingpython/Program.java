package com.priyanshparekh.everythingpython;

public class Program {
    private int id;
    private String ProgramQuestion;
    private String Program;

    public Program(int id, String programQuestion, String program) {
        this.id = id;
        this.ProgramQuestion = programQuestion;
        this.Program = program;
    }

    public Program() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProgramQuestion() {
        return ProgramQuestion;
    }

    public void setProgramQuestion(String programQuestion) {
        ProgramQuestion = programQuestion;
    }

    public String getProgram() {
        return Program;
    }

    public void setProgram(String program) {
        Program = program;
    }
}
