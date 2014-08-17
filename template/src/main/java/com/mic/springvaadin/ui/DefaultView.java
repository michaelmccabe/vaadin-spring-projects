package com.mic.springvaadin.ui;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.mic.springvaadin.backend.DefaultService;
import com.mic.springvaadin.backend.model.Article;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Configurable
public class DefaultView extends VerticalLayout implements View {

	@Autowired
	transient DefaultService defaultService;

	private static final long serialVersionUID = 1L;
	private HorizontalLayout horizontalLayout;
	private VerticalLayout verticalLayout;
	private TextArea articleBody;
	private TextField headline;
	private TextField previewText;
	private Button clearText;
	private Button saveText;
	private Button gotoView;

	@PostConstruct
	void init() {
		setSizeFull();
		setMargin(true);
		setSpacing(true);

		gotoView = new Button("Go to other view", new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(Button.ClickEvent event) {

				List<Article> articles = defaultService.getArticles();
				((AspectJManagedUI) getUI())
						.setLabel("Default View 2: number of articles saved: "
								+ articles.size());
				getUI().getNavigator().navigateTo("default2");

			}
		});
		addComponent(gotoView);

		headline = new TextField("headline");
		headline.setInputPrompt("enter some text");
		previewText = new TextField("preview text");
		previewText.setInputPrompt("enter some text");
		addComponent(headline);
		addComponent(previewText);

		horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);

		articleBody = new TextArea("article text");

		articleBody.setInputPrompt("enter some text");
		articleBody.setWidth("300px");
		articleBody.setHeight("200px");
		horizontalLayout.addComponent(articleBody);

		VerticalLayout verticalLayout = new VerticalLayout();

		clearText = new Button("clear text", new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(Button.ClickEvent event) {
				articleBody.setValue("");
			}
		});

		saveText = new Button("save text", new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(Button.ClickEvent event) {
				Article article = new Article(headline.getValue(),
						previewText.getValue(), articleBody.getValue());
				article.setId(1);
				defaultService.saveArticle(article);
			}
		});

		verticalLayout.addComponent(saveText);
		verticalLayout.addComponent(clearText);

		Article article = defaultService.getArticle(1);
		if (article != null) {
			headline.setValue(article.getHeadline());
			previewText.setValue(article.getPreviewText());
			articleBody.setValue(article.getFullText());

		}

		horizontalLayout.addComponent(verticalLayout);
		addComponent(horizontalLayout);

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

}
