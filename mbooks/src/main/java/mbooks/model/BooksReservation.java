package mbooks.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public @Data class BooksReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="id_book", referencedColumnName="id",unique = true)
    @NonNull
    private  Books books;

    @NonNull
    private Integer number ;

    @NonNull
    private Integer possible;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date nextAvailabilityDate;



}
