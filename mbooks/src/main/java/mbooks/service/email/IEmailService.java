package mbooks.service.email;




import mbooks.model.Email;
import mbooks.technical.email.EmailReturnWrapper;
import mbooks.technical.email.EmailWrapper;

import java.util.List;

public interface IEmailService {
    Email find(String name);
    void sendRevival(List<EmailWrapper> emailList);
    void sendReturn(EmailReturnWrapper pEmail);
}
