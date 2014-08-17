package com.mic.springvaadin.backend.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mic.springvaadin.backend.DefaultService;
import com.mic.springvaadin.backend.model.Article;
import com.mic.springvaadin.backend.model.ArticleRepository;

@Service
public class DefaultServiceImpl implements DefaultService {

	@Autowired
	private ArticleRepository repository;

	@Override
	public List<Article> getArticles() {
		return (List<Article>) repository.findAll();
	}

	@Override
	public void saveArticle(Article article) {

		repository.save(article);

	}

	@Override
	public Article getArticle(int id) {

		return repository.findOne(id);
	}
}
