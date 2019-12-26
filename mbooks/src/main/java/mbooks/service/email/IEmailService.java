package mbooks.service.email;




import mbooks.model.Email;
import mbooks.technical.email.EmailReturnWrapper;
import mbooks.technical.email.EmailWrapper;

import java.util.List;

public interface IEmailService {
    List<Email> findAll();
    Email find(String name);
    void save(Email email);
    void sendRevival(List<EmailWrapper> emailList);
    void sendReturn(EmailReturnWrapper pEmail);


}
