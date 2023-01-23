package com.kaffekopp.game.constants;

import java.util.ArrayList;
import java.util.Arrays;

public class MapConstants {
    public static final ArrayList<String> singlePlayerMaps = new ArrayList<>(Arrays.asList(
            "maps/DemoMap.tmx",
            FilePaths.TUTORIAL_MAP,
            FilePaths.SECOND_TILE_MAP,
            FilePaths.FOURTH_TILE_MAP,
            FilePaths.THIRD_TILE_MAP,
            FilePaths.FIRST_TILE_MAP,
            FilePaths.FIVE_TILE_MAP
            ));

    public static final ArrayList<String> multiPlayerMaps = new ArrayList<>(Arrays.asList(
            FilePaths.MULTIPLAYER_3_TILE_MAP,
            FilePaths.MULTIPLAYER_2_TILE_MAP,
            FilePaths.MULTIPLAYER_TUTORIAL_MAP,
            FilePaths.MULTIPLAYER_1_TILE_MAP,
            FilePaths.MULTIPLAYER_TILE_MAP,
            FilePaths.MULTIPLAYER_4_TILE_MAP,
            FilePaths.MULTIPLAYER_5_TILE_MAP

    ));
}
