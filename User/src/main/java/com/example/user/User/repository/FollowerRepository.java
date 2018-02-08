package com.example.user.User.repository;

import com.example.user.User.entity.FollowerMap;
import com.example.user.User.entity.FollowerMapIdentifier;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FollowerRepository  extends CrudRepository<FollowerMap,FollowerMapIdentifier> {
    long countByFollowerMapIdentifierFollowerId(String userId);
    long countByFollowerMapIdentifierFolloweeId(String userId);
    List<FollowerMap> findByFollowerMapIdentifierFollowerId(String userId);
    List<FollowerMap> findByFollowerMapIdentifierFolloweeId(String userId);
}
