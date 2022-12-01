package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.dto.CreateSongDTO;
import com.salesianostriana.dam.trianafy.dto.GetSongDTO;
import com.salesianostriana.dam.trianafy.dto.SongDtoConverter;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongRepository songrepo;

    private final SongDtoConverter dtoConverter;

    private final ArtistRepository artrepo;

    private final SongService songService;

    private final ArtistRepository artistRepository;

    @Operation(summary = "Petición que obtiene todos las canciones existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todos las canciones",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Song.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                                                        
                                                [
                                                        {
                                                             "id": 4,
                                                             "title": "19 días y 500 noches",
                                                             "artistName": "Joaquín Sabina",
                                                             "album": "19 días y 500 noches",
                                                             "year": "1999"
                                                         },
                                                         {
                                                             "id": 5,
                                                             "title": "Donde habita el olvido",
                                                             "artistName": "Joaquín Sabina",
                                                             "album": "19 días y 500 noches",
                                                             "year": "1999"
                                                         },
                                                         {
                                                             "id": 6,
                                                             "title": "A mis cuarenta y diez",
                                                             "artistName": "Joaquín Sabina",
                                                             "album": "19 días y 500 noches",
                                                             "year": "1999"
                                                         },
                                                         {
                                                             "id": 7,
                                                             "title": "Don't Start Now",
                                                             "artistName": "Dua Lipa",
                                                             "album": "Future Nostalgia",
                                                             "year": "2019"
                                                         },
                                                         {
                                                             "id": 8,
                                                             "title": "Love Again",
                                                             "artistName": "Dua Lipa",
                                                             "album": "Future Nostalgia",
                                                             "year": "2021"
                                                         },
                                                         {
                                                             "id": 9,
                                                             "title": "Enter Sandman",
                                                             "artistName": "Metallica",
                                                             "album": "Metallica",
                                                             "year": "1991"
                                                         },
                                                         {
                                                             "id": 10,
                                                             "title": "Unforgiven",
                                                             "artistName": "Metallica",
                                                             "album": "Metallica",
                                                             "year": "1991"
                                                         },
                                                         {
                                                             "id": 11,
                                                             "title": "Nothing Else Matters",
                                                             "artistName": "Metallica",
                                                             "album": "Metallica",
                                                             "year": "1991"
                                                         }
                                                ]
                                                                                      
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna canción",
                    content = @Content),
    })

    @GetMapping("/song/")
    public ResponseEntity<List<GetSongDTO>> findAllSongs() {
        List<Song> data = songrepo.findAll();
        if(data.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            List<GetSongDTO> result = data.stream()
                    .map(GetSongDTO::of)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(result);
        }
    }
    @Operation(summary = "Petición que obtiene una canción proporcionada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class),
                            examples = {@ExampleObject(
                                    value = """
                                                                                        
                                            {
                                                 "id": 4,
                                                 "title": "19 días y 500 noches",
                                                 "album": "19 días y 500 noches",
                                                 "year": "1999",
                                                 "artist": {
                                                     "id": 1,
                                                     "name": "Joaquín Sabina"
                                                 }
                                             }
                                                                                      
                                            """
                            )}
                    )}),

            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la canción por el ID",
                    content = @Content),
    })
    @GetMapping("/song/{id}")
    public ResponseEntity<Song> findSongById(@PathVariable Long id){

        return ResponseEntity.of(songrepo.findById(id));
    }
    @Operation(summary = "Petición que borra una canción proporcionada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))})
    })
    @DeleteMapping("/song/{id}")
    public ResponseEntity<?> deleteSong (@PathVariable Long id) {
        if (songrepo.existsById(id))
            songrepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @Operation(summary = "Petición que agrega una canción proporcionada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetSongDTO.class),
                            examples = {@ExampleObject(
                                    value = """
                                                                                        
                                                {
                                                 "id": 13,
                                                 "title": "Canción Nueva 3",
                                                 "artistName": "Metallica",
                                                 "album": "2022",
                                                 "year": "Album de la cancion nueva 3"
                                                }
                                                                                      
                                            """
                            )}
                    )}),

            @ApiResponse(responseCode = "400",
                    description = "No se ha agregado la canción",
                    content = @Content),
    })
    @PostMapping("/song/")
    public ResponseEntity<GetSongDTO> createSong(@RequestBody CreateSongDTO dto){
        if (dto.getArtistId() == null){
            return ResponseEntity.badRequest().build();
        }

        Song newSong = dtoConverter.createSongDtotoSong(dto);

        Artist artist = artrepo.findById(dto.getArtistId()).orElse(null);

        newSong.setArtist(artist);
        songrepo.save(newSong);

        GetSongDTO getSongDTO = dtoConverter.songToGetSongDTO(newSong);

        return ResponseEntity.status(HttpStatus.CREATED).body(getSongDTO);
    }
    @Operation(summary = " Petición que edita una canción proporcionada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetSongDTO.class),
                            examples = {@ExampleObject(
                                    value = """
                                                  
                                                   {
                                                       "id": 4,
                                                       "title": "titulo edit",
                                                       "artistName": "Dua Lipa",
                                                       "album": "album edit",
                                                       "year": 1999
                                                   }
                                                                                          
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la canción por el ID",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Se ha encontrado algún error en los datos",
                    content = @Content)
    })
    @PutMapping("/song/{id}")
    public ResponseEntity<GetSongDTO> editSong(@RequestBody CreateSongDTO dto, @PathVariable Long id){
        if(dto.getArtistId() == null){
            return ResponseEntity.badRequest().build();
        }

        Artist artist = artrepo.findById(dto.getArtistId()).orElse(null);

        Song song = songrepo.findById(id).map(old -> {
            old.setTitle(dto.getTitle());
            old.setAlbum(dto.getAlbum());
            old.setYear(dto.getYear());
            old.setArtist(artist);
            return (songrepo.save(old));
        }).orElse(null);

        if(song == null){
            return ResponseEntity.notFound().build();
        }

        GetSongDTO getSongDTO = dtoConverter.songToGetSongDTO(song);


        return ResponseEntity.ok(getSongDTO);
    }







}
