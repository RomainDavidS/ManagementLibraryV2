package musers.service.user;


import musers.config.ApplicationPropertiesConfig;
import musers.exceptions.ResourceNotFoundException;
import musers.model.user.Role;
import musers.model.user.Users;
import musers.repository.user.IUsersRepository;
import musers.service.user.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Gestion des utilisateurs
 */
@Service
public class UsersServiceImpl implements IUsersService {

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private ApplicationPropertiesConfig appPropertiesConfig;

    @Autowired
    private IRoleService roleService;



    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    /**
     * Permet la recherche d'un utilisateur
     * @param id Identifiant de l'utilisateur à rechercher
     * @return Entity users si il a été trouvé
     */
    public Users findUser(Long id){
        return usersRepository.findById(id) .orElseThrow(
                () -> new ResourceNotFoundException("Utilisateur non trouvé avec l'id " + id ) );
    }
    /**
     * Permet la recherche d'un utilisateur
     * @param email Email de l'utilisateur à rechercher
     * @return Entity users si il a été trouvé
     */
    public Users findUser(String email){return usersRepository.findByEmailAndActiveTrue( email );}

    /**
     * (Réservé à l'administreur)
     * On recherche la liste de tous les utilisateurs
     * @return La liste des utilisateurs
     */
    public List<Users> findAll(){
        return usersRepository.findAll();
    }


    public void save( Users user){
        if(user.getId() == null){
            user.setActive( appPropertiesConfig.isActive() );
            user.setPassword(passwordEncoder.encode(user.getPassword() ) );
            user.setRoleList( roleService.findListRole()  );
        }

        usersRepository.save( user );
    }


    /**
     * Réservé à l'administrateur :
     * Modification du role de l'utilisateur
     * @param idUser Id de l'utilisateur
     * @param newRole Nouveau role à affecter
     */
    public void updateRole(Long idUser,String newRole){

        Users user = usersRepository.getOne( idUser );
        Role role = roleService.findRole( "ROLE_"+ newRole);
        user.getRoleList().clear();
        user.getRoleList().add( role );
        usersRepository.save( user );
    }

    public String getUserName(Long id){
        Users users =  findUser( id) ;

        return  users.getFirstName()+ " "+ users.getLastName();
    }

}