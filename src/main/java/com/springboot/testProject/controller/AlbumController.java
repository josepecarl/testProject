package com.springboot.testProject.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.testProject.entity.AlbumEntity;
import com.springboot.testProject.entity.PhotoEntity;
import com.springboot.testProject.exception.DuplicateAlbumException;
import com.springboot.testProject.exception.ErrorPassingParameter;
import com.springboot.testProject.service.AlbumService;

import jakarta.validation.constraints.NotNull;

@RestController
public class AlbumController {

	@Autowired
	private AlbumService albumService;

	@RequestMapping(path = "/photo", method = RequestMethod.POST, consumes = {
	      MediaType.MULTIPART_FORM_DATA_VALUE }, produces = "application/vnd.api+json")
	public ResponseEntity<CollectionModel<PhotoEntity>> postPhotos(@RequestPart @NotNull MultipartFile file,
	      @RequestParam @NotNull String name, @RequestParam @NotNull Integer idAlbum)
	      throws ErrorPassingParameter, NotFoundException {
		List<PhotoEntity> listPhotos = albumService.createPhoto(file, name, idAlbum);
		listPhotos.forEach(c -> {
			c.add(linkTo(AlbumController.class).slash("/photo").slash(c.getIdPhoto()).withRel("Photos"));
		});
		return new ResponseEntity<CollectionModel<PhotoEntity>>(
		      CollectionModel.of(listPhotos,
		            Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(), LinkRelation.of("self"))),
		      HttpStatus.CREATED);
	}

	@PostMapping(value = "/album", produces = "application/vnd.api+json")
	public ResponseEntity<CollectionModel<AlbumEntity>> postAlbum(@RequestParam @NotNull String id)
	      throws ErrorPassingParameter, DuplicateAlbumException {
		List<AlbumEntity> listAlbum = albumService.createAlbum(id);
		listAlbum.forEach(c -> {
			c.add(linkTo(AlbumController.class).slash("/album").slash(c.getIdAlbum()).withRel("Albums"));
		});
		return new ResponseEntity<CollectionModel<AlbumEntity>>(
		      CollectionModel.of(listAlbum,
		            Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(), LinkRelation.of("self"))),
		      HttpStatus.CREATED);
	}

	@GetMapping(value = "/albums", produces = "application/vnd.api+json")
	public ResponseEntity<CollectionModel<AlbumEntity>> getAlbums() throws ErrorPassingParameter, DuplicateAlbumException {
		List<AlbumEntity> listAlbum = albumService.getAlbums();
		listAlbum.forEach(c -> {
			c.add(linkTo(AlbumController.class).slash("/album").slash(c.getIdAlbum()).withRel("Albums"));
		});
		return ResponseEntity.ok(CollectionModel.of(listAlbum,
		      Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(), LinkRelation.of("self"))));
	}

	@GetMapping(value = "/photo/{id}", produces = "application/vnd.api+json")
	public ResponseEntity<EntityModel<PhotoEntity>> getPhoto(@PathVariable Integer id)
	      throws ErrorPassingParameter, DuplicateAlbumException, NotFoundException {
		return ResponseEntity.ok(EntityModel.of(albumService.getPhoto(id),
		      Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(), LinkRelation.of("self"))));
	}

}