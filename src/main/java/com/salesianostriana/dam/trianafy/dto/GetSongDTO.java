package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetSongDTO {



    private Long id;
    private String title;
    private String artistName;
    private String album;
    private String year;

    public static GetSongDTO of(Song s){
        String artistName;

        if(s.getArtist() == null){
            artistName = "null";
        }else {
            artistName = s.getArtist().getName();
        }

        return GetSongDTO
                .builder()
                .id(s.getId())
                .title(s.getTitle())
                .album(s.getAlbum())
                .year(s.getYear())
                .artistName(artistName)
                .build();

    }

}
