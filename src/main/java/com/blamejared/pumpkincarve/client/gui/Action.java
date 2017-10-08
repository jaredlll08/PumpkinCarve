package com.blamejared.pumpkincarve.client.gui;


public class Action {
    
    private final int x;
    private final int y;
    private final int button;
    private final GuiCarving carving;
    
    public Action(GuiCarving carving, int x, int y, int button) {
        this.carving = carving;
        this.x = x;
        this.y = y;
        this.button = button;
    }
    
    public void apply(int[][] pixelMap) {
        System.out.println(pixelMap.length + ":" + pixelMap[pixelMap.length-1].length);
        pixelMap[x][y] = getMainColourForButton(button);
        
        if(y - 1 >= 0 && pixelMap[x][y - 1] == 0xFFFFFF) {
            pixelMap[x][y - 1] = getOuterColourForButton(button);
        }
        if(x - 1 >= 0 && pixelMap[x - 1][y] == 0xFFFFFF) {
            pixelMap[x - 1][y] = getOuterColourForButton(button);
        }
        if(y - 1 >= 0 && x - 1 >= 0 && pixelMap[x - 1][y - 1] == 0xFFFFFF) {
            pixelMap[x - 1][y - 1] = getOuterColourForButton(button);
        }
    }
    
    
    public int getMainColourForButton(int button) {
        switch(button) {
            case 0:
                return 0xFF441300;
            case 1:
                return 0xFFFFFF;
            default:
                return 0xFFFFFF;
        }
    }
    
    public int getOppositeMainColourForButton(int button) {
        switch(button) {
            case 0:
                return 0xFFFFFF;
            case 1:
                return 0xFF441300;
            default:
                return 0xFF441300;
        }
    }
    
    public int getOppositeOuterColourForButton(int button) {
        switch(button) {
            case 0:
                return 0xFFFFFF;
            case 1:
                return 0xFF2d0003;
            default:
                return 0xFF2d0003;
        }
    }
    
    public int getOuterColourForButton(int button) {
        switch(button) {
            case 0:
                return 0xFF2d0003;
            case 1:
                return 0xFFFFFF;
            default:
                return 0xFFFFFF;
        }
    }
    
    public void undo(int[][] pixelMap) {
        pixelMap[x][y] = getOppositeMainColourForButton(button);
        if(y - 1 >= 0 && pixelMap[x][y - 1] == 0xFF2D0003) {
            pixelMap[x][y - 1] = getOppositeOuterColourForButton(button);
        }
        if(x - 1 >= 0 && pixelMap[x - 1][y] == 0xFF2D0003) {
            pixelMap[x - 1][y] = getOppositeOuterColourForButton(button);
        }
        if(y - 1 >= 0 && x - 1 >= 0 && pixelMap[x - 1][y - 1] == 0xFF2D0003) {
            pixelMap[x - 1][y - 1] = getOppositeOuterColourForButton(button);
        }
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getButton() {
        return button;
    }
}
