package com.library.beans.mbooks.book;

import com.library.beans.mbooks.book.BookBean;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;


@NoArgsConstructor
public @Data class EditionBean {

    private Long id;

    @NotBlank(message = "Le nom de l''éditeur est obligatoire.")
    private String name;


}
