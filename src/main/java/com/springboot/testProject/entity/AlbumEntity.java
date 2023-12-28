package com.springboot.testProject.entity;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "ALBUM")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumEntity extends RepresentationModel<AlbumEntity> {
	@Id
	@Column(name = "ID_ALBUM")
	private Integer idAlbum;

	@OneToMany(mappedBy = "albumEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PhotoEntity> photoList;
}
