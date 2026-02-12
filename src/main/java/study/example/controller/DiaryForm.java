package study.example.controller;

import java.time.LocalDate;

public class DiaryForm {
    private String content;
    private LocalDate recordDay;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getRecordDay() {
        return recordDay;
    }

    public void setRecordDay(LocalDate recordDay) {
        this.recordDay = recordDay;
    }
}
