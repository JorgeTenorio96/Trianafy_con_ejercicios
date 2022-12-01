package com.salesianostriana.dam.trianafy.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.trianafy.views.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Objects;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    @JsonView(View.Base.class)
    private String title;
    @JsonView(View.Base.class)
    private String album;
    @JsonView(View.Base.class)
    @Column(name = "year_of_song")
    private String year;

    @ManyToOne(fetch = FetchType.EAGER)
    private Artist artist;

    @NaturalId
    private String idSong;



    public Song (String title, String album, String year){
        this.title = title;
        this.album = album;
        this.year = year;
    }


    public boolean equals(Song s){
        if (this == s) return true;
        if (s == null || getClass() != s.getClass()) return false;
        Song song = (Song) s;
        return Objects.equals(idSong, song.idSong);
    }
    public int hashCode(){
        return Objects.hash(idSong);
    }


}
