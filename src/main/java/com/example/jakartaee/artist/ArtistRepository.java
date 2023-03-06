package com.example.jakartaee.artist;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class ArtistRepository {

    @PersistenceContext
    EntityManager entityManager;


    //C
    public void insertArtist(Artist artist){
        entityManager.persist(artist);
    }

    //R
    public Optional<Artist> findOne(Long id){
        return Optional.ofNullable(entityManager.find(Artist.class, id));
    }

    @SuppressWarnings("unchecked")
    public List<Artist> findAll(){
        var query = entityManager.createQuery("select a from Artist a");
        return (List<Artist>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Artist> findAllByName(String name){
        var query = entityManager.createQuery(("select a from Artist a where a.name like :name"));
        query.setParameter("name", name);
        return (List<Artist>) query.getResultList();
    }

    //U
    public Artist updateArtist(Long id, Artist artist){
        var entity = entityManager.find(Artist.class, id);
        entity.setName(artist.getName());
        entity.setRecordTitle(artist.getRecordTitle());
        entity.setGenre(artist.getGenre());
        entityManager.persist(entity);
        return entity;
    }

    //D
    public void deleteArtist(Long id){
        var artist = findOne(id);
        artist.ifPresent(a -> entityManager.remove(a));
    }
}
