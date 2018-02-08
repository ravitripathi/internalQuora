package com.example.user.User.entity;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
@Embeddable

@Entity
@Table(name = FollowerMap.TABLE_NAME)
public class FollowerMap  {

    public static final String TABLE_NAME = "follower_map";
    @EmbeddedId
    private FollowerMapIdentifier followerMapIdentifier;

    public FollowerMapIdentifier getFollowerMapIdentifier() {
        return followerMapIdentifier;
    }

    public void setFollowerMapIdentifier(FollowerMapIdentifier followerMapIdentifier) {
        this.followerMapIdentifier = followerMapIdentifier;
    }
}
