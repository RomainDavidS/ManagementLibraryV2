package com.library.controller.mbooks;

import com.library.beans.mbooks.book.BookBean;
import com.library.beans.mbooks.book.BookCreateBean;
import com.library.beans.mbooks.book.author.AuthorBean;
import com.library.beans.mbooks.book.edition.EditionBean;
import com.library.beans.mbooks.book.language.LanguageBean;
import com.library.beans.mbooks.book.theme.ThemeBean;
import com.library.config.ApplicationPropertiesConfig;
import com.library.service.mbooks.IBooksService;
import com.library.service.mbooks.author.IAuthorService;
import com.library.service.mbooks.edition.IEditionService;
import com.library.service.mbooks.language.ILanguageService;
import com.library.service.mbooks.theme.IThemeService;
import com.library.service.users.IUsersService;
import com.library.technical.books.BooksFunction;
import com.library.technical.error.Field;
import com.library.technical.search.BookWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController  {


    @Autowired
    private IBooksService booksService;

    @Autowired
    private IAuthorService authorService;

    @Autowired
    private IEditionService editionService;

    @Autowired
    private ILanguageService languageService;

    @Autowired
    private IThemeService themeService;

    @Autowired
    private ApplicationPropertiesConfig appPropertiesConfig;

    @Autowired
    private IUsersService usersService;

    @ModelAttribute("getCoverPath")
    public String getCoverPath(){return appPropertiesConfig.getCoverPath();}

    @ModelAttribute("currentUser")
    public String getCurrentUserFirstName(){
        return  usersService.getCurrentUserFirstName();
    }

    @ModelAttribute("currentUserId")
    public Long getCurrentUserId(){
        return  usersService.getCurrentUserId();
    }

    @ModelAttribute
    public List<AuthorBean> authorBeanList(){
        return authorService.list();
    }

    @ModelAttribute
    public List<EditionBean> editionBeanList(){
        return editionService.list();
    }

    @ModelAttribute
    public List<LanguageBean> languageBeanList(){
        return languageService.list();
    }

    @ModelAttribute
    public List<ThemeBean> themeBeanList(){
        return themeService.list();
    }

    @ModelAttribute
    public List<Field> fieldList (){

        List< Field > fieldList = Arrays.asList(
               new Field("idCover" ),
                new Field("isbn" ) ,
                new Field("title" ),
                new Field("summary" ) ,
                new Field("review" ),
                new Field("availability" ),
                new Field("language.id" ) ,
                new Field("author.id" ) ,
                new Field("theme.id" ) ,
                new Field("edition.id" )
        );
        return fieldList;
    }

    @ModelAttribute
    public BookCreateBean bookCreateBean(){return new BookCreateBean();}


    @GetMapping("/all")
    public String list(  Model model)  {
        List<BookBean> bookBeanList = booksService.list();
        if ( bookBeanList == null) {
            model.addAttribute("title","Aucun livre à afficher");
            return "error/not-found";
        }
        model.addAttribute( bookBeanList );
        model.addAttribute("title","Liste des livres");

        return "books/book/list-book";
    }

    @GetMapping("/info/{id}")
    public String info(@PathVariable("id") long id, Model model){
        model.addAttribute( "idBook", id);

        return "books/book/info-book";
    }

    @GetMapping("/add")
    public String add( Model model){

        return "books/book/add-book";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute @Valid BookCreateBean bookCreateBean, BindingResult result, Model model){



        if ( result.hasErrors() )
            return "books/book/add-book";


        return "redirect:/book/info/" + booksService.save( bookCreateBean ).getId();

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){

        BookBean bookBean = booksService.find( id );

        model.addAttribute( bookBean);

        return "/books/book/update-book";

    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid BookBean bookBean, BindingResult result){
        if ( result.hasErrors() )
            return "/books/book/update-book";

        return "redirect:/book/info/" + booksService.save( bookBean ).getId();

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id,Model model)  {

        if ( booksService.delete( id ) )
            model.addAttribute("delete","delete");
        else
            model.addAttribute("delete","err");

        return "books/book/list-book";
    }


    @RequestMapping(value = "/bookList", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<BookBean> bookBeanList(@RequestParam("term") String term,Model model){

        System.out.println("term : " + term);
        ArrayList< BookBean > suggestions = new ArrayList<>();
        List< BookBean > bookBeanList= booksService.list();

        for (BookBean book: bookBeanList ) {

            if (book.getTitle().toLowerCase().contains(term.toLowerCase())
                    || book.getIsbn().contains(term)
                    || book.getSummary().toLowerCase().contains(term.toLowerCase())) {
                suggestions.add( book );
            }
        }


        // truncate the list to the first n, max 20 elements
        int n = suggestions.size() > 20 ? 20 : suggestions.size();

        List<BookBean> search = new ArrayList<>( suggestions.subList(0, n) );

        return search;

    }




}
