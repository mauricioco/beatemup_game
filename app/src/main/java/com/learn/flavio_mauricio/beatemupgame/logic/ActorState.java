package com.learn.flavio_mauricio.beatemupgame.logic;

public enum ActorState {
    Idle(0),
    Moving(1),
    Chasing(2),
    Attacking(3),
    Attacked(4),
    Unconscious(5);

    private final int id;
    private static int size = 6;

    ActorState(int value) {
        this.id = value;
    }

    public ActorState getState(int value){
        for(ActorState st : ActorState.values()){
            if (value == st.id){
                return st;
            }
        }
        return null;
    }

    public int getId(){
        return id;
    }

    public static int getSize(){
        return size;
    }

}
