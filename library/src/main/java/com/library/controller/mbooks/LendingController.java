package com.library.controller.mbooks;


import com.library.beans.mbooks.lending.LendingBean;
import com.library.beans.mbooks.lending.LendingCreateBean;
import com.library.config.ApplicationPropertiesConfig;
import com.library.proxies.IBooksPropertiesProxy;
import com.library.service.mbooks.IBooksService;
import com.library.service.mbooks.lending.ILendingService;
import com.library.service.users.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/lending")
public class LendingController  {

    @Autowired
    private ILendingService lendingService;

    @Autowired
    private IBooksService booksService;

    @Autowired
    private IUsersService usersService;

    @Autowired
    private IBooksPropertiesProxy booksPropertiesProxy;



    @Autowired
    private ApplicationPropertiesConfig appPropertiesConfig;

    @ModelAttribute("getCoverPath")
    public String getCoverPath(){return appPropertiesConfig.getCoverPath();}

    @ModelAttribute("currentUser")
    public String getCurrentUserFirstName(){
        return  usersService.getCurrentUserFirstName();
    }

    @ModelAttribute("renewalNumber")
    public Integer getRenewalNumber(){
        return booksPropertiesProxy.renewalNumber();
    }

    @GetMapping("/user")
    public String userList(Model model){

        model.addAttribute("title","Liste de mes prêts " );
        List<LendingBean> lendingBeanList = lendingService.list( usersService.getCurrentUserId() );
        if ( lendingBeanList == null) {
            model.addAttribute("title","Aucun emprunt à afficher");
            return "error/not-found";
        }

        model.addAttribute( lendingBeanList );
        return "books/lending/list-lending";
    }

    @GetMapping("/renewal/{id}")
    public String renawal(@PathVariable("id")Long id, Model model){

        LendingBean lendingBean = lendingService.find( id );
        model.addAttribute("id",id);
        model.addAttribute("title", lendingBean.getBook().getTitle() );
        model.addAttribute("endDate",lendingService.renewalDate( lendingBean.getEndDate() ) );

        if( lendingService.isRenewable( lendingBean) )
            return "books/lending/renewal-lending";
        else {
            model.addAttribute("endDate",lendingService.getEndDate( lendingBean.getEndDate() ) );
            return "books/lending/renewal-lending-ko";
        }
    }

    @GetMapping("/renewal/yes/{id}")
    public String renawalYes(@PathVariable("id")Long id, Model model){

        lendingService.renewal( id );
        LendingBean lendingBean = lendingService.find( id );
        model.addAttribute("title", lendingBean.getBook().getTitle() );
        model.addAttribute("endDate",lendingService.getEndDate( lendingBean.getEndDate() ) );
        return "books/lending/renewal-lending-success";
    }



    @ModelAttribute
    public LendingCreateBean lendingCreateBean(){return new  LendingCreateBean();}


    @GetMapping("/all")
    public String allList(Model model){

        List<LendingBean> lendingBeanList = lendingService.list();
        if ( lendingBeanList == null) {
            model.addAttribute("title","Aucun emprunt à afficher");
            return "error/not-found";
        }

        model.addAttribute( lendingBeanList );

        model.addAttribute("title","Liste de tous les prêts.");
        return "books/lending/list-lending";
    }




    @GetMapping("/book/{id}")
    public String list(@PathVariable("id") String id,Model model){

        model.addAttribute("title","Liste des prêts du livre : " + booksService.getTitle( id ));
        List<LendingBean> lendingBeanList = lendingService.list( id );
        if ( lendingBeanList == null) {
            model.addAttribute("title","Aucun emprunt à afficher");
            return "error/not-found";
        }
        model.addAttribute( lendingBeanList );
        return "books/lending/list-lending";
    }


    @GetMapping("/info/{id}")
    public String info(@PathVariable("id") long id, Model model){
        model.addAttribute( "idLending", id);



        return "books/lending/info-lending";
    }

    @GetMapping("/add")
    public String add(Model model){return "books/lending/add-lending";}

    @PostMapping("/save")
    public String save(@ModelAttribute @Valid LendingCreateBean lendingCreateBean, BindingResult result, Model model){



        if ( result.hasErrors() )
            return "books/lending/add-lending";


        return "redirect:/lending/info/" + lendingService.save( lendingCreateBean ).getId();

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){

        LendingBean lendingBean = lendingService.find( id );

        model.addAttribute( lendingBean);

        return "/books/lending/update-lending";

    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid LendingBean lendingBean, BindingResult result){
        if ( result.hasErrors() )
            return "/books/lending/lending-book";

        return "redirect:/book/info/" + lendingService.save( lendingBean ).getId();

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id,Model model)  {

        if ( lendingService.delete( id ) )
            model.addAttribute("delete","delete");
        else
            model.addAttribute("delete","err");

        return "books/lending/list-lending";
    }
}
