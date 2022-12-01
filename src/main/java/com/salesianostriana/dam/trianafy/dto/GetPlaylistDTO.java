package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Playlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPlaylistDTO {

    private Long id;
    private String name;
    private int numberOfSongs;

    public static GetPlaylistDTO of (Playlist playlist){
        return GetPlaylistDTO
                .builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .numberOfSongs(playlist.getSongs().size())
                .build();
    }

}
