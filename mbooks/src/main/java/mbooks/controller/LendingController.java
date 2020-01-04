package mbooks.controller;

import lombok.NonNull;
import mbooks.controller.dto.lending.LendingCreateDto;
import mbooks.controller.dto.lending.LendingUpdateDto;
import mbooks.exceptions.ResourceNotFoundException;
import mbooks.model.Books;
import mbooks.model.Lending;
import mbooks.service.lending.ILendingService;
import mbooks.technical.dto.DTO;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/lending")
public class LendingController   {
    @Autowired
    private ILendingService lendingService;



    @GetMapping("/{id}")
    public Lending find(@PathVariable Long id) {
        return lendingService.find( id );
    }

    @GetMapping("/all")
    public List<Lending> list(){

        List<Lending> lendingList = lendingService.list();
        if (lendingList.isEmpty()) throw new ResourceNotFoundException( "Aucun prêt trouvé.");

        return lendingList;
    }

    @GetMapping("/user/{id}")
    public List<Lending> list(@PathVariable Long id){

        List<Lending> lendingList = lendingService.list( id );
        if (lendingList.isEmpty()) throw new ResourceNotFoundException( "Aucun prêt trouvé.");

        return lendingList;
    }

    @GetMapping("/isRenewable/{id}")
    public boolean  isRenewable(@PathVariable Long id){
        Lending lending = lendingService.find( id );
        return lendingService.isRenewable( lending );
    }

    @GetMapping("/isLendingPossible/{idBooks}/{idUser}")
    public boolean isLendingPossible(@PathVariable Long idBooks, @PathVariable Long idUser){
        return lendingService.isLendingPossible( idBooks,idUser );
    }

    @GetMapping("/book/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<Lending> list(@PathVariable String id){

        List<Lending> lendingList = lendingService.list( id );
        if (lendingList.isEmpty()) throw new ResourceNotFoundException( "Aucun prêt trouvé.");

        return lendingList;
    }

    @PostMapping("/save/fromReservation")
    @ResponseStatus(HttpStatus.OK)
    public Lending saveFromReservation(@DTO(LendingCreateDto.class) @RequestBody Lending lending)  {
        return lendingService.addFromReservation( lending );
    }

    @PutMapping("/renewal")
    @ResponseStatus(HttpStatus.OK)
    public void renewal(@RequestBody Long id){
        lendingService.renewal(id );
    }

    @PutMapping("/return")
    @ResponseStatus(HttpStatus.OK)
    public void returnBook(@RequestBody Long id){ lendingService.returnBook(id ); }


    @GetMapping("/sendRevival")
    public String sendRevival() {
            lendingService.sendLendingRevival();
            return "Les mails ont été envoyés";

    }
    @GetMapping("/getRenewalDay")
    public Integer getRenewalDay(){
        return lendingService.getRenewalDay();
    }


}
