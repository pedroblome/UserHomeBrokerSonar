package com.pedroblome.user.repository;

import java.sql.Timestamp;
import java.util.List;

import com.pedroblome.user.model.User_stock_balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface User_stock_balanceRepository extends JpaRepository<User_stock_balance, Long> {
    User_stock_balance getByIdUser = null;

    //stocks por usuario
    @Query("SELECT  new com.pedroblome.user.model.User_stock_balance ( stock.id_user, stock.id_stock, stock.stock_name, stock.stock_symbol,stock.volume) FROM User_stock_balance stock where stock.id_user = :id_user and stock.volume>0 ")
    List<User_stock_balance> findStockByUser(@Param("id_user") Long id_user);

    @Query("SELECT  new com.pedroblome.user.model.User_stock_balance (stock.volume) FROM User_stock_balance stock where (stock.id_user = :id_user) and (stock.id_stock = :id_stock)")
    User_stock_balance volume(@Param("id_user") Long id_user,@Param("id_stock") Long id_stock);

    @Query(value = "select * from user_stock_balance where id_user = ?1 and id_stock = ?2", nativeQuery = true)
    User_stock_balance findByUserStock( long idUser, long idStock);

    @Query(value = "INSERT INTO public.user_stock_balance  (id_stock, id_user, created_on, stock_name, stock_symbol, updated_on, volume)  VALUES(?1, ?2, ?3, ?4, ?5, ?6, ?7)", nativeQuery = true)
    User_stock_balance createStockBalance(long id_stock, long id_user,Timestamp created_on, String stock_name, String stock_symbol, Timestamp updated_on, Integer volume);
    //cria stock ballance com volume = 0




    // @Query(value = "INSERT INTO public.user_stock_balance  (id_stock, id_user, created_on, stock_name, stock_symbol, updated_on, volume)  VALUES(:id_stock, :id_use, :created_on, :stock_name, :stock_symbol, :updated_on, :volume)", nativeQuery = true)
    // User_stock_balance createStockBalance(long id_stock, long id_user,Timestamp created_on, String stock_name, String stock_symbol, Timestamp updated_on, Integer volume);
    

    





}
