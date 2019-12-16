package com.library.service.mbooks;
import com.library.proxies.IBooksPropertiesProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BooksPropertiesImpl implements IBooksPropertiesService {

    @Autowired
    private IBooksPropertiesProxy booksPropertiesProxy;


   public Integer getRenewalNumber(){
       return  booksPropertiesProxy.getRenewalNumber();
   }


   public Integer getRenewalDay(){
       return  booksPropertiesProxy.getRenewalDay();
   }



}
