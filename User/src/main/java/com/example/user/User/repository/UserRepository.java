package com.example.user.User.repository;

import com.example.user.User.entity.User;
import org.springframework.data.repository.CrudRepository;
public interface UserRepository extends CrudRepository<User,String>{
}
