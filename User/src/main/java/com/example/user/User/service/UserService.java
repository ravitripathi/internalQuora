package com.example.user.User.service;

import com.example.user.User.dto.CategoryWithUserDTO;
import com.example.user.User.dto.UserDTO;
import com.example.user.User.dto.UserProfileDTO;
import com.example.user.User.entity.FollowerMapIdentifier;
import com.example.user.User.entity.User;

import java.util.List;

public interface UserService {
    public boolean Login(User user);

    public User getUserById(String userId);

    public boolean setRole(String userId, int role);

    public UserProfileDTO getProfile(String userId);

    public List<UserDTO> getFollowingList(String userId);

    public List<UserDTO> getFollowerList(String userId);

    public List<String> getFollowerListKafka(String userId);
    public String getUserName(String userId);

    public List<CategoryWithUserDTO> getAllUser();
public boolean checkFollow(FollowerMapIdentifier followerMapIdentifier);

}
