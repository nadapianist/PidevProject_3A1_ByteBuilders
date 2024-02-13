package tn.esprit.services;

import tn.esprit.entities.Activity;
import tn.esprit.entities.Challenge;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {



    void add(T t) throws SQLException;

    void addd(T t) throws SQLException;
    void update(T t) throws  SQLException;

    void delete(T t) throws  SQLException;

    List<T> diplayList() throws  SQLException;

}
