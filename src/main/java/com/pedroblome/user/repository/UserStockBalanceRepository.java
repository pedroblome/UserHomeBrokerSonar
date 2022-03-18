package com.pedroblome.user.repository;

import java.sql.Timestamp;
import java.util.List;

import com.pedroblome.user.model.UserStockBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserStockBalanceRepository extends JpaRepository<UserStockBalance, Long> {
    UserStockBalance getByIdUser = null;

    //stocks por usuario
    @Query("SELECT  stock  from UserStockBalance stock  where stock.idUser = :idUser and stock.volume>0 ")
    List<UserStockBalance> findStockByUser(@Param("idUser") Long idUser);

    @Query("SELECT  new com.pedroblome.user.model.UserStockBalance (stock.volume) FROM UserStockBalance stock where (stock.idUser = :idUser) and (stock.idStock = :idStock)")
    UserStockBalance volume(@Param("idUser") Long idUser,@Param("idStock") Long idStock);

    @Query("select stock from UserStockBalance stock  where stock.idUser = ?1 and stock.idStock = ?2")
    UserStockBalance findByUserStock( long idUser, long idStock);
    
    @Transactional  
    @Modifying  
    @Query(value = "INSERT INTO public.user_stock_balance  (idStock, idUser, createdOn, stockName, stockSymbol, updatedOn, volume)  VALUES(?1, ?2, ?3, ?4, ?5, ?6, ?7)", nativeQuery = true)
    void createStockBalance(long idStock, long idUser,Timestamp createdOn, String stockName, String stockSymbol, Timestamp updatedOn, Integer volume);
  




}
