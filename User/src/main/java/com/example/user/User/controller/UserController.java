package com.example.user.User.controller;

import com.example.user.User.APIContants;
import com.example.user.User.dto.*;
import com.example.user.User.entity.*;
import com.example.user.User.service.CategoryService;
import com.example.user.User.service.FollowerService;
import com.example.user.User.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FollowerService followerService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.POST, value = "/getOne")
    public ResponseEntity<?> getOne(@RequestParam String userId) {
        User user = userService.getUserById(userId);
        UserDTO userDTO = new UserDTO();
        if (user == null) {
            return new ResponseEntity<UserDTO>(userDTO, HttpStatus.NO_CONTENT);
        }
        BeanUtils.copyProperties(user, userDTO);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);

    }


    @RequestMapping(method = RequestMethod.GET, value = "/getAllUser")
    public ResponseEntity<?> getAllUserName() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/getProfile")
    public ResponseEntity<?> getProfile(@RequestParam String userId) {
        return new ResponseEntity<UserProfileDTO>(userService.getProfile(userId), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/getFollowingList")
    public ResponseEntity<?> getFollowingList(@RequestParam String userId) {
        return new ResponseEntity<List<UserDTO>>(userService.getFollowingList(userId), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/getFollowerList")
    public ResponseEntity<?> getFollowerList(@RequestParam String userId) {
        return new ResponseEntity<List<UserDTO>>(userService.getFollowerList(userId), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/getFollowerListKafka")
    public ResponseEntity<?> getFollowerListKafka(@RequestParam String userId) {
        return new ResponseEntity<List<String>>(userService.getFollowerListKafka(userId), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/getCategoryListKafka")
    public ResponseEntity<?> getCategoryListKafka(@RequestParam String userId) {
        return new ResponseEntity<ArrayList<String>>(categoryService.findCategoryIdsByUserKafka(userId), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/setRole")
    public ResponseEntity<?> setRole(@RequestParam("userId") String userId, @RequestParam("role") int role) {
        BooleanDTO response = new BooleanDTO(userService.setRole(userId, role));
        return new ResponseEntity<BooleanDTO>(response, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/findCategoryList")
    public ResponseEntity<?> findCatList(@RequestParam String userId) {


        return new ResponseEntity<CategoryList>(categoryService.findByCategory(userId), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/findCategoryListName")
    public ResponseEntity<?> findCatListToString(@RequestParam String userId) {
        RestTemplate restTemplate = new RestTemplate();
        List<String> names = restTemplate.postForObject(APIContants.ADMIN_SERVICE_API+"/getCategoryList", categoryService.findByCategory(userId).getCategoryList(), List.class);

        return new ResponseEntity<List<String>>(names, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/findCategoryUserList/{categoryId}")
    public ResponseEntity<?> findCatUserList(@PathVariable("categoryId") String categoryId) {

        return new ResponseEntity<CategoryList>(categoryService.findCategoryByUser(categoryId), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/getUserName")
    public ResponseEntity<?> getUserName(@RequestParam("userId") String userId) {


        return new ResponseEntity<String>(userService.getUserName(userId), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/findCategoryUserListKafka/{categoryId}")
    public ResponseEntity<?> findCatUserListKafka(@PathVariable("categoryId") String categoryId) {

        return new ResponseEntity<ArrayList<String>>(categoryService.findCategoryByUserKafka(categoryId), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> getByUSerId(@RequestBody UserDTO userDTO) {
        //System.out.println(employeeDTO);
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        BooleanDTO response = new BooleanDTO();
        if (user != null) {
            userService.Login(user);
            for (int i = 1; i <= 5; i++) {
                CategoryFollowerMapIdentifier categoryFollowerMapIdentifier = new CategoryFollowerMapIdentifier(userDTO.getUserId(), i + "");
                categoryService.add(new CategoryFollowMap(categoryFollowerMapIdentifier));
            }
            response.setStatus(true);
            return new ResponseEntity<BooleanDTO>(response, HttpStatus.OK);
        } else {
            response.setStatus(false);
            return new ResponseEntity<BooleanDTO>(response, HttpStatus.CREATED);
        }

    }

    private static String UPLOADED_FOLDER = "/Users/coviam/documents/quora/photos/";

    @RequestMapping(method = RequestMethod.POST, value = "/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestBody MultipartFile file) {
        //System.out.println(employeeDTO);
        if (file.isEmpty()) {
            return new ResponseEntity<BooleanDTO>(new BooleanDTO(false), HttpStatus.CREATED);
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            return new ResponseEntity<BooleanDTO>(new BooleanDTO(true), HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<BooleanDTO>(new BooleanDTO(false), HttpStatus.CREATED);
            //e.printStackTrace();
        }


    }

    @RequestMapping(method = RequestMethod.POST, value = "/follow")
    public ResponseEntity<?> setFollow(@RequestParam("followerId") String followerId, @RequestParam("followeeId") String followeeId) {
        FollowerMapDTO followerMapDto = new FollowerMapDTO();
        FollowerMapIdentifier followerMapIdentifier = new FollowerMapIdentifier();
        followerMapIdentifier.setFollowerId(followerId);

        followerMapIdentifier.setFolloweeId(followeeId);
        followerMapDto.setFollowerMapIdentifier(followerMapIdentifier);
        FollowerMap followerMap = new FollowerMap();
        BeanUtils.copyProperties(followerMapDto, followerMap);
        boolean res = followerService.add(followerMap);
        BooleanDTO response = new BooleanDTO();
        response.setStatus(res);

        return new ResponseEntity<BooleanDTO>(response, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/isFollowing")
    public ResponseEntity<?> checkFollow(@RequestParam("followerId") String followerId, @RequestParam("followeeId") String followeeId) {
        return new ResponseEntity<Boolean>(userService.checkFollow(new FollowerMapIdentifier(followerId, followeeId)), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/unFollow/{followerId}/{followeeId}")
    public ResponseEntity<?> unFollow(@PathVariable("followerId") String followerId, @PathVariable("followeeId") String followeeId) {
        FollowerMapDTO followerMapDto = new FollowerMapDTO();

        FollowerMapIdentifier followerMapIdentifier = new FollowerMapIdentifier();
        followerMapIdentifier.setFollowerId(followerId);

        followerMapIdentifier.setFolloweeId(followeeId);
        followerMapDto.setFollowerMapIdentifier(followerMapIdentifier);
        FollowerMap followerMap = new FollowerMap();
        BeanUtils.copyProperties(followerMapDto, followerMap);
        boolean res = followerService.remove(followerMap);
        BooleanDTO response = new BooleanDTO();
        response.setStatus(res);
        return new ResponseEntity<BooleanDTO>(response, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/followCategory")
    public ResponseEntity<?> setFollowCategory(@RequestParam("userId") String userId, @RequestParam("categoryId") String categoryId) {
        CategoryFollowMapDTO categoryFollowMapDto = new CategoryFollowMapDTO();
        CategoryFollowerMapIdentifier categoryFollowMapIdentifierDTO = new CategoryFollowerMapIdentifier();
        categoryFollowMapIdentifierDTO.setUserId(userId);
        categoryFollowMapIdentifierDTO.setCategoryId(categoryId);
        categoryFollowMapDto.setCategoryFollowMapIdentifierDTO(categoryFollowMapIdentifierDTO);
        CategoryFollowMap categoryFollowMap = new CategoryFollowMap();
        categoryFollowMap.setCategoryFollowerMapIdentifier(categoryFollowMapIdentifierDTO);
        boolean res = categoryService.add(categoryFollowMap);
        BooleanDTO response = new BooleanDTO();
        response.setStatus(res);
        return new ResponseEntity<BooleanDTO>(response, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/unFollowCategory/{followerId}/{categoryId}")
    public ResponseEntity<?> unFollowCategory(@PathVariable("userId") String userId, @PathVariable("categoryId") String categoryId) {
        CategoryFollowMapDTO categoryFollowMapDto = new CategoryFollowMapDTO();
        CategoryFollowerMapIdentifier categoryFollowMapIdentifierDTO = new CategoryFollowerMapIdentifier();
        categoryFollowMapIdentifierDTO.setUserId(userId);

        categoryFollowMapIdentifierDTO.setCategoryId(categoryId);
        categoryFollowMapDto.setCategoryFollowMapIdentifierDTO(categoryFollowMapIdentifierDTO);
        CategoryFollowMap categoryFollowMap = new CategoryFollowMap();
        categoryFollowMap.setCategoryFollowerMapIdentifier(categoryFollowMapIdentifierDTO);
        boolean res = categoryService.remove(categoryFollowMap);
        BooleanDTO response = new BooleanDTO();
        response.setStatus(res);
        return new ResponseEntity<BooleanDTO>(response, HttpStatus.OK);

    }
}
