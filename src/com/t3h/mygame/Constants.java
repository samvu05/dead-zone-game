package com.t3h.mygame;

public interface Constants {
    int H_F = 800;
    int H_HEADER = 0;   //Header can be 50,100... when needed
    int H_PLAY = H_F-H_HEADER;  //We need the square map, so W_F=H_F-H_HEADER=W_PLAY=H_PLAY
    int W_F= H_PLAY;
    int PADDING = W_F/4;
//    int SIZE_MAP = 5 ;
//    int SIZE_PAD = (W_F-(2*PADDING))/ SIZE_MAP;
//    int SIZE_ITEM = (2*SIZE_PAD)/3;
//    int LEVEL = 3;
}
