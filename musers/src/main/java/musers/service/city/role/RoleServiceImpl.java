package musers.service.city.role;


import musers.config.ApplicationPropertiesConfig;
import musers.model.user.Role;
import musers.repository.user.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Gestion des roles utilisateur
 */
@Service
public class RoleServiceImpl implements IRoleService {


    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    private ApplicationPropertiesConfig appPropertiesConfig;

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public void save(Role role){
        roleRepository.save( role );
    }

    /**
     * cherche un role utilisateur
     * @param id Id du role utilisateur à chercher
     * @return Le role utilisateur (entity)Role
     */
    public Role findRole(Long id) {
        return roleRepository.findRoleById( id );
    }

    /**
     * cherche un role utilisateur
     * @param name Nom du role utilisateur
     * @return Le role utilisateur (entity)Role
     */
    public Role findRole(String name) {
        return roleRepository.findRoleByName( name );
    }

    /**
     * Converti (entity) Role par defaut en ArraysList
     * @return ArraysList (entity) Role
     */
    public List<Role> findListRole(){
        return Arrays.asList( roleRepository.findRoleByName( appPropertiesConfig.getRole() ) );
    }

    /**
     * Converti (entity) Role en ArraysList
     * @param id Id du role à convertir
     * @return ArraysList (entity) Role
     */
    private List<Role> findListRole(Long id){
        return Arrays.asList( roleRepository.findRoleById( id ) );
    }

    /**
     * Cherche un role
     * @param name Nom du role
     * @return ArraysList (entity) Role
     */
    private List<Role> findListRole(String name){
        return Arrays.asList( roleRepository.findRoleByName( name ) );
    }


}
