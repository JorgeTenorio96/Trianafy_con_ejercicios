package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistRepository artrepo;
    private final SongRepository songrepo;


    @Operation(summary = "Petición que agrega un artista con su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado el artista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class),
                            examples = {@ExampleObject(
                                    value = """
                                            
                                                {"id": 1, "name": "Joaquín Sabina"}
                                                                                      
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha agregado el artista",
                    content = @Content),
    })
    @PostMapping("/artist/")
    public ResponseEntity<Artist> newArtist(@RequestBody Artist artist){
        return ResponseEntity.status(HttpStatus.CREATED).body(artrepo.save(artist));
    }
    @Operation(summary = "Petición que obtiene todos los Artistas existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todos los artistas",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artist.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                    {
                                                        "id": 1,
                                                        "name": "Joaquín Sabina"
                                                    },
                                                    {
                                                        "id": 2,
                                                        "name": "Dua Lipa"
                                                    },
                                                    {
                                                        "id": 3,
                                                        "name": "Metallica"
                                                    }
                                            ]                                       
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun Artista",
                    content = @Content),
    })
    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> findAllArtist() {
        return ResponseEntity.ok(artrepo.findAll());
    }
    @Operation(summary = "Petición que obtiene un artista especificado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado al artista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class),
                            examples = {@ExampleObject(
                                    value = """
                                            
                                                {"id": 1, "name": "Joaquín Sabina"}
                                                                                      
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el artista por el ID",
                    content = @Content),
    })
    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> findArtistById(@PathVariable Long id){
        return ResponseEntity.of(artrepo.findById(id));
    }
    @Operation(summary = "Petición que edita un artista proporcionado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado el artista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class),
                            examples = {@ExampleObject(
                                    value = """
                                            
                                                {"id": 1, "name": "Artista Modificado"}
                                                                                      
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el artista por el ID",
                    content = @Content),
    })
    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> editArtist(@RequestBody Artist artist, @PathVariable Long id){
        return ResponseEntity.of(artrepo.findById(id).map(old ->{
            old.setName(artist.getName());
            return (artrepo.save(old));
        }));
    }
    @Operation(summary = "Petición que borra un artista proporcionado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado el artista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el artista por el ID",
                    content = @Content),
    })
    @DeleteMapping("/artist/{id}")
    public ResponseEntity<?> deleteArtist (@PathVariable Long id){
        List<Song> songList = songrepo.findAll();
        Artist artist  = artrepo.findById(id).orElse(null);

        if (artrepo.existsById(id)){
            for(int i = 0; i < songList.size(); i++){

                if(songList.get(i).getArtist() == artist){
                    songList.get(i).setArtist(null);
                }
            }
        }
            artrepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
