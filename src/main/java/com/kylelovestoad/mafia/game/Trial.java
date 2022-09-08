package com.kylelovestoad.mafia.game;

import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;

import java.util.ArrayList;
import java.util.List;

public class Trial {

    //TODO maybe use this as a trial???

    private final GamePlayer gamePlayerOnTrial;

    private final List<GamePlayer> voters = new ArrayList<>();

    public Trial(GamePlayer gamePlayerOnTrial) {
        this.gamePlayerOnTrial = gamePlayerOnTrial;
    }

    public void addVoter(GamePlayer gamePlayer) {
        voters.add(gamePlayer);
    }

    public void removeVoter(GamePlayer gamePlayer) {
        voters.remove(gamePlayer);
    }

    public List<GamePlayer> getVoters() {
        return voters;
    }
}
