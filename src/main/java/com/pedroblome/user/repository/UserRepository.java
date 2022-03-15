package com.pedroblome.user.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.pedroblome.user.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT *FROM User stock where email = ?1 ", nativeQuery = true)
    List<User> getByEmail(String email);

    //analisar se existe quantidade necessaria de bots.
    @Query(value = "SELECT COUNT(*) as total_bots from public.users  where bot=true;", nativeQuery = true)
    int sevenOrMoreOrdersBot();

    @Query(value = "SELECT id from public.users where bot = true ORDER BY random() LIMIT 1", nativeQuery = true)
    int randomBot();


    
}
