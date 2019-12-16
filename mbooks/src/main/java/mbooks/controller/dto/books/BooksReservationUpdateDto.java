package mbooks.controller.dto.books;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class BooksReservationUpdateDto {

    @Id
    private Long id;

    @NonNull
    private Integer number ;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date nextAvailabilityDate;
}
