package mfile.repository;





import mfile.model.Cover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Initialisation de l'entity Photo
 */
@Repository
public interface ICoverRepository extends JpaRepository<Cover, String> {


    List<Cover> findAllByUseIs(String use);
}
