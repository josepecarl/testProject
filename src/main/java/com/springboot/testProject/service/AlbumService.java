package com.springboot.testProject.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.testProject.entity.AlbumEntity;
import com.springboot.testProject.entity.PhotoEntity;
import com.springboot.testProject.exception.DuplicateAlbumException;
import com.springboot.testProject.exception.ErrorPassingParameter;
import com.springboot.testProject.repository.AlbumRepository;
import com.springboot.testProject.repository.PhotoRepository;

import jakarta.transaction.Transactional;

@Component
public class AlbumService {

	@Autowired
	private PhotoRepository photoRepository;

	@Autowired
	private AlbumRepository albumRepository;

	@Transactional
	public List<PhotoEntity> createPhoto(MultipartFile file, String name, Integer idAlbum)
	      throws ErrorPassingParameter, NotFoundException {
		Optional<AlbumEntity> albumOp = albumRepository.findById(idAlbum);
		if (albumOp.isEmpty()) {
			throw new NotFoundException();
		}
		try {
			PhotoEntity photoEntity = PhotoEntity.builder().name(name).payload(file.getBytes())
			      .albumEntity(albumRepository.findById(idAlbum).get()).build();
			photoRepository.save(photoEntity);
			return photoRepository.findAll();
		} catch (IOException e) {
			throw new ErrorPassingParameter();
		}
	}

	@Transactional
	public List<AlbumEntity> createAlbum(String id) throws DuplicateAlbumException {
		Optional<AlbumEntity> opAlbum = albumRepository.findById(Integer.valueOf(id));
		if (opAlbum.isEmpty()) {
			albumRepository.save(AlbumEntity.builder().idAlbum(Integer.valueOf(id)).build());
			return albumRepository.findAll();
		} else
			throw new DuplicateAlbumException();
	}

	public List<AlbumEntity> getAlbums() {
		return albumRepository.findAll();
	}

	public PhotoEntity getPhoto(Integer id) throws NotFoundException {
		Optional<PhotoEntity> op = photoRepository.findById(id);
		if (op.isEmpty()) {
			throw new NotFoundException();
		} else
			return op.get();
	}
}