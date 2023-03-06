package com.example.jakartaee.artist;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ArtistMapper {
    public List<ArtistDto> map(List<Artist> all){
        return all.stream().map(ArtistDto::new).toList();
    }

    public Artist map (ArtistDto artist){
        var a = new Artist();
        a.setId(artist.getId());
        a.setName(artist.getName());
        a.setRecordTitle(artist.getRecordTitle());
        a.setGenre(artist.getGenre());
        return a;
    }

    public ArtistDto map(Artist artist){
        return new ArtistDto(artist);
    }
}

