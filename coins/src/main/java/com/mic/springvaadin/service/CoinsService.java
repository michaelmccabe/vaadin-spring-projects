package com.mic.springvaadin.service;

import java.util.List;

import com.mic.springvaadin.dao.model.Coin;

public interface CoinsService {

	void addCoins(List<Coin> coins);

	Coin getCoin(Integer id);

	void removeCoins(List<Coin> coins);

	List<Coin> getCoins();

	int getNumberOfPermutations(int pence) throws CoinsException;

}
