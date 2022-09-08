package com.kylelovestoad.mafia.game.gameplayers.properties;

import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;

import java.util.function.Consumer;

public class NamedTargetedAbility implements TargetedAbility {

    private final String name;
    private final Consumer<GamePlayer> onUse;

    private final Consumer<GamePlayer> onSelect;
    private GamePlayer selectedPlayer = null;

    public NamedTargetedAbility(Builder builder) {
        this.name = builder.name;
        this.onSelect = builder.onSelect;
        this.onUse = builder.onUse;
    }

    @Override
    public void useAbility() {
        if (selectedPlayer != null)
            onUse.accept(selectedPlayer);
    }

    @Override
    public void select(GamePlayer selectedPlayer) {
        onSelect.accept(selectedPlayer);
        this.selectedPlayer = selectedPlayer;
    }

    @Override
    public GamePlayer getSelected() {
        return selectedPlayer;
    }

    public String name() {
        return name;
    }

    public static class Builder {

        String name;
        private Consumer<GamePlayer> onUse;
        private Consumer<GamePlayer> onSelect;

        public Builder(String name) {
            this.name = name;
        }

        public Builder onUse(Consumer<GamePlayer> onUse) {
            this.onUse = onUse;
            return this;
        }

        public Builder onSelect(Consumer<GamePlayer> onSelect) {
            this.onSelect = onSelect;
            return this;
        }

        public NamedTargetedAbility build() {
            return new NamedTargetedAbility(this);
        }
    }

}
