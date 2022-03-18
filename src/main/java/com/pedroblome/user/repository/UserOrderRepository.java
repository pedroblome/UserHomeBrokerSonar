package com.pedroblome.user.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import com.pedroblome.user.model.UserOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {

    @Query(value = "select max(price) from public.user_order where type = 0 and status = 1 and idStock = ?1", nativeQuery = true)
    BigDecimal askMax(Long idStock);

    @Query(value = "select min(price) from public.user_order where type = 0 and status = 1 and idStock = ?1", nativeQuery = true)
    BigDecimal askMin(Long idStock);

    @Query(value = "select max(price) from public.user_order where type = 1 and status = 1 and idStock = ?1", nativeQuery = true)
    BigDecimal bidMax(Long idStock);

    @Query(value = "select min(price) from public.user_order where type = 1 and status = 1 and idStock = ?1", nativeQuery = true)
    BigDecimal bidMin(Long idStock);

    @Query(value = " SELECT CASE WHEN EXISTS(SELECT * from public.user_order where idStock = ?1 and status = 1 and type = 0 ) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END ", nativeQuery = true)
    Boolean orderStockSell(Long idStock);

    @Query(value = " SELECT CASE WHEN EXISTS(SELECT * from public.user_order where idStock = ?1 and status = 1 and type = 1 ) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END ", nativeQuery = true)
    Boolean orderStockBuy(Long idStock);


    @Query(value = "select * from public.user_order where type = 0 and idStock= ?1 and status = 1 and idUser != ?2 and  remaingVolume>0 order by createdOn", nativeQuery = true)
    List<UserOrder> listSell(Long idStock, Long idUser);


    @Query(value = "select * from public.user_order where type = 1 and idStock= ?1 and status = 1 and idUser != ?2 and remaingVolume>0 order by createdOn", nativeQuery = true)
    List<UserOrder> listBuy(Long idStock, Long idUser);

    @Query(value = "select * from public.user_order where  idUser= ?1 and status = 1 order by createdOn", nativeQuery = true) 
    List<UserOrder> findOrdersByIdUser(Long idUser);


}