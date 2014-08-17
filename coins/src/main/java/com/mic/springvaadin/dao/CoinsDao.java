package com.mic.springvaadin.dao;

import java.util.List;

import com.mic.springvaadin.dao.model.Coin;

public interface CoinsDao {

	List<Coin> getCoins();

	Coin getCoin(Integer id);

	void saveCoin(Coin coin);

	void removeCoin(Coin coin);

}
