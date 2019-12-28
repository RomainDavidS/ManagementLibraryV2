package musers.service;


import musers.model.Users;

import java.util.List;


public interface IUsersService  {

    Users findUser(Long id);
    Users findUser(String email);
    String getUserName(Long id);
    List<Users> findAll();

}