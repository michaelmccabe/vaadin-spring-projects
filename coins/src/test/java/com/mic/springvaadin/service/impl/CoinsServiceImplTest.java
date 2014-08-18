package com.mic.springvaadin.service.impl;

import static org.easymock.EasyMock.anyObject;

import org.junit.rules.ExpectedException;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import com.mic.springvaadin.dao.CoinsDao;
import com.mic.springvaadin.dao.model.Coin;
import com.mic.springvaadin.service.CoinsException;

public class CoinsServiceImplTest {

	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	private CoinsDao coinsDao;

	CoinsServiceImpl underTest = new CoinsServiceImpl();
	
	
	@Test
	public void tooLargeAmountThrowsExcepttionMessage() throws Exception {
		
		// setup
		List<Coin> coins = getPreparedCoins();

		coinsDao = createMock(CoinsDao.class);
		expect(coinsDao.getCoins()).andReturn(coins);
		replay(coinsDao);
		underTest.setCoinsDao(coinsDao);
		expectedException.expect(CoinsException.class);
		expectedException.expectMessage("amount too large");
		
		// exercise
		underTest.getNumberOfPermutations(9999999);
		
	}

	@Test
	public void getNumberOfPermutationsUsesRepo() throws Exception {

		// setup
		List<Coin> coins = getPreparedCoins();

		coinsDao = createMock(CoinsDao.class);
		expect(coinsDao.getCoins()).andReturn(coins);
		replay(coinsDao);
		underTest.setCoinsDao(coinsDao);

		// exercise
		int result = underTest.getNumberOfPermutations(200);

		// verify
		assertEquals(73682,result );
		verify(coinsDao);

	}

	private List<Coin> getPreparedCoins() {
		List<Coin> coins = new ArrayList<>();
		Coin coin1 = new Coin("1p", 1);
		coin1.setId(1);
		Coin coin2 = new Coin("2p", 2);
		coin1.setId(2);
		Coin coin5 = new Coin("5p", 5);
		coin1.setId(3);
		Coin coin10 = new Coin("10p", 10);
		coin1.setId(4);
		Coin coin20 = new Coin("20p", 20);
		coin1.setId(5);
		Coin coin50 = new Coin("50p", 50);
		coin1.setId(6);
		Coin coin100 = new Coin("1 pound", 100);
		coin1.setId(7);
		Coin coin200 = new Coin("2 pound", 200);
		coin1.setId(8);
		coins.add(coin200);
		coins.add(coin100);
		coins.add(coin50);
		coins.add(coin20);
		coins.add(coin10);
		coins.add(coin5);
		coins.add(coin2);
		coins.add(coin1);
		return coins;
	}

	@Test
	public void addCoinsUsesDao() throws Exception {

		// setup
		List<Coin> coins = new ArrayList<>();
		Coin coin20 = new Coin("20p", 20);
		coins.add(coin20);

		coinsDao = createMock(CoinsDao.class);
		coinsDao.saveCoin(coin20);
		replay(coinsDao);
		underTest.setCoinsDao(coinsDao);

		// exercise
		underTest.addCoins(coins);

		// verify
		verify(coinsDao);
	}

	@Test
	public void removeCoinsUsesDao() throws Exception {

		List<Coin> coins = getPreparedCoins();
		coinsDao = createMock(CoinsDao.class);

		// don't care about the individual cons but must remove all
		coinsDao.removeCoin((Coin) anyObject());
		expectLastCall().times(coins.size());
		replay(coinsDao);
		underTest.setCoinsDao(coinsDao);

		// exercise
		underTest.removeCoins(coins);


		// verify
		verify(coinsDao);

	}
	
	@Test
	public void getCoinUsesDao() throws Exception {
		List<Coin> coins = getPreparedCoins();
		coinsDao = createMock(CoinsDao.class);

		// don't care about the individual cons but must remove all
		expect(coinsDao.getCoin(1)).andReturn(coins.get(0));

		replay(coinsDao);
		underTest.setCoinsDao(coinsDao);

		// exercise
		Coin coin = underTest.getCoin(1);


		// verify
		assertEquals(coin,coins.get(0) );
		verify(coinsDao);	
		
		
	}

}
