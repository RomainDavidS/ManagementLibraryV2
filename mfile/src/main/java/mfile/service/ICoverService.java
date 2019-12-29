package mfile.service;

import mfile.model.Cover;

import java.util.List;

public interface ICoverService {

    Cover find(String id );
    List<Cover> findAll();

}
