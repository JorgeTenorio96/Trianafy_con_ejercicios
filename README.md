# Proyecto Trianafy

Proyecto realizado por Jorge Miguel Tenorio Rodríguez

## Enlace a la Documentación generada con Swagger

http://localhost:8080/DocJORGE.html

## Descripción del proyecto

Este proyecto se ha realizado con la intención de crear una sencilla aplicación con la que el usuario podrá ver, crear, editar y borrar diferentes canciones, artistas y listas de reproducción que se encuentren en la propia aplicación o que él mismo cree y añada.

Todo esto se logra a través de diferentes peticiones que uno puede hacer sobre la aplicación. Con las peticiones de tipo GET, el usuario podrá ver tanto el contenido que ya existe en la aplicación (Los artistas, las canciones y las listas de reproducción con las canciones que contienen) como el que él haya generado al añadir o modificar dichos campos.

El usuario puede, además, añadir nuevos artistas, canciones y listas de reproducción a la aplicación mediante peticiones de tipo POST.

Mediante peticiones de tipo PUT, el usuario puede editar el contenido ya existente o creado por él en la aplicación, como modificar el nombre de un artista, el álbum de una canción o el nombre de una lista de reproducción.

Por último, el usuario puede eliminar contenido de la aplicación mediante peticiones de tipo DELETE, donde podrá eliminar desde un artista hasta una sola canción que se encuentre en una lista de repoducción.

El proyecto se ha realizado con Spring Boot en el lenguaje Java.

## Funcionalidades

Artistas:

- Ver todos los artistas
- Ver un artista en concreto
- Añadir un nuevo artista
- Editar un artista
- Borrar un artista

Canciones:

- Ver todas las canciones
- Ver una canción en concreto
- Añadir una nueva canción
- Editar una canción
- Borrar una canción

Listas de reproducción

- Ver todas las listas de reproducción
- Ver una lista de repoducción en concreto
- Añadir una nueva lista de reproducción
- Editar una lista de reroducción
- Borrar una lista de reproducción (no elimina las canciones que contiene)
- Añadir una canción a una lista de reproducción
- Borrar una canción de una lista de reproducción
