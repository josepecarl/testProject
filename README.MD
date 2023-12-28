## Table of Contents
1. [General Info](#general-info)
2. [Technologies](#technologies)
3. [Installation](#installation)
4. [EndPoints](#EndPoints)

### General Info
***
This project check knowledge of java-spring-rest. It loads photos and albums. 

## Technologies
***
A list of technologies used within the project:
* Spring Boot
* liquibase
* Spring Boot
* h2database
* lombok
* Junit

## EndPoints
***

* /photo (POST): 
  * In: File, nameFile, IdAlbum
  * Out: List of all photos, not only created photo.

* /album (POST)
  * In: idAlbum
  * Out: List of all albums, not only created album.
   
* /albums (GET)
   * In: Nothing
   * Out: List of all albums, not only created album.
   
* /photo/{id} (GET)
   * In: Picture's id
   * Out: Picture entity.