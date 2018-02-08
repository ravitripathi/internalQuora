package com.coviam.notifications.controller;

import com.coviam.notifications.entity.Notification;
import com.coviam.notifications.service.KafkaSender;
import com.coviam.notifications.service.NotificationService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/notifications/")
public class NotificationController {

	@Autowired
    KafkaSender kafkaSender;

    @Autowired
    NotificationService notificationService;

	@RequestMapping(method = RequestMethod.GET, value = APIPath.FIND_ALL)
    public ResponseEntity<List<Notification>> findAll(){
        return new ResponseEntity<>(notificationService.findAll(), HttpStatus.OK);
    }


	@RequestMapping(method= RequestMethod.GET,value = APIPath.NEW_POST)
	public void newPost(@PathVariable("category") String category, @PathVariable("sender") String sender, @PathVariable("questionId") String question_id) {


        RestTemplate restTemplate = new RestTemplate();
        ArrayList<String> users = restTemplate.getForObject(APIPath.API_USERS+"getFollowerListKafka/"+sender, ArrayList.class);
        for(String user: users) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("msg", "new_post_by_follower");
            jsonObject.addProperty("follower_id", sender);
            jsonObject.addProperty("question_id", question_id);
            kafkaSender.send(user, jsonObject);
            HashMap<String, String> details = new HashMap<>();
            details.put("category_id", category);
            details.put("question_id", question_id);
            details.put("follower_id", sender);
            notificationService.addNotification(user,details,1);
            System.out.println(user+ " follows "+sender);
        }

        System.out.println("PRINTING USERS");
        restTemplate = new RestTemplate();
        users = restTemplate.getForObject(APIPath.API_USERS+"findCategoryUserListKafka/"+category, ArrayList.class);
        for(String user: users) {
            System.out.println("User "+user+" follows "+category);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("msg", "new_post_in_category");
            jsonObject.addProperty("follower_id", sender);
            jsonObject.addProperty("question_id", question_id);
            kafkaSender.send(user, jsonObject);
            HashMap<String, String> details = new HashMap<>();
            details.put("category_id", category);
            details.put("question_id", question_id);
            details.put("author_id", sender);
            notificationService.addNotification(user,details,2);
        }


        //sendEmail
	}



    @RequestMapping(method= RequestMethod.GET,value = APIPath.NEW_ANSWER)
    public void newAnswer(@PathVariable("user_id") String user_id, @PathVariable("answer_id") String answer_id, @PathVariable("questionId") String question_id) {

        //addNotification

        HashMap<String, String> details = new HashMap<>();
        details.put("answer_id", answer_id);
        details.put("question_id", question_id);
        notificationService.addNotification(user_id,details,3);

        //sendEmail
    }

    @RequestMapping(method= RequestMethod.GET,value = APIPath.NEW_FOLLOWER)
    public String newFollower(@PathVariable("user_id") String user_id, @PathVariable("followerId") String follower_id) {

	    System.out.println("CALL RECEIVED "+ user_id + " AND "+follower_id);

	    //PRODUCE
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("msg", "new_follower");
        jsonObject.addProperty("follower_id", follower_id);

	    kafkaSender.send(user_id, jsonObject);

        //addNotification
        HashMap<String, String> details = new HashMap<>();
        details.put("follower_id", follower_id );
        notificationService.addNotification(user_id,details,4);

        return "1";
        //sendEmail
    }

    @RequestMapping(method= RequestMethod.GET,value = APIPath.MARK_ALL_AS_READ)
    public void markAllRead(@PathVariable("user_id") String user_id) {
	    notificationService.markAllAsRead(user_id);
    }

    @RequestMapping(method= RequestMethod.GET,value = APIPath.FIND_ALL_BY_USER)
    public void findAllByUser(@PathVariable("user_id") String user_id) {
        notificationService.findAllByUser(user_id);
    }

    @RequestMapping(method = RequestMethod.GET, value = APIPath.GET_NOTIFICATION)
    public ResponseEntity<Notification> getSingle(@PathVariable("notification_id") String notification_id){
	    return new ResponseEntity<> (notificationService.findOneById(notification_id), HttpStatus.OK);
    }





}

