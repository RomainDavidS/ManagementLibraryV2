package mbooks.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "isbn"))
public @Data class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String isbn;

    @NonNull
    private String idCover;

    @NonNull
    private String title;

    @NonNull
    @Column(columnDefinition = "TEXT")
    private String summary;

    @NonNull
    private Long review;

    @NonNull
    private Long availability;


    @ManyToOne
    @JoinColumn(name="id_language", referencedColumnName="id")
    @NonNull
    private Language language;

    @ManyToOne
    @JoinColumn(name="id_author", referencedColumnName="id")
    @NonNull
    private Author author;

    @ManyToOne
    @JoinColumn(name="id_coauthor", referencedColumnName="id")
    private Author coAuthor;

    @ManyToOne
    @JoinColumn(name="id_theme", referencedColumnName="id")
    @NonNull
    private Theme theme;

    @ManyToOne
    @JoinColumn(name="id_edition", referencedColumnName="id")
    @NonNull
    private  Edition edition;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id_book_reservation", referencedColumnName="id",unique = true)
    @NonNull
    private  BooksReservation booksReservation;




}
