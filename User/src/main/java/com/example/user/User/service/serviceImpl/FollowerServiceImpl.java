package com.example.user.User.service.serviceImpl;

import com.example.user.User.APIContants;
import com.example.user.User.entity.FollowerMap;
import com.example.user.User.repository.FollowerRepository;
import com.example.user.User.service.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
public class FollowerServiceImpl implements FollowerService{
   @Autowired
    private FollowerRepository followerRepository;
    @Override
    public boolean add(FollowerMap followerMap) {
        RestTemplate restTemplate=new RestTemplate();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("user_id", followerMap.getFollowerMapIdentifier().getFolloweeId());
        body.add("follower_id", followerMap.getFollowerMapIdentifier().getFollowerId());

       restTemplate.postForObject(APIContants.NOTIFICATION_SERVICE_API+"/notifications/newFollower", body, Integer.class);
        followerRepository.save(followerMap);
        return true;
    }

    @Override
    public boolean remove(FollowerMap followerMap) {
        followerRepository.delete(followerMap);
        return true;
    }
}
