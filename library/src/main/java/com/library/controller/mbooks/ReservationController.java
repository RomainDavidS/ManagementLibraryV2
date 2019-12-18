package com.library.controller.mbooks;

import com.library.beans.mbooks.reservation.ReservationBean;
import com.library.service.mbooks.reservation.IReservationService;
import com.library.service.users.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private IUsersService usersService;

    @ModelAttribute("currentUser")
    public String getCurrentUserFirstName(){
        return  usersService.getCurrentUserFirstName();
    }

    @GetMapping("/all")
    public String all(Model model){

        List<ReservationBean> reservationBeanList = reservationService.list();
        if ( reservationBeanList == null) {
            model.addAttribute("title","Aucune réservation à afficher");
            return "error/not-found";
        }

        model.addAttribute( reservationBeanList );

        model.addAttribute("title","Liste de toutes les réservations");
        return "books/reservation/list-reservation";
    }

    @GetMapping("/user")
    public String userList(Model model){

        model.addAttribute("title","Liste de mes réservations" );
        List<ReservationBean> reservationBeanList = reservationService.list( usersService.getCurrentUserId() );
        if ( reservationBeanList == null) {
            model.addAttribute("title","Aucune réservation à afficher");
            return "error/not-found";
        }

        model.addAttribute( reservationBeanList );
        return "books/reservation/list-reservation";
    }
}
