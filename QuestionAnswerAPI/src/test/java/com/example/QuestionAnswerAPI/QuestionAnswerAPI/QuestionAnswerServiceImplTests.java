package com.example.QuestionAnswerAPI.QuestionAnswerAPI;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.CommentDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.*;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.repository.*;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.impl.QuestionAnswerServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;


@SpringBootTest
public class QuestionAnswerServiceImplTests {

    @InjectMocks
    private QuestionAnswerServiceImpl questionAnswerServiceImpl;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private AnswerListRepository answerListRepository;
    @Mock
    private CommentListRepository commentListRepository;
    @Mock
    private UpvoteListRepository upvoteListRepository;
    @Mock
    private UserQuestionAnswerRepository userQuestionAnswerRepository;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testFindByUserId() {
        List<Question> questionList = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        tags.add("honeysingh");
        questionList.add(new Question("1", "General", "sweta@coviam.com","questiontest1","today", true, "Sport", tags, "", "Swata"));

        when(questionRepository.getByUserId("sweta@coviam.com")).thenReturn(questionList);
        List<Question> response = questionAnswerServiceImpl.findByUserId("sweta@coviam.com");
        Assert.assertEquals(questionList, response);
    }
//    @After
//    public void tearDown() {
//        Mockito.ver
    @Test
    public void testFindByCategory(){
        List<Question> questionList = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        tags.add("honeysingh");
        questionList.add(new Question("1", "General", "sweta@coviam.com","questiontest1","today", true, "Sport", tags, "", "Swata"));

        when(questionRepository.getByCategory("Sport")).thenReturn(questionList);
        List<Question> response = questionAnswerServiceImpl.findByCategory("Sport");
        Assert.assertEquals(questionList, response);
    }

    @Test
    public void testFindAllQuestions(){
        List<Question> questionList = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        tags.add("honeysingh");
        questionList.add(new Question("1", "General", "sweta@coviam.com","questiontest1","today", true, "Sport", tags, "", "Swata"));

        when(questionRepository.findAll()).thenReturn(questionList);
        List<Question> response = questionAnswerServiceImpl.findAllQuestions();
        Assert.assertEquals(questionList, response);
    }

    @Test
    public void testFindByQuestionId(){
        List<String> tags = new ArrayList<>();
        tags.add("honeysingh");
        Question question = new Question("1", "General", "sweta@coviam.com","questiontest1","today", true, "Sport", tags, "", "Swata");

        when(questionRepository.findOne(question.getQuestionId())).thenReturn(question);
        Question response = questionAnswerServiceImpl.findByQuestionId(question.getQuestionId());
        Assert.assertEquals(question, response);
    }

    @Test
    public void testCountQuestionsbyuserid(){
        Question question = new Question("1", "General", "sweta@coviam.com","questiontest1","today", true, "Sport", new ArrayList<>(), "", "Swata");
        Integer count = questionRepository.countByUserId(question.getUserId());
        when(questionRepository.countByUserId(question.getUserId())).thenReturn(count);
        Integer countQuestionResponse = questionAnswerServiceImpl.countQuestionsbyuserid("sweta@coviam.com");

        Assert.assertEquals(count, countQuestionResponse);
    }

    @Test
    public void testCountAnswersByQuestionId(){
        Answer answer = new Answer();
        AnswerList answerList = new AnswerList();
        answer.setUserName("answerTest");
        answer.setId("1");
        answer.setQuestionId("1");
        answer.setTimestamp(Date.from(Instant.now()).toString());
        answerList.setAnswerListId(answer.getQuestionId());
        List<Answer> listOfAnswer = new ArrayList<>();
        listOfAnswer.add(answer);
        answerList.setAnswerDTOList(listOfAnswer);
        answerList.setAnswerListId(answer.getQuestionId());
        when(answerListRepository.getByAnswerListId("1")).thenReturn(answerList);
        //Integer countAnswerResponse = questionAnswerServiceImpl.countAnswersByQuestionId("1");
        Integer actualcount = answerList.getAnswerDTOList().size();

        //Assert.assertEquals(actualcount, countAnswerResponse);
    }

    @Test
    public void testCountCommentsByAnswerId(){
        Comment comment = new Comment();
        CommentList commentList = new CommentList();
        comment.setAnswerId("1");
        comment.setCommentId("1");
        comment.setTimestamp(Date.from(Instant.now()).toString());
        commentList.setCommentListId(comment.getAnswerId());
        List<CommentDTO> listOfComment = new ArrayList<>();
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        listOfComment.add(commentDTO);
        commentList.setCommentDTOList(listOfComment);
        commentList.setCommentListId(comment.getAnswerId());
        when(commentListRepository.getByCommentListId("1")).thenReturn(commentList);
        //Integer countCommentResponse = questionAnswerServiceImpl.countCommentsByAnswerId("1");
        Integer actualcount = commentList.getCommentDTOList().size();

        //Assert.assertEquals(actualcount, countCommentResponse);
    }

    @Test
    public void testCountUpvotesByAnswerId(){
        UpvoteList upvoteList = new UpvoteList();
        upvoteList.setUpvoteListId("1");
        List<String> listOfUser = new ArrayList<>();
        listOfUser.add("1");
        listOfUser.add("2");
        listOfUser.add("3");
        when(upvoteListRepository.getByUpvoteListId("1")).thenReturn(upvoteList);
        //Integer countUpvoteResponse = questionAnswerServiceImpl.countUpvotesByAnswerId("1");
        Integer actualcount = upvoteList.getUserIdList().size();

        //Assert.assertEquals(actualcount, countUpvoteResponse);
    }

    @Test
    public void testGetLatestAnswer(){
        Answer answer = new Answer();
        AnswerList answerList = new AnswerList();
        answer.setAnswer("Test Answer1");
        answer.setQuestionId("1");
        answer.setTimestamp(Date.from(Instant.now()).toString());
        answerList.setAnswerListId("1");
        List<Answer> listOfAnswer = new ArrayList<>();
        listOfAnswer.add(answer);
        answerList.setAnswerDTOList(listOfAnswer);

        when(answerListRepository.findOne("1")).thenReturn(answerList);
       // List<String> responseList = questionAnswerServiceImpl.getLatestAnswer("1");
        List<String> actualList = new ArrayList<>();
        for(Answer answer1 : answerList.getAnswerDTOList()) {
            actualList.add(answer1.getAnswer());
        }

        //Assert.assertEquals(actualList, responseList);
    }

    @Test
    public void testAnswerIdAnsweredByUserId(){
        UserQuestionAnswer userQuestionAnswer = new UserQuestionAnswer();
        List<String> answerIdList = new ArrayList<>();
        answerIdList.add("1");
        answerIdList.add("2");
        answerIdList.add("3");
        userQuestionAnswer.setAnswerIdList(answerIdList);
        when(userQuestionAnswerRepository.findOne("1")).thenReturn(userQuestionAnswer);
        //List<String> responseIdList = questionAnswerServiceImpl.answerIdAnsweredByUserId("1");
        List<String> actualList = userQuestionAnswer.getAnswerIdList();

        //Assert.assertEquals(actualList, responseIdList);
    }
}
