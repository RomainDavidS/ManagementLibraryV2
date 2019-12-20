package mbooks.controller;


import mbooks.controller.dto.reservation.ReservationCreateDto;
import mbooks.controller.dto.reservation.ReservationUpdateDto;
import mbooks.exceptions.ResourceNotFoundException;
import mbooks.model.Reservation;
import mbooks.service.IBooksService;
import mbooks.service.reservation.IReservationService;
import mbooks.technical.dto.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;


    @Autowired
    private IBooksService booksService;

    @GetMapping("/{id}")
    public Reservation find(@PathVariable Long id) {
        Reservation reservation = reservationService.find( id );

        return reservation;
    }

    @GetMapping("/all")
    public List<Reservation> list(){

        List<Reservation> reservationList = reservationService.list();
        if (reservationList.isEmpty()) throw new ResourceNotFoundException( "Aucun livre trouvé.");

        return reservationList;
    }
    @GetMapping("/user/{id}")
    public List<Reservation> listUser( @PathVariable Long id){

        List<Reservation> reservationList = reservationService.list( id );
        if (reservationList.isEmpty()) throw new ResourceNotFoundException( "Aucun livre trouvé.");

        return reservationList;
    }

    @GetMapping("/book/{id}")
    public List<Reservation> listBook( @PathVariable Long id){
        List<Reservation> reservationList = reservationService.list( booksService.find( id) );
        if ( reservationList.isEmpty() ) throw new ResourceNotFoundException( "Aucun livre trouvé.");
        return reservationList;
    }

    @GetMapping("/position/{idBook}/{idUser}")
    public Integer positionUser( @PathVariable Long idBook, @PathVariable Long idUser){
        return reservationService.positionUser( idBook,idUser );
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public Reservation save(@DTO(ReservationCreateDto.class) @RequestBody Reservation reservation)  {



        return reservationService.save(reservation);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Reservation update(@DTO(ReservationUpdateDto.class) @RequestBody Reservation reservation){
        return reservationService.save( reservation );
    }

    @DeleteMapping("/delete/{id}/{idUserUpdate}")
    public void delete(@PathVariable Long id, @PathVariable Long idUserUpdate){
        reservationService.delete( id, idUserUpdate );
    }
}
