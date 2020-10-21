package com.myambulance.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import static com.myambulance.game.Assets.SPEED;

public class DynamicGameObjectAmbulance extends DynamicGameObject {

    public DynamicGameObjectAmbulance(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void commandMoveLeft() {
        accel.x -= SPEED * Gdx.graphics.getDeltaTime();
        if(accel.x < 0) accel.x = 0;
    }

    public void commandMoveRight() {
        this.bounds.x += SPEED * Gdx.graphics.getDeltaTime();
        if(this.bounds.x > Gdx.graphics.getWidth() - Assets.ambulanceImage.getWidth())
            this.bounds.x = Gdx.graphics.getWidth() - Assets.ambulanceImage.getWidth();
    }

    public void commandMoveDown() {
        this.bounds.y -= SPEED * Gdx.graphics.getDeltaTime();
        if(this.bounds.y < 0) this.bounds.y = 0;
    }

    public void commandMoveUp() {
        this.bounds.y += SPEED * Gdx.graphics.getDeltaTime();
        if(this.bounds.y > Gdx.graphics.getHeight() - Assets.ambulanceImage.getHeight())
            this.bounds.y = Gdx.graphics.getHeight() - Assets.ambulanceImage.getHeight();
    }

    public void commandMoveLeftCorner() {
        this.bounds.x = 0;
    }
    public void commandMoveRightCorner() {
        this.bounds.x = Gdx.graphics.getWidth() - Assets.ambulanceImage.getWidth();
    }

    public void commandTouched(OrthographicCamera camera, DynamicGameObject ambulance) {
        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
        ambulance.bounds.x = touchPos.x - Assets.ambulanceImage.getWidth() / 2;
    }

    public void commandExitGame() {
        Gdx.app.exit();
    }
}
