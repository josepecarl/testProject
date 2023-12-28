package com.springboot.testProject.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.mock.web.MockMultipartFile;

import com.springboot.testProject.entity.AlbumEntity;
import com.springboot.testProject.entity.PhotoEntity;
import com.springboot.testProject.exception.ErrorPassingParameter;
import com.springboot.testProject.repository.AlbumRepository;
import com.springboot.testProject.repository.PhotoRepository;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {

	private final static String FILE_NAME = "FILE";
	private final static MockMultipartFile FILE_PART = new MockMultipartFile("file", null, "application/json",
	      "{\"name\": \"File Name\"}".getBytes());
	private final static Integer ID_ALBUM = 2;
	private final static Integer ID_PHOTO = 2;
	private final static String ID_ALBUM_STR = "2";

	@Mock
	private PhotoRepository photoRepository;

	@Mock
	private AlbumRepository albumRepository;

	@InjectMocks
	private AlbumService albumService;

	@Test
	public void createPhotoTest() throws ErrorPassingParameter, NotFoundException {
		List<PhotoEntity> listPhotoEntity = new ArrayList<PhotoEntity>();
		listPhotoEntity.add(PhotoEntity.builder().idPhoto(ID_ALBUM).build());
		Optional<AlbumEntity> albunOp = Optional.of(AlbumEntity.builder().idAlbum(ID_ALBUM).build());
		Mockito.when(albumRepository.findById(ID_ALBUM)).thenReturn(albunOp);
		Mockito.when(photoRepository.findAll()).thenReturn(listPhotoEntity);
		List<PhotoEntity> listPhotoEntityOut = albumService.createPhoto(FILE_PART, FILE_NAME, ID_ALBUM);
		assertEquals(1, listPhotoEntityOut.size());
		assertEquals(ID_ALBUM, listPhotoEntityOut.get(0).getIdPhoto());
	}

	@Test
	public void createAlbumTest() throws ErrorPassingParameter, NotFoundException {
		List<AlbumEntity> listAlbumEntity = new ArrayList<AlbumEntity>();
		listAlbumEntity.add(AlbumEntity.builder().idAlbum(ID_ALBUM).build());
		Mockito.when(albumRepository.findById(ID_ALBUM)).thenReturn(Optional.empty());
		Mockito.when(albumRepository.findAll()).thenReturn(listAlbumEntity);
		List<AlbumEntity> listAlbumEntityOut = albumService.createAlbum(ID_ALBUM_STR);
		assertEquals(1, listAlbumEntityOut.size());
		assertEquals(ID_ALBUM, listAlbumEntityOut.get(0).getIdAlbum());
	}

	@Test
	public void getAlbumsTest() {
		List<AlbumEntity> listAlbumEntity = new ArrayList<AlbumEntity>();
		listAlbumEntity.add(AlbumEntity.builder().idAlbum(ID_ALBUM).build());
		Mockito.when(albumRepository.findAll()).thenReturn(listAlbumEntity);
		List<AlbumEntity> listAlbum = albumService.getAlbums();
		assertEquals(1, listAlbum.size());
		assertEquals(ID_ALBUM, listAlbum.get(0).getIdAlbum());
	}

	@Test
	public void getPhotoTest() throws NotFoundException {
		Mockito.when(photoRepository.findById(ID_PHOTO))
		      .thenReturn(Optional.of(PhotoEntity.builder().idPhoto(ID_PHOTO).build()));
		PhotoEntity photoEntity = albumService.getPhoto(ID_PHOTO);
		assertEquals(ID_PHOTO, photoEntity.getIdPhoto());
	}
}