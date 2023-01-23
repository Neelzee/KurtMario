package com.kaffekopp.game.constants;

public class ScreenConstants {
    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 480;
    public static final float SCALED_VIEWPORT_WIDTH_HALF = (float) V_WIDTH / 100f * 0.5f;
    public static final float SCALED_VIEWPORT_HEIGHT_HALF = (float) V_HEIGHT / 100f * 0.5f;
    public static final float MAGIC_FACTOR = 8f / 25f; //The magic factor which makes our camera stop at the end of maps
}
