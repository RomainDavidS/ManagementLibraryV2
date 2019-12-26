package musers.controller.user;


import musers.controller.dto.user.UserCreateDto;
import musers.controller.dto.user.UserUpdateDto;
import musers.exceptions.ResourceNotFoundException;
import musers.model.user.Users;
import musers.service.city.IUsersService;
import musers.technical.dto.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UsersController implements HealthIndicator {

    @Autowired
    private IUsersService usersService;

    @GetMapping("/all")
    public List<Users> usersList(){

        List<Users> usersList = usersService.findAll();
        if (usersList.isEmpty()) throw new ResourceNotFoundException( "Aucun utilisateur trouvé");

        return usersList;
    }

    @GetMapping(value = "/byId/{id}")
    public Users user(@PathVariable Long  id){
        return usersService.findUser( id );
    }

    @GetMapping("/name/{id}")
    public String getUserName(@PathVariable Long  id){
        return usersService.getUserName( id );
    }

    @GetMapping(value = "/byEmail/{id}")
    public Users user(@PathVariable String  id){
        return usersService.findUser( id );
    }



    @GetMapping(value = "/id/{id}")
    public Long idUser(@PathVariable String  id){
        return usersService.findUser( id ).getId();
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

    @Override
    public Health health() {
        List<Users> usersList = usersService.findAll();

        if(usersList.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }
}