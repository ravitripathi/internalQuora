package com.example.QuestionAnswerAPI.QuestionAnswerAPI;

public class URL {
    public static final String QAAPI = "/questionAnswer";   //end point URL
    public static final String NOTIFICATIONURL = "http://192.168.43.122:8080/notifications";
    public static final String NOTIFICATIONURLNEWPOSTQUESTION = "http://192.168.43.122:8080/notifications/newPost";    //POST method
    public static final String NOTIFICATIONURLNEWPOSTANSWER = "http://192.168.43.122:8080/notifications/newAnswer";    //POST method
    public static final String SOLRSEARCH = "http://192.168.43.157:8080/question/save";
    public static final String USERURL = "http://192.168.43.59:8080/user/getUserName";
    public static final String ADMINQUESTIONURL = "http://192.168.43.186:9090/getModeratorInfo/";
    public static final String ADMINURL = "http://192.168.43.186/";
    public static final String ADDQUESTION = "/addQuestion";    //POST method
    public static final String ADDQUESTIONIMAGE = "/addQuestionImage";    //POST method
    public static final String ADDANSWERIMAGE = "/addAnswer";    //POST method
    public static final String ADDANSWER = "/addAnswerWithoutImage";   //POST method
    public static final String ADDCOMMENT = "/addComment";  //POST method
    public static final String ADDUPVOTE = "/addUpvote";    //POST method
    public static final String COUNTQUESTIONANSWERBYUSERID = "/countQuestionByUserId";   //POST method
    public static final String GETALLQUESTIONS = "/getAllQuestion"; //GET method
    public static final String GETQUESTIONSBYUSERID = "/getQuestionsByUserId";  //POST method
    public static final String GETQUESTIONSBYCATEGORY = "/getQuestionsByCategory";  //POST method
    public static final String COUNTQUESTIONBYCATEGORY = "/countQuestionByCategory/{category}";  //GET method
    public static final String GETQUESTIONBYQUESTIONID = "/getQuestionByQuestionId";  //POST method
    public static final String GETANSWERSBYQUESTIONID = "/getAnswersByQuestionId/{questionId}";  //GET method
    public static final String GETCOMMENTSBYANSWERID = "/getCommentsByAnswerId/{answerId}";  //GET method
    public static final String COUNTANSWERBYQUESTIONID = "/countAnswersByQuestionId/{questionId}";  //GET method
    public static final String GETQUESTIONTITLEBYQUESTIONID = "/getQuestionTitleByQuestionId/{questionId}";  //GET method
    public static final String COUNTCOMMENTSBYANSWERID = "/countCommentsByAnswerId/{answerId}";   //GET method
    public static final String COUNTUPVOTESBYANSWERID = "/countUpvotesByAnswerId/{answerId}"; //GET method
    public static final String DEACTIVATEQUESTIONBYQUESTIONID = "/deactivateQuestion/{questionId}";//GET method
    public static final String GETQUESTIONANSWERBYUSERID = "/questionAnswerByUserId";  //POST method
    public static final String GETLATESTQUESTIONBYCATEGORY = "/latestQuestionByCategory";  //POST method
    public static final String CORABOT = "https://hooks.slack.com/services/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";
    public static final String CORABOTANSWER = "/coraBotAnswer/{questionId}";    //GET method
    public static final String GETANSWERBYUPVOTESORTED = "/getAnswerByUpvoteSorted/{questionId}"; //    GET method
    public static final String GETUPVOTEBYUSERID = "/getUpvoteByUserId";   //GET method

}




   /* /addquestion - done
    /addanswer - done
    /addAnswerWithoutImage - done
    /getquestions(byuserid) - done
    /getanswers(byquestionid) - done
    /addcomment - done
    /addupvote - done
    /countquestion(byuserid) - done
    /countanswers(byquestionid) - done
    /countcomments(byanswerid) - done
    /countupvotes(byanswerid) - done
    /getQuestionByUserid - done
    /getQuestionsByCategory - done
    /getUpvoteByUseridByAnswerId - done
    /getCommentsByAnswerId - done
    /getQuestionByQuestionId - done
    /deActivateQuestionByQuestionId - done
    /getQuestionsAnsweredByUserId - done
    /getLatestQuestionByCategory - done
    /getQuestionsAnsweredByUserId -  done
    /getAnswersForBot - done */

    /*//  For adding a question with image into database API
    //    input - post method and questionId and image file of question to add into question database of a particular user
    //    output - question will be added into database and status will be returns*/
//    @RequestMapping(method = RequestMethod.POST, value = ADDQUESTIONIMAGE)
//    public ResponseEntity<?> addOrUpdate(@RequestBody MultipartFile file, String questionId) {
//        if (!file.isEmpty()) {
//            try {
//                // Get the file and save it somewhere
//                byte[] bytes = file.getBytes();
//                Path path = Paths.get(STORAGE_PATH_QUESTION + "/" + questionId + ".jpg");
//                Files.write(path, bytes);
//                Question question = questionAnswerService.findByQuestionId(questionId);
//                question.setImageUrl(STORAGE_PATH_QUESTION + "/" + questionId + ".jpg");
//                questionAnswerService.addQuestion(question);
//                QuestionDTO questionDTO = new QuestionDTO();
//                BeanUtils.copyProperties(question, questionDTO);
//            } catch (IOException e) {
//                //e.printStackTrace();
//            }
//        }
//        SuccessDTO successDTO = new SuccessDTO();
//        successDTO.setStatus(true);
//        return new ResponseEntity<SuccessDTO>(successDTO, HttpStatus.CREATED);
//    }
//
//    /*//  For adding an answer with image into database API
//    //    input - post method and details of answer along with image file to add into answer database of a particular question
//    //    output - answer will be added into database and status will be returns*/
//    @RequestMapping(method = RequestMethod.POST, value = ADDANSWERIMAGE)
//    public ResponseEntity<?> addAnswer(@RequestParam("file") MultipartFile file, @RequestParam("questionId") String questionId, @RequestParam("userId") String userId, @RequestParam("answer") String answer) { //answerid of postdto will be same as questionid
//        String image_url = "";
//        AnswerDTO answerDTO = new AnswerDTO();
//        answerDTO.setQuestionId(questionId);
//        answerDTO.setAnswer(answer);
//        answerDTO.setUserId(userId);
//        String ansId = UUID.randomUUID().toString().replace("-", "");
//        if (!file.isEmpty()) {
//            image_url = STORAGE_PATH_ANSWER + "/" + ansId + ".jpg";
//        }
//        Answer answerData = new Answer();
//        BeanUtils.copyProperties(answerDTO, answerData);
//        answerData.setId(ansId);
//        answerData.setImageUrl(image_url);
//
//        String responseUserName = restTemplateService.addAnswerUser(answerDTO);
//
//        answerData.setUserName(responseUserName);
//        questionAnswerService.addAnswer(answerData);
//        questionAnswerService.addQuestionAnswer(answerData);
//
//        restTemplateService.addAnswerNotification(answerDTO);
//
//        if (!file.isEmpty()) {
//            try {
//                // Get the file and save it somewhere
//                byte[] bytes = file.getBytes();
//                Path path = Paths.get(STORAGE_PATH_ANSWER + "/" + ansId + ".jpg");
//                Files.write(path, bytes);
//            } catch (IOException e) {
//                //e.printStackTrace();
//            }
//        }
//        SuccessDTO successDTO = new SuccessDTO();
//        successDTO.setStatus(true);
//        return new ResponseEntity<SuccessDTO>(successDTO, HttpStatus.CREATED);
//    }
