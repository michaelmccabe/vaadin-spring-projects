package com.mic.springvaadin.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.mic.springvaadin.dao.model.Coin;
import com.mic.springvaadin.service.CoinsService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Configurable
public class CoinsView extends VerticalLayout implements View {

	@Autowired
	transient CoinsService coinsService;

	private static final long serialVersionUID = 1L;
	private VerticalLayout buttonLayout;
	private Button calculateButton;
	private Button removeCoinButton;

	private VerticalLayout verticalLayout;
	private HorizontalLayout horizontalLayout;
	private Label resultLabel;
	private TextField amountField;

	private Table table;

	@PostConstruct
	void init() {
		setSizeFull();
		setMargin(true);
		setSpacing(true);

		verticalLayout = new VerticalLayout();

		amountField = new TextField("amount (in pence)");
		amountField.setValue("200");
		calculateButton = new Button("calculate");
		resultLabel = new Label("reuslt");
		resultLabel.setValue("output goes here");

		verticalLayout.addComponents(amountField, calculateButton,
				resultLabel);

		horizontalLayout = new HorizontalLayout();

		table = new Table("coins");
		table.setHeight("200");
		table.setWidth("200");
		table.setSelectable(true);
		table.setImmediate(true);
		table.addContainerProperty("Name", String.class, null);
		table.addContainerProperty("Value in pence", Integer.class, null);

		buttonLayout = new VerticalLayout();
		removeCoinButton = new Button("remove coin");

		buttonLayout.addComponent(removeCoinButton);

		horizontalLayout.addComponents(table, buttonLayout);

		this.addComponents(horizontalLayout, verticalLayout);

		setUpButtonAndTableHandlers();

		populateTable();

	}

	private void populateTable() {
		List<Coin> coins = coinsService.getCoins();

		table.removeAllItems();
		for (Coin coin : coins) {
			String name = coin.getName();
			Integer value = coin.getValue();
			table.addItem(new Object[] { name, value }, coin.getId());
		}

	}

	private void setUpButtonAndTableHandlers() {

		calculateButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				Integer result = coinsService.getNumberOfPermutations(Integer
						.parseInt(amountField.getValue()));
				resultLabel.setValue(result.toString());

			}
		});

		removeCoinButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				Integer currentId = (Integer) table.getValue();

				if (currentId != null) {
					List<Coin> coins = new ArrayList<>();
					coins.add(coinsService.getCoin(currentId));

					coinsService.removeCoins(coins);
					populateTable();
				}

			}
		});

	}

	@Override
	public void attach() {
		super.attach();
	}

	@Override
	public void detach() {
		super.detach();
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
	}

	public void setCoinsService(CoinsService coinsService) {
		this.coinsService = coinsService;

	}

}
