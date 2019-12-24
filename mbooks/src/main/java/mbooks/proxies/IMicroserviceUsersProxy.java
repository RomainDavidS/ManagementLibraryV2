package mbooks.proxies;



import mbooks.beans.musers.user.UsersBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "zuul-server",contextId = "usersProxy")
@RibbonClient(name = "microservice-users")
@RequestMapping("/microservice-users/user")
public interface IMicroserviceUsersProxy {


    @GetMapping(value = "/byId/{id}")
    UsersBean user(@PathVariable("id") Long id);

    @GetMapping(value = "/byEmail/{id}")
    UsersBean user(@PathVariable("id") String id);


}
