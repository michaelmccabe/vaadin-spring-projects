package com.mic.springvaadin.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mic.springvaadin.dao.CoinsDao;
import com.mic.springvaadin.dao.model.Coin;
import com.mic.springvaadin.service.CoinsService;

@Service
public class CoinsServiceImpl implements CoinsService {

	@Autowired
	private CoinsDao coinsDao;

	private List<Coin> coins;

	@Override
	public int getNumberOfPermutations(int amount) {

		coins = coinsDao.getCoins();

		return iterateOverCoinsAndReturnTargetValue(amount);
	}

	private int iterateOverCoinsAndReturnTargetValue(int targetAmount) {

		Iterator<Coin> iter = coins.iterator();
		int[] permutation = new int[targetAmount + 1];
		
		// there's only 1 way to get 0, that's no coins
		permutation[0] = 1;

		while (iter.hasNext()) {
			int coinValue = iter.next().getValue();
			
			// increase amount a penny at a time and iterate over whole array
			// the last element in the array permutation[] is the answer returned
			// but in effect every answer from 0 - targetAmount is stored in permutation[] 
			for (int amount = coinValue; amount <= targetAmount; amount++)
				permutation[amount] += permutation[amount - coinValue];
		}

		return permutation[targetAmount];
	}

	@PostConstruct
	void init() {
		addDefaultEnglishCoins();
	}

	private void addDefaultEnglishCoins() {
		String[] coinNames = { "1p", "2p", "5p", "10p", "20p", "50p",
				"1 Pound", "2 Pound" };
		int[] coinValues = { 1, 2, 5, 10, 20, 50, 100, 200 };

		List<Coin> coins = new ArrayList();

		for (int i = 0; i < coinValues.length; i++) {
			Coin coin = new Coin(coinNames[i], coinValues[i]);
			coins.add(coin);
		}
		addCoins(coins);
	}

	@Override
	public void removeCoins(List<Coin> coins) {

		for (Coin coin : coins) {
			coinsDao.removeCoin(coin);
		}

	}

	@Override
	public void addCoins(List<Coin> coins) {
		for (Coin coin : coins) {
			coinsDao.saveCoin(coin);
		}
	}

	@Override
	public List<Coin> getCoins() {
		return coinsDao.getCoins();
	}

	@Override
	public Coin getCoin(Integer id) {
		return coinsDao.getCoin(id);
	}

	public void setCoinsDao(CoinsDao coinsDao) {
		this.coinsDao = coinsDao;

	}

}
