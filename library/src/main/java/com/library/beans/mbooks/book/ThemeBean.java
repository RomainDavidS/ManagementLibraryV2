package com.library.beans.mbooks.book;


import com.library.beans.mbooks.book.BookBean;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
public  @Data class ThemeBean {

    private Long id;

    @NotBlank(message = "Le thème est obligatoire.")
    private String value;

}
