package com.mic.springvaadin.dao.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mic.springvaadin.dao.model.Coin;
import com.mic.springvaadin.dao.model.CoinRepository;

public class CoinsDaoImplTest {

	CoinsDaoImpl underTest = new CoinsDaoImpl();
	List<Coin> coins = new ArrayList<>();;

	@Before
	public void setup() {
		Coin coin = new Coin("1p", 1);
		coins.add(coin);
	}

	@Test
	public void saveCoinCallsRepository() throws Exception {

		// setup
		CoinRepository repository;
		Coin coin = coins.get(0);
		repository = createMock(CoinRepository.class);
		expect(repository.save(coin)).andReturn(coin);
		replay(repository);
		// just a quick note on this -
		// I'm using easymock so popped a setter for the sake of the test..
		// Google's mockito allows you to inject mocks without this explicit
		// declaration
		underTest.setRepository(repository);

		// exercise
		underTest.saveCoin(coin);

		// verify
		verify(repository);
	}

	@Test
	public void removeCoinUsesRepo() throws Exception {
		// setup
		CoinRepository repository;
		Coin coin = coins.get(0);
		repository = createMock(CoinRepository.class);
		repository.delete(coin);
		replay(repository);
		underTest.setRepository(repository);

		// exercise
		underTest.removeCoin(coin);

		// verify
		verify(repository);

	}

	@Test
	public void getAllCoinsUsesRepo() throws Exception {
		// setup
		CoinRepository repository = createMock(CoinRepository.class);
		expect(repository.findAll()).andReturn(coins);
		replay(repository);
		underTest.setRepository(repository);

		// exercise
		List<Coin> gotCoins = underTest.getCoins();

		// verify
		verify(repository);
		assertEquals(gotCoins, coins);

	}
}
