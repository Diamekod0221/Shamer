package com.lolshame.LoLShame.match;

public enum TeamColorEnum {
    BLUE,
    RED;

    public static String toStringVal(TeamColorEnum color){
        switch (color){
            case RED -> { return "200";}
            case BLUE -> { return "100";}
            default -> throw new RuntimeException("bad enum value, can't convert:" +
                    color);
        }
    }

    public static TeamColorEnum numericValueOf(String string){
        return numericValueOf(Integer.parseInt(string));
    }

    public static TeamColorEnum numericValueOf(int value){
        if(value == 100){
            return BLUE;
        }
        if(value == 200){
            return RED;
        }
        throw new IllegalArgumentException("can't assign team to value: " + value);
    }
}
