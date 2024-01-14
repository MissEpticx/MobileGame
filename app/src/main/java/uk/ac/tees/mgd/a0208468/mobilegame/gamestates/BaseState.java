package uk.ac.tees.mgd.a0208468.mobilegame.gamestates;

import uk.ac.tees.mgd.a0208468.mobilegame.main.Game;

public abstract class BaseState {
    protected Game game;
    public BaseState(Game game){
        this.game = game;
    }

    public Game getGame(){
        return game;
    }
}
