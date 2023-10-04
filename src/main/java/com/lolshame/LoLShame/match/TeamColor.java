package com.lolshame.LoLShame.match;

public enum TeamColor {
    BLUE,
    RED;

    public static String toStringVal(TeamColor color){
        switch (color){
            case RED -> { return "200";}
            case BLUE -> { return "100";}
            default -> throw new RuntimeException("bad enum value, can't convert:" +
                    color);
        }
    }
}
