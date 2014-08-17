package com.mic.springvaadin.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Article {

	@Id
	// @GeneratedValue - dn't want it as a generated value for this demo
	private Integer id;
	@Column(length = 40)
	private String headline;
	@Column(length = 200)
	private String previewText;
	@Column(length = 40000)
	private String fullText;

	public Article() {
	}

	public Article(String headline, String previewText, String fullText) {
		super();
		this.headline = headline;
		this.previewText = previewText;
		this.fullText = fullText;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getPreviewText() {
		return previewText;
	}

	public void setPreviewText(String previewText) {
		this.previewText = previewText;
	}

	public String getFullText() {
		return fullText;
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

}
