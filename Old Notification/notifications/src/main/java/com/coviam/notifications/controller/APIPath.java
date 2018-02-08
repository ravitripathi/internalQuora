package com.coviam.notifications.controller;

public interface APIPath {


    String API_QUESTIONS="";
    String API_USERS="http://10.177.7.93:8080/user/";

    // ============= KAFKA ===============
    String NEW_POST="/newPost/{category}/{sender}/{questionId}";
    String NEW_ANSWER="/newAnswer/{user_id}/{answer_id}/{questionId}";
    String NEW_FOLLOWER= "/newFollower/{user_id}/{followerId}";


    // ============== NORMAL ==============
    String FIND_ALL = "/findAll";
    String FIND_ALL_BY_USER = "/findAll/{user_id}";
    String MARK_ALL_AS_READ = "/markAllRead/{user_id}";
    String GET_NOTIFICATION = "/get/{notification_id}";


}
