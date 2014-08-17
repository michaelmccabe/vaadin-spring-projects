package com.mic.springvaadin.backend;

import java.util.List;

import com.mic.springvaadin.backend.model.Article;

public interface DefaultService {

	List<Article> getArticles();

	void saveArticle(Article article);

	Article getArticle(int id);

}
