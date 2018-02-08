package com.example.user.User.service.serviceImpl;

import com.example.user.User.APIContants;
import com.example.user.User.dto.*;
import com.example.user.User.entity.FollowerMap;
import com.example.user.User.entity.FollowerMapIdentifier;
import com.example.user.User.entity.User;
import com.example.user.User.repository.CategoryRepository;
import com.example.user.User.repository.FollowerRepository;
import com.example.user.User.repository.UserRepository;
import com.example.user.User.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean Login(User user) {
        User res = userRepository.save(user);
        return true;
    }

    @Override
    public User getUserById(String userId) {

        return userRepository.findOne(userId);
    }

    @Override
    public boolean setRole(String userId, int role) {
        User user = userRepository.findOne(userId);
        user.setRole(role);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserProfileDTO getProfile(String userId) {
        User user = userRepository.findOne(userId);
        long following = followerRepository.countByFollowerMapIdentifierFollowerId(userId);
        long followers = followerRepository.countByFollowerMapIdentifierFolloweeId(userId);
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("userId", userId);
        ProfileCountingDTO response = restTemplate.postForObject(APIContants.QNA_SERVICE_API+"/questionAnswer/countQuestionByUserId", body, ProfileCountingDTO.class);
        long categoty = categoryRepository.countByCategoryFollowerMapIdentifierUserId(userId);
        UserProfileDTO userProfileDTO = new UserProfileDTO(user.getUserId(), user.getName(), user.getImageUrl(), user.getRole(), response.getQuestionAskedCount(), followers, following, categoty, response.getQuestionAnsweredCount(), response.getAnswerCount());
        return userProfileDTO;
    }

    @Override
    public List<UserDTO> getFollowingList(String userId) {
        List<FollowerMap> followerMaps = followerRepository.findByFollowerMapIdentifierFollowerId(userId);
        List<UserDTO> userDTOS = new ArrayList<>();
        for (FollowerMap map : followerMaps) {
            User user = userRepository.findOne(map.getFollowerMapIdentifier().getFolloweeId());
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            userDTOS.add(userDTO);

        }

        return userDTOS;
    }

    @Override
    public List<UserDTO> getFollowerList(String userId) {
        List<FollowerMap> followerMaps = followerRepository.findByFollowerMapIdentifierFolloweeId(userId);
        List<UserDTO> userDTOS = new ArrayList<>();
        for (FollowerMap map : followerMaps) {
            User user = userRepository.findOne(map.getFollowerMapIdentifier().getFollowerId());
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            userDTOS.add(userDTO);

        }

        return userDTOS;
    }

    @Override
    public List<String> getFollowerListKafka(String userId) {
        List<FollowerMap> followerMaps = followerRepository.findByFollowerMapIdentifierFolloweeId(userId);
        List<String> userIds = new ArrayList<>();
        for (FollowerMap map : followerMaps) {
            User user = userRepository.findOne(map.getFollowerMapIdentifier().getFollowerId());
            userIds.add(user.getUserId());

        }

        return userIds;
    }

    @Override
    public String getUserName(String userId) {
        return userRepository.findOne(userId).getName();
    }

    @Override
    public List<CategoryWithUserDTO> getAllUser() {
        Iterable<User> userList = userRepository.findAll();
        List<CategoryWithUserDTO> userNameList = new ArrayList<>();
        for (User user : userList) {
            CategoryWithUserDTO categoryWithUserDTO = new CategoryWithUserDTO();
            BeanUtils.copyProperties(user, categoryWithUserDTO);

            RestTemplate restTemplate = new RestTemplate();
            //MAKE CALL TO GET INTERESTED CATEGORIES
            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
            body.add("userId", categoryWithUserDTO.getUserId()); // and so on
            CategoryDto categoryDto = restTemplate.postForObject(APIContants.ADMIN_SERVICE_API+"/getCategoryByModerator", body, CategoryDto.class);
            categoryWithUserDTO.setCategoryId(categoryDto.getCategoryId());
            categoryWithUserDTO.setCategoryName(categoryDto.getCategoryName());
            categoryWithUserDTO.setCategoryUrl(categoryDto.getImageUrl());
            userNameList.add(categoryWithUserDTO);
        }
        return userNameList;
    }

    @Override
    public boolean checkFollow(FollowerMapIdentifier followerMapIdentifier) {
        return followerRepository.exists(followerMapIdentifier);
    }
}
