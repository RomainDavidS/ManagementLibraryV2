package com.library.controller;

import com.library.beans.mbooks.book.BookBean;
import com.library.config.ApplicationPropertiesConfig;
import com.library.service.mbooks.books.IBooksService;
import com.library.service.users.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController  {


    @Autowired
    private IBooksService booksService;

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





    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid BookBean bookBean, BindingResult result){
        if ( result.hasErrors() )
            return "/books/book/update-book";

        return "redirect:/book/info/" + booksService.save( bookBean ).getId();

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
