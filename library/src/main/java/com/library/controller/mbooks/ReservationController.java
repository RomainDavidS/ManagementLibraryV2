package com.library.controller.mbooks;

import com.library.beans.mbooks.book.BookBean;
import com.library.beans.mbooks.book.BooksReservationBean;
import com.library.beans.mbooks.lending.LendingBean;
import com.library.beans.mbooks.reservation.ReservationBean;
import com.library.beans.mbooks.reservation.ReservationCreateBean;
import com.library.service.mbooks.IBooksReservationService;
import com.library.service.mbooks.IBooksService;
import com.library.service.mbooks.reservation.IReservationService;
import com.library.service.users.IUsersService;
import com.library.technical.date.SimpleDate;
import com.library.technical.state.books.BooksState;
import com.library.technical.state.reservation.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private IUsersService usersService;

    @Autowired
    private IBooksService booksService;



    @Autowired
    private SimpleDate simpleDate;

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

    @GetMapping("/add/{id}/{title}")
    public String add(@PathVariable Long id, @PathVariable String title, Model model){
        BooksState booksState = booksService.getBooksState( id, usersService.getCurrentUserId() );

        model.addAttribute("id",id);
        model.addAttribute("title", title );
        model.addAttribute("booksState", booksState.getCode() );


        if( booksState == BooksState.RESERVATION_POSSIBLE )
            return "books/reservation/add-reservation";
        else
            return "books/reservation/add-reservation-ko";

    }

    @GetMapping("/add/yes/{id}")
    public String addYes(@PathVariable("id")Long id, Model model){
        BookBean bookBean = booksService.find( id );

        reservationService.save(
                new ReservationCreateBean(
                        usersService.getCurrentUserId(),
                        State.INPROGRESS,
                        bookBean
                ) );


        bookBean.getBooksReservation().setNumber( bookBean.getBooksReservation().getNumber() + 1);
        booksService.save( bookBean );
        model.addAttribute("title", bookBean.getTitle() );
        model.addAttribute("nextReturnDate", simpleDate.getDateTime( bookBean.getBooksReservation().getNextReturnDate() ) );
        model.addAttribute("positionUser", reservationService.positionUser(id, usersService.getCurrentUserId() ) );

        return "books/reservation/add-reservation-success";
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

    @GetMapping("/delete/{id}/{title}")
    public String delete(@PathVariable Long id,@PathVariable String title, Model model){

        model.addAttribute("id",id);
        model.addAttribute("title",title );

        return "books/reservation/delete-reservation";

    }
    @GetMapping("/delete/yes/{id}")
    public String deleteYes(@PathVariable Long id, Model model){

        ReservationBean reservation = reservationService.find( id );


        model.addAttribute("id",id);

        if ( reservation.getState() == State.INPROGRESS
                && (reservation.getIdUserReservation() == usersService.getCurrentUserId() || usersService.isAdmin() ) ) {
            reservationService.delete( id );
            BookBean bookBean = booksService.find( reservation.getBook().getId() );
            bookBean.getBooksReservation().setNumber( bookBean.getBooksReservation().getNumber() - 1);
            booksService.save( bookBean );
            return "books/reservation/delete-reservation-success";
        }
        else
            return "books/reservation/delete-reservation-ko";
    }
}
