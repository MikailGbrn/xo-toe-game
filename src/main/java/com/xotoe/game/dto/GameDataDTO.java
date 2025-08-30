package com.xotoe.game.dto;

import org.springframework.stereotype.Component;

@Component
public class GameDataDTO {

    public Integer tileSize;

    public Integer getTileSize() {
        return tileSize;
    }

    public void setTileSize(Integer tileSize) {
        this.tileSize = tileSize;
    }
}
