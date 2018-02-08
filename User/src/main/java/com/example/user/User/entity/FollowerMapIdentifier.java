package com.example.user.User.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Embeddable
public class FollowerMapIdentifier  implements Serializable {
    @NotNull
    private String followerId;
    @NotNull
    private String followeeId;
    public static final String TABLE_NAME = "follower_map";
    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public FollowerMapIdentifier() {
    }

    public FollowerMapIdentifier(String followerId, String followeeId) {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }

    public String getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(String followeeId) {
        this.followeeId = followeeId;
    }
}
