package com.pedroblome.user.repository;


import com.pedroblome.user.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT COUNT(*) as total_bots from public.users  where bot=true;", nativeQuery = true)
    int sevenOrMoreOrdersBot();

    @Query(value = "SELECT id from public.users where bot = true ORDER BY random() LIMIT 1;", nativeQuery = true)
    Long randomBot();



}
