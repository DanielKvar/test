package com.myambulance.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
    public static Texture syringeImage;
    public static Texture ambulanceImage;
    public static Texture treeImage;
    public static Texture healImage;
    public static Sound pickupSound;
    public static Sound pickupHealSound;
    public static BitmapFont font;
    public static int SPEED = 400;

    public static void Load(){
        ambulanceImage = new Texture(Gdx.files.internal("ambulance.png"));
        syringeImage = new Texture(Gdx.files.internal("syringe.png"));
        treeImage = new Texture(Gdx.files.internal("tree.png"));
        healImage = new Texture(Gdx.files.internal("heal.png"));
        pickupSound = Gdx.audio.newSound(Gdx.files.internal("pickup.wav"));
        pickupHealSound = Gdx.audio.newSound(Gdx.files.internal("pickupheal.wav"));
        font = new BitmapFont();
        font.getData().setScale(2);
    }

    public void dispose() {
        // dispose of all the native resources
        syringeImage.dispose();
        ambulanceImage.dispose();
        pickupSound.dispose();
        pickupHealSound.dispose();
        treeImage.dispose();
        healImage.dispose();
    }
}
