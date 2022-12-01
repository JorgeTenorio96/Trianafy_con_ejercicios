package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlaylistDtoConverter {


    private SongDtoConverter dtoConverter;

    public Playlist createPlaylistDtotoPlaylist(CreatePlaylistDTO c) {
        return new Playlist(
                c.getName(),
                c.getDescription()
        );
    }
    public GetPlaylistDTO playlistToGetPlaylistDTO(Playlist playlist){
        int numberOfSongs;
        if(playlist.getSongs()==null){
            numberOfSongs = 0;
        }else{
            numberOfSongs = playlist.getSongs().size();
        }
        return GetPlaylistDTO
                .builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .numberOfSongs(playlist.getSongs().size())
                .build();
    }
    public SongPlaylistDTO of(Playlist playlist){
        return SongPlaylistDTO.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .description(playlist.getDescription())
                .songDTOS(playlist.getSongs().stream().filter(song -> song.getId() != null).map(s -> dtoConverter.songToGetSongDTO(s)).collect(Collectors.toList())).build();

    }}
