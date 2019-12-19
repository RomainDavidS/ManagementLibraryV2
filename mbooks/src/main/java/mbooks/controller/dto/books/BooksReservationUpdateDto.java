package mbooks.controller.dto.books;

import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;
import mbooks.model.Books;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class BooksReservationUpdateDto {
    @Id
    private Long id;

    private Integer number ;

    private Integer possible;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date nextReturnDate;
}
