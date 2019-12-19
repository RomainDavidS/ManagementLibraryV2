package com.library.beans.mbooks.book;

import com.library.beans.mbooks.book.author.AuthorBean;
import com.library.beans.mbooks.book.edition.EditionBean;
import com.library.beans.mbooks.book.language.LanguageBean;
import com.library.beans.mbooks.book.theme.ThemeBean;
import com.library.beans.mbooks.cover.CoverBean;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
public @Data class BookCreateBean {
    @NotBlank(message = "Le numéro ISBN est obligatoire.")
    private String isbn;

    @NotBlank(message = "Le titre du livre est obligatoire.")
    private String title;

    @NotBlank(message = "Le résumé du livre est obligatoire")
    private String summary;

    @NotNull(message ="Le nombre d''examplaire est obligatoire.")
    private Long review;

    @NotNull(message = "Le nombre de livre disponible est obligatoire.")
    private Long availability;

    @NotNull(message = "Le choix de la couverture est obligatoire.")
    private String idCover;

    @NotNull(message = "Le choix du langage est obligatoire.")
    private LanguageBean language;

    @NotNull(message = "Le choix de l''auteut est obligatoire.")
    private AuthorBean author;

    private AuthorBean coAuthor;

    @NotNull(message = "Le choix du thème est obligatoire.")
    private ThemeBean theme;

    @NotNull(message = "Le choix de l''éditeur est obligatoire.")
    private EditionBean edition;

    @NotNull
    private BooksReservationBean booksReservation;
}
