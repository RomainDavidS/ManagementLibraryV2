package musers.repository;


import musers.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * JPA Initialisation de l'entity Role
 */
@Repository
public interface IRoleRepository extends JpaRepository<Role, Long > {

}
