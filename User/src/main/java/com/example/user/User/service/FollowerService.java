package com.example.user.User.service;

import com.example.user.User.entity.FollowerMap;

public interface FollowerService {

    public boolean add(FollowerMap followerMap);

    public boolean remove(FollowerMap followerMap);
}
