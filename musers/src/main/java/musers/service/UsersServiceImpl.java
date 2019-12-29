package musers.service;

import musers.model.Users;
import musers.repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Gestion des utilisateurs
 */
@Service
public class UsersServiceImpl implements IUsersService {

    @Autowired
    private IUsersRepository usersRepository;


    /**
     * Permet la recherche d'un utilisateur
     * @param id Identifiant de l'utilisateur à rechercher
     * @return Entity users si il a été trouvé
     */
    public Users findUser(Long id){
        return usersRepository.findById(id).orElse(null);
    }

    /**
     * Permet la recherche d'un utilisateur
     * @param email Email de l'utilisateur à rechercher
     * @return Entity users si il a été trouvé
     */
    public Users findUser(String email){return usersRepository.findByEmailAndActiveTrue( email );}


    public String getUserName(Long id){
        Users users =  findUser( id) ;

        return  users.getFirstName()+ " "+ users.getLastName();
    }

    public List<Users> findAll(){
        return usersRepository.findAll();
    }
}