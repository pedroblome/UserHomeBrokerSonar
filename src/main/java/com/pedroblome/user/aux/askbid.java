// package com.pedroblome.user.aux;

// import java.math.BigDecimal;

// import com.pedroblome.user.controller.dto.StockAskBidDto;
// import com.pedroblome.user.model.User_order;
// import com.pedroblome.user.repository.User_orderRepository;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.RequestBody;

// public class askbid {
//     @Autowired
//     private User_orderRepository user_orderRepository;

    // public StockAskBidDto checkAskBid(@RequestBody User_order user_order) {
        

    //     BigDecimal askmin = null;
    //     BigDecimal askmax = null;
    //     BigDecimal bidmin = null;
    //     BigDecimal bidmax = null;

    //     System.out.println("----------------------------");
    //     System.out.println(askmin +" "+ askmin+" "+bidmax+" "+bidmin);

    //     if (user_order.getType() == 1) {
    //         if (user_order.getPrice().compareTo(user_orderRepository.askMax(user_order.getId_stock())) == 1) {
    //             askmax = user_order.getPrice();

    //         }
    //         // novo preço menor que askmax
    //         if (user_order.getPrice().compareTo(user_orderRepository.askMax(user_order.getId_stock())) == -1) {
    //             askmax = user_orderRepository.askMax(user_order.getId_stock());

    //         } // novo preço menor que ask min
    //         if (user_order.getPrice().compareTo(user_orderRepository.askMin(user_order.getId_stock())) == -1) {
    //             askmin = user_order.getPrice();

    //         }
    //         // novo preço maior que ask min
    //         if (user_order.getPrice().compareTo(user_orderRepository.askMax(user_order.getId_stock())) == 1) {
    //             askmin = user_orderRepository.askMax(user_order.getId_stock());

    //         }
    //     }
    //     if (user_order.getType() == 0) {
    //         if (user_order.getPrice().compareTo(user_orderRepository.bidMax(user_order.getId_stock())) == 1) {
    //             bidmax = user_order.getPrice();

    //         }
    //         if (user_order.getPrice().compareTo(user_orderRepository.bidMax(user_order.getId_stock())) == -1) {
    //             bidmax = user_orderRepository.bidMax(user_order.getId_stock());

    //         } // novo preço menor que ask min
    //         if (user_order.getPrice().compareTo(user_orderRepository.bidMin(user_order.getId_stock())) == -1) {
    //             bidmin = user_order.getPrice();

    //         }
    //         // novo preço maior que ask min
    //         if (user_order.getPrice().compareTo(user_orderRepository.bidMax(user_order.getId_stock())) == 1) {
    //             bidmin = user_orderRepository.bidMax(user_order.getId_stock());

    //         }

    //     }
       
    //     StockAskBidDto updateStock = new StockAskBidDto(user_order.getId_stock(), askmin, askmax, bidmin, bidmax,
    //             user_order.getUpdated_on());
    //     return updateStock;
    // }
//}
