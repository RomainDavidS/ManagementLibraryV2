package com.library.service.mfile;


import com.library.beans.mbooks.cover.CoverBean;
import com.library.proxies.ICoverProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Gestion du téléchargement des images et du caroussel d'image de la page d'accueil
 *
 */
@Service
public class CoverServiceImpl implements ICoverService {
    @Autowired
    private ICoverProxy coverProxy;

    public CoverBean findCover(String id){

        return coverProxy.find(id);
    }


}
