package musers.controller;


import musers.model.Users;
import musers.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UsersController implements HealthIndicator {

    @Autowired
    private IUsersService usersService;

    @GetMapping("/byId/{id}")
    public Users user(@PathVariable Long  id){
        return usersService.findUser( id );
    }

    @GetMapping("/byEmail/{id}")
    public Users user(@PathVariable String  id){
        return usersService.findUser( id );
    }

    @GetMapping("/name/{id}")
    public String getUserName(@PathVariable Long  id){
        return usersService.getUserName( id );
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