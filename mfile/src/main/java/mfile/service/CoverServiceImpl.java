package mfile.service;



import mfile.model.Cover;
import mfile.repository.ICoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CoverServiceImpl implements ICoverService {

    @Autowired
    private ICoverRepository coverRepository;

    /**
     * Permet la récupération de toutes les images de couvertures
     * @return La liste de tous les images de couverture
     */
    public List<Cover> findAll(){ return coverRepository.findAll() ;}



    /**
     * permet la récherche l'image de la couverture d'un livre
     * @param id Identifiant de l'image à rechercher
     * @return Entity cover
     */
    public Cover find(String id ) {
        return coverRepository.findById( id ).orElse(null);
    }



}
