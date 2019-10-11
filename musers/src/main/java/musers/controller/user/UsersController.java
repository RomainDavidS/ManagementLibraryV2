package musers.controller.user;


import musers.controller.dto.user.UserCreateDto;
import musers.controller.dto.user.UserUpdateDto;
import musers.exceptions.ResourceNotFoundException;
import musers.model.user.Users;
import musers.service.user.IUsersService;
import musers.technical.dto.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private IUsersService usersService;

    @GetMapping("/all")
    public List<Users> usersList(){

        List<Users> usersList = usersService.findAll();
        if (usersList.isEmpty()) throw new ResourceNotFoundException( "Aucun utilisateur trouvé");

        return usersList;
    }

    @GetMapping(value = "/connection/{id}")
    public Users user(@PathVariable String  id){

        return usersService.findUser( id );

    }



    @PostMapping
    public void newUser(@DTO(UserCreateDto.class) Users user) {
        usersService.save( user );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@DTO(UserUpdateDto.class) Users user ){
        usersService.save( user );
    }



}