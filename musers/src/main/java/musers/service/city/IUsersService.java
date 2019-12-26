package musers.service.city;




import musers.model.user.Users;

import java.util.List;


public interface IUsersService  {

    Users findUser(Long id);
    Users findUser(String email);

    List<Users> findAll();

    void save(Users user);

    void updateRole(Long idUser, String newRole);

    String getUserName(Long id);









}