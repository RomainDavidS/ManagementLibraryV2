package mbooks.controller.dto.books;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbooks.model.Author;
import mbooks.model.BooksReservation;
import mbooks.model.Language;
import mbooks.model.Theme;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class BooksUpdateDto {

    @Id
    private Long id;

    @NotBlank(message = "Le numéro ISBN est obligatoire.")
    private String isbn;

    @NotBlank(message = "Le titre du livre est obligatoire.")
    private String title;

    @NotBlank(message = "Le résumé du livre est obligaoire")
    private String summary;

    @NotBlank(message ="Le nombre d'examplaire est obligatoire.")
    private Long review;

    @NotBlank(message = "Le nombre de livre disponible est obligatoire.")
    private Long availability;

    @NotNull(message = "Le choix de la couverture est obligatoire.")
    private String idCover;

    @NotNull(message = "Le choix du langage est obligatoire.")
    private Language language;

    @NotNull(message = "Le choix de l''auteut est obligatoire.")
    private Author author;

    private Author coAuthor;

    @NotNull(message = "Le choix du thème est obligatoire.")
    private Theme theme;

    @NotNull
    private  BooksReservation booksReservation;
}
