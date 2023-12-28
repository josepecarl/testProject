package com.springboot.testProject.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.testProject.entity.AlbumEntity;
import com.springboot.testProject.entity.PhotoEntity;
import com.springboot.testProject.repository.AlbumRepository;
import com.springboot.testProject.repository.PhotoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AlbumIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PhotoRepository photoRepository;

	@MockBean
	private AlbumRepository albumRepository;

	@Test
	public void createAlbum() throws Exception {
		Integer albumParamsIn = 2;
		Mockito.when(albumRepository.findById(albumParamsIn)).thenReturn(Optional.empty());
		Mockito.when(albumRepository.save(Mockito.any())).thenReturn(AlbumEntity.builder().idAlbum(2).build());
		Mockito.when(albumRepository.findAll()).thenReturn(new ArrayList<AlbumEntity>());
		mockMvc
		      .perform(MockMvcRequestBuilders.post("/album").param("id", "2")
		            .contentType("application/vnd.api+json").accept("application/vnd.api+json"))
		      .andExpect(status().isCreated());
	}

	@Test
	public void createPhoto() throws Exception {
		MockMultipartFile photoFile = new MockMultipartFile("file", null, "application/json",
		      "{\"name\": \"File Name\"}".getBytes());
		Optional<AlbumEntity> optionalAlbumEntity = Optional.of(AlbumEntity.builder().idAlbum(2).build());
		Mockito.when(albumRepository.findById(2)).thenReturn(optionalAlbumEntity);
		Mockito.when(photoRepository.save(Mockito.any())).thenReturn(PhotoEntity.builder().idPhoto(null).build());
		Mockito.when(albumRepository.findAll()).thenReturn(new ArrayList<AlbumEntity>());
		mockMvc.perform(multipart("/photo").file(photoFile).param("name", "nameFile").param("idAlbum", "2"))
		      .andExpect(status().isCreated());
	}

	@Test
	void getAlbums() throws Exception {
		mockMvc.perform(get("/albums").contentType("application/json")).andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}