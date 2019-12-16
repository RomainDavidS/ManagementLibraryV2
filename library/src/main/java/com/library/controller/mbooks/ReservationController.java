package com.library.controller.mbooks;

import com.library.beans.mbooks.reservation.ReservationBean;
import com.library.service.mbooks.reservation.IReservationService;
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

    @ModelAttribute
    public List<ReservationBean> reservationBeanList(){ return reservationService.list(); }

    @GetMapping("/all")
    public String all(Model model){

        return "books/reservation/list-reservation";
    }
}
