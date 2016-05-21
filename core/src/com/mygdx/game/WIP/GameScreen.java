package com.mygdx.game.WIP;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.SaSGame;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.screen.MenuScreen;

public class GameScreen extends AbstractScreen {
  // TODO: a very rough class, just an example

  private World world;
  private Box2DDebugRenderer debugRenderer;
  private OrthographicCamera camera;

  /**
   * Gravity of the world measured in Newtons.
   */
  private static final Vector2 GRAVITY = new Vector2(0, -9.81f);

  private static final float TIME_STEP = 1 / 60f;
  private static final int VELOCITY_ITERATIONS = 8, POSITION_INTERATIONS = 3;

  @Override
  public void render(float delta) {
    super.render(delta);

    debugRenderer.render(world, camera.combined);

    world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_INTERATIONS);
  }

  @Override
  public void resize(int width, int height) {
    camera.viewportWidth = width / 20;
    camera.viewportHeight = height / 20;
    camera.update();
  }

  @Override
  public void show() {
    super.show();

    Gdx.input.setInputProcessor(new InputController() {
      @Override
      public boolean keyDown(int keycode) {
        if (keycode == Keys.ESCAPE) {
          SaSGame.getInstance().setScreen(new MenuScreen());
        }
        return true;
      }
    });

    // The world
    world = new World(GRAVITY, true);
    debugRenderer = new Box2DDebugRenderer();
    camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);

    BodyDef ballDef = new BodyDef();
    ballDef.type = BodyType.DynamicBody;
    ballDef.position.set(0, 1);

    CircleShape shape = new CircleShape();
    shape.setRadius(.25f);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 2.5f;
    fixtureDef.friction = .25f;
    fixtureDef.restitution = .75f;

    world.createBody(ballDef).createFixture(fixtureDef);

    shape.dispose();

    // The ground
  }

  @Override
  public void dispose() {
    world.dispose();
    debugRenderer.dispose();
    super.dispose();
  }

}
