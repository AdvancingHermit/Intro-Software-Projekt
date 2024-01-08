package com.snake.game.handlers;

import com.snake.game.GameFeature;

public class AppleHandler extends GameFeature {
        private int chance;

        public AppleHandler(boolean enabled, String featureName, int chance) {
            super(enabled, featureName);
            this.chance = chance;
            if (!enabled) this.chance = 0;

        }

        @Override
        public void disable(){
            chance = 0;
            super.disable();
        }

        public int getChance() {
            return chance;
        }

        public void setChance(int chance) {
            this.chance = chance;
        }
}
