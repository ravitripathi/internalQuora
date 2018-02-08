package com.example.user.User;


import com.example.user.User.dto.UserDTO;
import com.example.user.User.entity.User;
import com.example.user.User.repository.UserRepository;
import com.example.user.User.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUserById_returnTrue()
{

    User user=new User();
    user.setUserId("shahviral9990@gmail.com");
    user.setRole(1);
    user.setImageUrl("img");
    user.setName("viral");
    Mockito.when(userService.getUserById("shahviral9990@gmail.com")).thenReturn(user);
    User response =userService.getUserById("shahviral9990@gmail.com");
    Assert.assertEquals(user,response);

}

    @Test
    public void getFollowingListTest()
    {
        List<UserDTO> userDTOS=new ArrayList<>();
        String userId="shahviral9990@gmail.com";
        userDTOS.add(new UserDTO("shahviral9990@gmail.com","viral","img",1));

        userDTOS.add(new UserDTO("viral.shah@coviam.com","viral","img",1));
        Mockito.when(userService.getFollowingList(userId)).thenReturn(userDTOS);
        List<UserDTO> response=userService.getFollowingList(userId);
        Assert.assertEquals(userDTOS,response);
    }

    @Test
    public void getFollowerListTest()
    {
        List<UserDTO> userDTOS=new ArrayList<>();
        String userId="shahviral9990@gmail.com";
        userDTOS.add(new UserDTO("shahviral9990@gmail.com","viral","img",1));

        userDTOS.add(new UserDTO("viral.shah@coviam.com","viral","img",1));
        Mockito.when(userService.getFollowerList(userId)).thenReturn(userDTOS);
        List<UserDTO> response=userService.getFollowerList(userId);
        Assert.assertEquals(userDTOS,response);
    }
    @Test
    public void getUserNameTest(){

        User user=new User();
        user.setUserId("shahviral9990@gmail.com");
        user.setRole(1);
        user.setImageUrl("img");
        user.setName("viral");
        Mockito.when(userRepository.findOne("shahviral9990@gmail.com")).thenReturn(user);
        String response = userService.getUserName("shahviral9990@gmail.com");
        Assert.assertEquals(user.getName(),response);
    }

}
