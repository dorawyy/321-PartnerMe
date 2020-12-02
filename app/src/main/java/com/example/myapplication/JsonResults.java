package com.example.myapplication;

class JsonResults {
    public static class MatchResult {
        private Float similarity;
        private User userList;

        public Float getSimilarity(){ return this.similarity;}
        public User getUser(){ return this.userList; }
    }

    public static class MessageResult {
        private String user;
        private String message;

        public String getUser(){ return this.user; }
        public String getMessage(){ return this.message; }
    }

    public static class MessageListResult {
        private String email;

        public String getEmail(){ return this.email; }
    }
}
