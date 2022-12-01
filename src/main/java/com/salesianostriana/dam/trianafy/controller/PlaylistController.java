package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.CreatePlaylistDTO;
import com.salesianostriana.dam.trianafy.dto.GetPlaylistDTO;
import com.salesianostriana.dam.trianafy.dto.PlaylistDtoConverter;
import com.salesianostriana.dam.trianafy.dto.SongPlaylistDTO;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
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
public class PlaylistController {

    private final PlaylistRepository playrepo;
    private final PlaylistDtoConverter dtoConverter;

    private final PlaylistService playService;

    private final SongRepository songrepo;


    @Operation(summary = "Petición que devuelve todas las Playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todos las playlist",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPlaylistDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                  {
                                                      "id": 12,
                                                      "name": "Random",
                                                      "numberOfSongs": 4
                                                  }
                                            ]                                       
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist",
                    content = @Content),
    })

    @GetMapping("/list/")
    public ResponseEntity <List<GetPlaylistDTO>> findAllPlaylist(){
        List<Playlist> data = playrepo.findAll();
        if(data.isEmpty()){
            return ResponseEntity.notFound().build();
        }else  {
            List<GetPlaylistDTO> result = data.stream()
                    .map(GetPlaylistDTO::of)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(result);
        }

    }
    @Operation(summary = "Petición que devuelve una Playlist proporcionada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la Playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class),
                            examples = {@ExampleObject(
                                    value = """
                                                  
                                                {
                                                 "id": 12,
                                                 "name": "Random",
                                                 "description": "Una lista muy loca",
                                                 "songs": [
                                                     {
                                                         "id": 9,
                                                         "title": "Enter Sandman",
                                                         "album": "Metallica",
                                                         "year": "1991",
                                                         "artist": {
                                                             "id": 3,
                                                             "name": "Metallica"
                                                         }
                                                     },
                                                     {
                                                         "id": 8,
                                                         "title": "Love Again",
                                                         "album": "Future Nostalgia",
                                                         "year": "2021",
                                                         "artist": {
                                                             "id": 2,
                                                             "name": "Dua Lipa"
                                                         }
                                                     },
                                                     {
                                                         "id": 9,
                                                         "title": "Enter Sandman",
                                                         "album": "Metallica",
                                                         "year": "1991",
                                                         "artist": {
                                                             "id": 3,
                                                             "name": "Metallica"
                                                         }
                                                     },
                                                     {
                                                         "id": 11,
                                                         "title": "Nothing Else Matters",
                                                         "album": "Metallica",
                                                         "year": "1991",
                                                         "artist": {
                                                             "id": 3,
                                                             "name": "Metallica"
                                                         }
                                                     }
                                                 ]
                                             }
                                                                                            
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la playlist por el ID",
                    content = @Content),
    })

    @GetMapping("/list/{id}")
    public ResponseEntity <Playlist> findPlaylistById(@PathVariable Long id){

        return ResponseEntity.of(playrepo.findById(id));

    }
    @Operation(summary = "Petición que borra una playlist proporcionada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la playlist por el ID",
                    content = @Content),
    })


    @DeleteMapping("/list/{id}")
    public ResponseEntity<?> deletePlaylist (@PathVariable Long id){
        if (playrepo.existsById(id))
            playrepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @Operation(summary = "Petición que Agrega una playlist con su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado la Playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreatePlaylistDTO.class),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                     "name": "La mejor lista",
                                                     "description": "Esta lista es la mejor de todas sin duda",
                                                     "id": 13
                                                 }
                                            ]                                       
                                            """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha agregado la Playlist",
                    content = @Content),
    })
    @PostMapping("/list/")
    public ResponseEntity<CreatePlaylistDTO> createPlaylist(@RequestBody CreatePlaylistDTO dto){
        Playlist newPlaylist = dtoConverter.createPlaylistDtotoPlaylist(dto);

        playrepo.save(newPlaylist);

        CreatePlaylistDTO dtoCreateList = new CreatePlaylistDTO(newPlaylist.getId(), newPlaylist.getName(), newPlaylist.getDescription());

        return ResponseEntity.status(HttpStatus.CREATED).body(dtoCreateList);
    }

    @Operation(summary = "Petición que edita una playlist en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado la playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlaylistDTO.class),
                            examples = {@ExampleObject(
                                    value = """
                                            
                                                {
                                                     "id": 12,
                                                     "name": "La lista mas aburrida",
                                                     "numberOfSongs": 5
                                                 }
                                                                                      
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la playlist por el ID",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Se ha encontrado algún error en los datos",
                    content = @Content)
    })

    @PutMapping("/list/{id}")
    public ResponseEntity<GetPlaylistDTO> editPlaylist(@RequestBody CreatePlaylistDTO dto, @PathVariable Long id){
        Playlist playlist = playrepo.findById(id).map(old ->{
            old.setName(dto.getName());
            old.setDescription(dto.getDescription());
            return playrepo.save(old);
        }).orElse(null);

        if (playlist == null){
            return ResponseEntity.notFound().build();
        }
        GetPlaylistDTO getPlaylistDTO = dtoConverter.playlistToGetPlaylistDTO(playlist);
                return ResponseEntity.ok(getPlaylistDTO);
    }
    @Operation(summary = "Petición que agrega una canción a la playlist en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlaylistDTO.class),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                  "id": 12,
                                                  "name": "Random",
                                                  "description": "Una lista muy loca",
                                                  "songs": [
                                                      {
                                                          "id": 9,
                                                          "title": "Enter Sandman",
                                                          "album": "Metallica",
                                                          "year": "1991",
                                                          "artist": {
                                                              "id": 3,
                                                              "name": "Metallica"
                                                          }
                                                      },
                                                      {
                                                          "id": 8,
                                                          "title": "Love Again",
                                                          "album": "Future Nostalgia",
                                                          "year": "2021",
                                                          "artist": {
                                                              "id": 2,
                                                              "name": "Dua Lipa"
                                                          }
                                                      },
                                                      {
                                                          "id": 9,
                                                          "title": "Enter Sandman",
                                                          "album": "Metallica",
                                                          "year": "1991",
                                                          "artist": {
                                                              "id": 3,
                                                              "name": "Metallica"
                                                          }
                                                      },
                                                      {
                                                          "id": 11,
                                                          "title": "Nothing Else Matters",
                                                          "album": "Metallica",
                                                          "year": "1991",
                                                          "artist": {
                                                              "id": 3,
                                                              "name": "Metallica"
                                                          }
                                                      },
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
                                                  ]
                                              }                                          
                                            """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha agregado la canción",
                    content = @Content),
    })

    @PostMapping("/list/{idList}/song/{idSong}")
    public ResponseEntity<Playlist> addSongToPlaylist(@PathVariable("idList") Long idList, @PathVariable("idSong") Long idSong){
        if(!playrepo.existsById(idList) && !songrepo.existsById(idSong)){
            return ResponseEntity.notFound().build();
        }else{
        Playlist playlist = playrepo.findById(idList).orElse(null);
        Song song = songrepo.findById(idSong).orElse(null);
        playlist.addSong(song);
        playrepo.save(playlist);

        return ResponseEntity.ok(playlist);

    }}
    @Operation(summary = "Petición que obtiene todas las canciones de una playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todos las canciones",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPlaylistDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                  "id": 12,
                                                  "name": "La lista mas aburrida",
                                                  "description": "Me aburro",
                                                  "songs": [
                                                      {
                                                          "id": 9,
                                                          "title": "Enter Sandman",
                                                          "album": "Metallica",
                                                          "year": "1991",
                                                          "artist": {
                                                              "id": 3,
                                                              "name": "Metallica"
                                                          }
                                                      },
                                                      {
                                                          "id": 8,
                                                          "title": "Love Again",
                                                          "album": "Future Nostalgia",
                                                          "year": "2021",
                                                          "artist": {
                                                              "id": 2,
                                                              "name": "Dua Lipa"
                                                          }
                                                      },
                                                      {
                                                          "id": 9,
                                                          "title": "Enter Sandman",
                                                          "album": "Metallica",
                                                          "year": "1991",
                                                          "artist": {
                                                              "id": 3,
                                                              "name": "Metallica"
                                                          }
                                                      },
                                                      {
                                                          "id": 11,
                                                          "title": "Nothing Else Matters",
                                                          "album": "Metallica",
                                                          "year": "1991",
                                                          "artist": {
                                                              "id": 3,
                                                              "name": "Metallica"
                                                          }
                                                      },
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
                                                  ]
                                              }                                        
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna cancion",
                    content = @Content),
    })
    @GetMapping("/list/{id}/song/")
    public ResponseEntity <Playlist> getSongsFromPlaylist(@PathVariable Long id){

        return ResponseEntity.of(playrepo.findById(id));

    }
    @Operation(summary = "Petición que obtiene las canciones de una playlist proporcionada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlaylistDTO.class),
                            examples = {@ExampleObject(
                                    value = """
                                            
                                                {
                                                     "id": 12,
                                                     "name": "La lista mas aburrida",
                                                     "description": "Me aburro",
                                                     "songs": [
                                                         {
                                                             "id": 9,
                                                             "title": "Enter Sandman",
                                                             "album": "Metallica",
                                                             "year": "1991",
                                                             "artist": {
                                                                 "id": 3,
                                                                 "name": "Metallica"
                                                             }
                                                         },
                                                         {
                                                             "id": 8,
                                                             "title": "Love Again",
                                                             "album": "Future Nostalgia",
                                                             "year": "2021",
                                                             "artist": {
                                                                 "id": 2,
                                                                 "name": "Dua Lipa"
                                                             }
                                                         },
                                                         {
                                                             "id": 9,
                                                             "title": "Enter Sandman",
                                                             "album": "Metallica",
                                                             "year": "1991",
                                                             "artist": {
                                                                 "id": 3,
                                                                 "name": "Metallica"
                                                             }
                                                         },
                                                         {
                                                             "id": 11,
                                                             "title": "Nothing Else Matters",
                                                             "album": "Metallica",
                                                             "year": "1991",
                                                             "artist": {
                                                                 "id": 3,
                                                                 "name": "Metallica"
                                                             }
                                                         },
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
                                                     ]
                                                 }
                                                                                      
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna canción de la playlist por el ID",
                    content = @Content),
    })
    @GetMapping("/list/{idList}/song/{idSong}")
    public ResponseEntity<Playlist> getSongToPlaylist(@PathVariable("idList") Long idList, @PathVariable("idSong") Long idSong){
        if(!playrepo.existsById(idList) && !songrepo.existsById(idSong)){
            return ResponseEntity.notFound().build();
        }else{
            Playlist playlist = playrepo.findById(idList).orElse(null);
            Song song = songrepo.findById(idSong).orElse(null);


            return ResponseEntity.ok(playlist);

        }}
    @Operation(summary = "Petición que borra una canción de la playlist proporcionada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la canción por el ID",
                    content = @Content),
    })
    @DeleteMapping("/list/{idList}/song/{idSong}")
    public ResponseEntity<Playlist> deleteSongFromPlaylist(@PathVariable("idList") Long idList, @PathVariable("idSong") Long idSong){
        if(playrepo.existsById(idList) & songrepo.existsById(idSong)){
            playService.findById(idList).get().getSongs().removeAll(songrepo.findById(idSong).stream().toList());
            playService.add(playService.findById(idList).get());
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }



}
