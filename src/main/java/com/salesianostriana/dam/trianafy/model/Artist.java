package com.salesianostriana.dam.trianafy.model;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
@Builder
public class Artist {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @NaturalId
    private String dni;

    public boolean equals(Artist a){
        if (this == a) return true;
        if (a == null || getClass() != a.getClass()) return false;
        Artist artist = (Artist) a;
        return Objects.equals(dni, artist.dni);
    }
    public int hashCode(){
        return Objects.hash(dni);
    }

}
