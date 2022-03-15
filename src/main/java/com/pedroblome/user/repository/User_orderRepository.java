package com.pedroblome.user.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import com.pedroblome.user.model.User_order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface User_orderRepository extends JpaRepository<User_order, Long> {

    @Query(value = "select max(price) from public.user_order where type = 0 and status = 1 and id_stock = ?1", nativeQuery = true)
    BigDecimal askMax(Long id_stock);

    @Query(value = "select min(price) from public.user_order where type = 0 and status = 1 and id_stock = ?1", nativeQuery = true)
    BigDecimal askMin(Long id_stock);

    @Query(value = "select max(price) from public.user_order where type = 1 and status = 1 and id_stock = ?1", nativeQuery = true)
    BigDecimal bidMax(Long id_stock);

    @Query(value = "select min(price) from public.user_order where type = 1 and status = 1 and id_stock = ?1", nativeQuery = true)
    BigDecimal bidMin(Long id_stock);

    @Query(value = " SELECT CASE WHEN EXISTS(SELECT * from public.user_order where id_stock = ?1 and status = 1 and type = 0 ) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END ", nativeQuery = true)
    Boolean orderStockSell(Long id_stock);

    @Query(value = " SELECT CASE WHEN EXISTS(SELECT * from public.user_order where id_stock = ?1 and status = 1 and type = 1 ) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END ", nativeQuery = true)
    Boolean orderStockBuy(Long id_stock);

    @Query(value = "SELECT COUNT(*) as total_bots from public.user_order where status = 1 and type = ?1", nativeQuery = true)
    Integer sevenOrMoreOrders(Long id_stock, Integer type);

    @Query(value = "select * from public.user_order where type = 0 and id_stock= ?1 and status = 1 and id_user != ?2 and  remaing_volume>0 order by created_on", nativeQuery = true)
    List<User_order> listSell(Long id_stock, Long id_user);


    @Query(value = "select * from public.user_order where type = 1 and id_stock= ?1 and status = 1 and id_user != ?2 and remaing_volume>0 order by created_on", nativeQuery = true)
    List<User_order> listBuy(Long id_stock, Long id_user);

    @Query(value = "select * from public.user_order where  id_user= ?1 and status = 1 order by created_on", nativeQuery = true) 
    List<User_order> findOrdersByIdUser(Long id_user);


}