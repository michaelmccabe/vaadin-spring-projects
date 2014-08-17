package com.mic.springvaadin.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mic.springvaadin.dao.CoinsDao;
import com.mic.springvaadin.dao.model.Coin;
import com.mic.springvaadin.dao.model.CoinRepository;

@Service
public class CoinsDaoImpl implements CoinsDao {

	@Autowired
	private CoinRepository repository;

	@Override
	public List<Coin> getCoins() {
		return (List<Coin>) repository.findAll();
	}

	@Override
	public void saveCoin(Coin coin) {
		repository.save(coin);

	}

	public void setRepository(CoinRepository repository) {
		this.repository = repository;

	}

	@Override
	public void removeCoin(Coin coin) {
		repository.delete(coin);

	}

	@Override
	public Coin getCoin(Integer id) {
		return repository.findOne(id);
	}

}
