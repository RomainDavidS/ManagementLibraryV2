package com.library.beans.mbooks.book;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
public @Data class AuthorBean {
    private Long id;

    @NotBlank(message = "Le nom de l''auteur est obligatoire.")
    private String lastName;

    @NotBlank(message = "Le prénom de l''auteur est obligatoire.")
    private String firstName;


}
