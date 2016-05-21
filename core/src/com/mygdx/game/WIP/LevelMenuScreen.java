package com.mygdx.game.WIP;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.mygdx.game.screen.AbstractScreen;

public class LevelMenuScreen extends AbstractScreen {
  // TODO: Not currently using this, just a demo.

  /**
   * The spacing between the heading the options.
   */
  protected final float TITLE_SPACE = 60;

  /**
   * The spacing between the options.
   */
  protected final float OPTION_SPACE = 20;

  protected Stage stage;
  protected Table table;
  protected List<String> list;
  protected ScrollPane scrollPane;
  protected Skin skin;
  protected static final String SKIN_PATH = "skin/uiskin.json";

  protected BitmapFont optionFont;
  protected BitmapFont headingFont;
  protected BitmapFont buttonFont;
  protected static final int OPTION_FONT_SIZE = 30;
  protected static final int HEADING_FONT_SIZE = 80;
  protected static final int BUTTON_FONT_SIZE = 60;
  protected static final String FONT_PATH = "font/CRAYON__.TTF";

  @Override
  public void dispose() {
    super.dispose();
  }

  @Override
  public void render(float delta) {
    super.render(delta);
  }

  @Override
  public void show() {
    super.show();

    // Setup the stage
    stage = new Stage();
    Gdx.input.setInputProcessor(stage);

    // Setup the table
    table = new Table();

    // Setup skin
    skin = new Skin(Gdx.files.internal(SKIN_PATH));

    // Setup fonts
    final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
        Gdx.files.internal(FONT_PATH));
    final FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    parameter.size = HEADING_FONT_SIZE;
    headingFont = generator.generateFont(parameter);
    parameter.size = OPTION_FONT_SIZE;
    optionFont = generator.generateFont(parameter);
    optionFont.setColor(Color.GRAY);
    parameter.size = BUTTON_FONT_SIZE;
    buttonFont = generator.generateFont(parameter);
    generator.dispose();

    // Setup label styles
    final LabelStyle optionLabelStyle = new LabelStyle();
    optionLabelStyle.font = optionFont;
    final LabelStyle headingLabelStyle = new LabelStyle();
    headingLabelStyle.font = headingFont;
    final TextButtonStyle buttonStyle = new TextButtonStyle();
    buttonStyle.font = buttonFont;
    final ListStyle listStyle = new ListStyle();
    listStyle.font = optionFont;

    list = new List<String>(listStyle);

    scrollPane = new ScrollPane(list);

    final TextButton playButton = new TextButton("Play", buttonStyle);
    playButton.padLeft(OPTION_SPACE);

    final TextButton backButton = new TextButton("Back", buttonStyle);

  }

}
