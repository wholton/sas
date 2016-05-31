package com.mygdx.game.workinprogress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.screen.MainMenuScreen;

public class GameScreen extends AbstractScreen {
  
  private World world;
  private Box2DDebugRenderer debugRenderer;
  private OrthographicCamera camera;

  /**
   * Gravity of the world measured in Newtons.
   */
  private static final Vector2 GRAVITY = new Vector2(0, -9.81f);

  private static final float TIME_STEP = 1 / 60f;
  private static final int VELOCITY_ITERATIONS = 8;
  private static final int POSITION_INTERATIONS = 3;
  
  // TODO: this could be clearer
  private static final float ZOOM = 25;
  
  private Body box;
  private Vector2 movement = new Vector2();
  private static final float SPEED = 500;

  @Override
  public void render(float delta) {
    super.render(delta);

    world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_INTERATIONS);
    
    box.applyForceToCenter(movement, true);
    
    camera.position.set(box.getPosition().x, box.getPosition().y, 0);
    camera.update();
    
    debugRenderer.render(world, camera.combined);
  }

  @Override
  public void resize(int width, int height) {
    camera.viewportWidth = width / ZOOM;
    camera.viewportHeight = height / ZOOM;
  }

  @Override
  public void show() {
    super.show();

    world = new World(GRAVITY, true);
    debugRenderer = new Box2DDebugRenderer();
    camera = new OrthographicCamera();

    BodyDef bodyDef = new BodyDef();
    
    
    // Box
    bodyDef.type = BodyType.DynamicBody;
    bodyDef.position.set(2.25f, 10f);
    
    PolygonShape polygonShape = new PolygonShape();
    polygonShape.setAsBox(.5f, .5f);
    
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = polygonShape;
    fixtureDef.friction = .75f;
    fixtureDef.restitution = .1f;
    fixtureDef.density = 5f;
    
    box = world.createBody(bodyDef);
    box.createFixture(fixtureDef);
    
    polygonShape.dispose();
    
    //box.applyAngularImpulse(500, true);
    
    // Ball
    bodyDef.type = BodyType.DynamicBody;
    //bodyDef.position.set(5f, 5f);

    CircleShape circleShape = new CircleShape();
    circleShape.setPosition(new Vector2(0, 1f));
    circleShape.setRadius(.25f);

    fixtureDef.shape = circleShape;
    fixtureDef.density = 2.5f;
    fixtureDef.friction = .25f;
    fixtureDef.restitution = .75f;

    box.createFixture(fixtureDef);

    circleShape.dispose();

    // Ground
    bodyDef.type = BodyType.StaticBody;
    bodyDef.position.set(0, 0);
    
    ChainShape groundShape = new ChainShape();
    groundShape.createChain(new Vector2[] { new Vector2(-50, 0), new Vector2(50, 0) });
    
    fixtureDef.shape = groundShape;
    fixtureDef.friction = .5f;
    fixtureDef.restitution = 0f;
    
    world.createBody(bodyDef).createFixture(fixtureDef);
    
    groundShape.dispose();
    
    Gdx.input.setInputProcessor(new InputProcessor() {
      @Override
      public boolean keyDown(int keycode) {
        Gdx.app.log(logName, "Key down: " + Keys.toString(keycode));
        switch (keycode) {
          case Keys.ESCAPE:
            game.setScreen(new MainMenuScreen());
            break;
          case Keys.W:
            movement.y = SPEED;
            break;
          case Keys.A:
            movement.x = -SPEED;
            break;
          case Keys.S:
            movement.y = -SPEED;
            break;
          case Keys.D:
            movement.x = SPEED;
            break;
          default:
            break;
        }
        return true;
      }
      
      @Override
      public boolean keyUp(int keycode) {
        Gdx.app.log(logName, "Key up: " + Keys.toString(keycode));
        switch (keycode) {
          case Keys.W:
          case Keys.S:
            movement.y = 0;
            break;
          case Keys.A:
          case Keys.D:
            movement.x = 0;
            break;
          default:
            break;
        }
        return true;
      }
      
      @Override
      public boolean scrolled(int amount) {
        camera.zoom += amount / ZOOM;
        return true;
      }

      @Override
      public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
      }

      @Override
      public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
      }

      @Override
      public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
      }

      @Override
      public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
      }

      @Override
      public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
      }
    });
  }

  @Override
  public void dispose() {
    world.dispose();
    debugRenderer.dispose();
    super.dispose();
  }

  @Override
  public boolean keyDown(int keycode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void connected(Controller controller) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void disconnected(Controller controller) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean buttonDown(Controller controller, int buttonCode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean buttonUp(Controller controller, int buttonCode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean axisMoved(Controller controller, int axisCode, float value) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean povMoved(Controller controller, int povCode, PovDirection value) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
    // TODO Auto-generated method stub
    return false;
  }

}
