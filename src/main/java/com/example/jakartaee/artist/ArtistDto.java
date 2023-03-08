package com.example.jakartaee.artist;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ArtistDto {

    private Long id;
    @NotNull(message = "can't be null")
    @Size(min = 1)
    String name;
    @NotNull(message = "can't be null")
    @Size(min = 1)
    String recordTitle;
    @NotNull(message = "can't be null")
    @Size(min = 1)
    String genre;

    public ArtistDto(){}

    public ArtistDto(Artist artist) {
        this.id = artist.getId();
        this.name = artist.getName();
        this.recordTitle = artist.getRecordTitle();
        this.genre = artist.getGenre();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecordTitle() {
        return recordTitle;
    }

    public void setRecordTitle(String recordTitle) {
        this.recordTitle = recordTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
