package ru.game;

import ru.game.graph.GameLoop;

public class App 
{
    public static void main( String[] args )
    {
    	ResourceManager.init();
        new GameLoop("2048 Game", 460, 605, true, false).run();
    }
}