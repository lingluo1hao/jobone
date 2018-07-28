package com.luo.jobtest.jobone.repository;

import com.luo.jobtest.jobone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository  extends JpaRepository<User,String> {


}


