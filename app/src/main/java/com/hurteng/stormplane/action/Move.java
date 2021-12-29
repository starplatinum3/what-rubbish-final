package com.hurteng.stormplane.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Move {
    int fromX;
    int fromY;
    int toX;
    int toY;
}
