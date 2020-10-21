package com.myambulance.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameObjectScore extends GameObject{
    private int vaccineScore;
    private int ambulanceHealth;

    public GameObjectScore(float x, float y, float width, float height, int vaccineScore, int ambulanceHealth){
        super(x, y, width, height);
        this.vaccineScore = vaccineScore;
        this.ambulanceHealth = ambulanceHealth;
    }

    public int getVaccineScore() {
        return vaccineScore;
    }

    public void setVaccineScore(int vaccineScore) {
        this.vaccineScore = vaccineScore;
    }

    public int getAmbulanceHealth() {
        return ambulanceHealth;
    }

    public void setAmbulanceHealth(int ambulanceHealth) {
        this.ambulanceHealth = ambulanceHealth;
    }

    public void render(SpriteBatch batch){
        Assets.font.setColor(Color.WHITE);
        Assets.font.draw(batch, "" + vaccineScore, Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 20);
        Assets.font.setColor(Color.GREEN);
        Assets.font.draw(batch, "" + ambulanceHealth, 20, Gdx.graphics.getHeight() - 20);
    }
}
