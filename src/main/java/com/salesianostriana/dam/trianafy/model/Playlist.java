package com.salesianostriana.dam.trianafy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Playlist {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @NaturalId
    private String idPlay;

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private List<Song> songs = new ArrayList<>();


    public void addSong(Song song) {
        songs.add(song);
    }

    public void deleteSong(Song song) {
        songs.remove(song);
    }

    public Playlist (String name, String description){
        this.name = name;
        this.description = description;
    }

    public boolean equals(Playlist p){
        if (this == p) return true;
        if (p == null || getClass() != p.getClass()) return false;
        Playlist playlist = (Playlist) p;
        return Objects.equals(idPlay, playlist.idPlay);
    }

    public int hashCode(){
        return Objects.hash(idPlay);
    }


}
