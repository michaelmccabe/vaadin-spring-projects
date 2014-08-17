package com.mic.springvaadin.ui;

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
import com.mic.springvaadin.service.CoinsService;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CoinsViewTest {

	private final CoinsView underTest = new CoinsView();

	private Button calculateButton;

	private Button removeCoinButton;
	private VerticalLayout verticalLayout;
	private HorizontalLayout horizontalLayout;
	private Label resultLabel;
	private Table table;

	@Before
	public void setup() {

		List<Coin> coins = getCoinsForTest();

		CoinsService coinsService = createMock(CoinsService.class);
		expect(coinsService.getCoins()).andReturn(coins);
		replay(coinsService);
		underTest.setCoinsService(coinsService);

		underTest.init();
		verticalLayout = (VerticalLayout) underTest.getComponent(1);

		horizontalLayout = (HorizontalLayout) underTest.getComponent(0);
		
		table = (Table) horizontalLayout.getComponent(0);
				
		VerticalLayout buttons = (VerticalLayout) horizontalLayout
				.getComponent(1);

		calculateButton = (Button) verticalLayout.getComponent(1);
		resultLabel = (Label) verticalLayout.getComponent(2);
		removeCoinButton = (Button) buttons.getComponent(0);

	}

	private List<Coin> getCoinsForTest() {
		List<Coin> coins = new ArrayList<>();
		Coin coin1 = new Coin("1p", 1);
		coin1.setId(1);
		Coin coin2 = new Coin("2p", 2);
		coin1.setId(2);
		Coin coin20 = new Coin("20p", 20);
		coin1.setId(3);
		coins.add(coin20);
		coins.add(coin2);
		coins.add(coin1);
		return coins;
	}

	@Test
	public void checkViewSetupCorrectly() throws Exception {

		assertEquals(2, underTest.getComponentCount());
		assertEquals(3, verticalLayout.getComponentCount());
		assertEquals(2, horizontalLayout.getComponentCount());
		assertEquals(3, table.getItemIds().size()); // 3 coins added in setup

		TextField amountField = (TextField) verticalLayout.getComponent(0);
		assertEquals("amount (in pence)", amountField.getCaption());
		// ensure it's preloaded with 200
		assertEquals("200", amountField.getValue());

		assertEquals("calculate", calculateButton.getCaption());
		assertEquals("remove coin", removeCoinButton.getCaption());

	}

	@Test
	public void calculateButtonShouldCallService() throws Exception {
		// setup
		CoinsService coinsService = createMock(CoinsService.class);

		expect(coinsService.getNumberOfPermutations(200)).andReturn(11);
		replay(coinsService);
		underTest.setCoinsService(coinsService);

		// exercise
		calculateButton.click();

		// verify
		assertEquals("11", resultLabel.getValue());
		verify(coinsService);

	}
	
	@Test
	public void removeCoinButtonShouldCallService() throws Exception {
		// setup
		List<Coin> coins = getCoinsForTest();
		
		List<Coin> coinsToRemove =new ArrayList<>();
		coinsToRemove.add(coins.get(0));
		CoinsService coinsService = createMock(CoinsService.class);
		expect(coinsService.getCoin(1)).andReturn(coins.get(0));
		
		expect(coinsService.getCoins()).andReturn(coins);
		coinsService.removeCoins(coinsToRemove);
		replay(coinsService);
		underTest.setCoinsService(coinsService);
		
		table.select(1);

		// exercise
		removeCoinButton.click();

		// verify
		verify(coinsService);

	}

}
