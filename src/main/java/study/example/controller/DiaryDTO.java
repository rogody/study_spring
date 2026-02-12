package study.example.controller;

import java.time.LocalDate;

public class DiaryDTO {
    private Long userId;
    private String content;
    private LocalDate recordDay;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
        if(recordDay == null){
            this.recordDay = LocalDate.now();
        }
        else{
            this.recordDay = recordDay;
        }
    }
}
