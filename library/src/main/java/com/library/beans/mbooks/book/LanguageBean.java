package com.library.beans.mbooks.book;

import com.library.beans.mbooks.book.BookBean;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;



@NoArgsConstructor
public  @Data class LanguageBean {

    private Long id;

    @NotBlank(message = "Le langage est obligatoire.")
    private String value;

}
