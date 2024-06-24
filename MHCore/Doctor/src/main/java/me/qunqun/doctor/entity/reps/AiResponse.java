package me.qunqun.doctor.entity.reps;

import lombok.Data;

import java.util.List;

@Data
public class AiResponse {
    private int code;
    private Data data;
    private String message;

    @lombok.Data
    public static class Data {
        private String id;
        private String type;
        private boolean close;
        private String error;
        private int chatId;
        private String textResponse;
        private List<Source> sources;

    }

    @lombok.Data
    public static class Source {
        private String id;
        private String url;
        private String title;
        private String docAuthor;
        private String description;
        private String docSource;
        private String chunkSource;
        private String published;
        private int wordCount;
        private int tokenCountEstimate;
        private String text;
        private int _distance;
        private int score;

    }

}

