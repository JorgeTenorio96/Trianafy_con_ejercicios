package com.salesianostriana.dam.trianafy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreatePlaylistDTO {
    private Long Id;
    private String name;
    private String description;
}
