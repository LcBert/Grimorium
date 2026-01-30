package com.lucab.grimorium.blocks.stand;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallStandVoxelShape {
    public static VoxelShape getShape(Direction direction) {
        VoxelShape shape = Shapes.empty();
        switch (direction) {
            case Direction.NORTH:
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.8125, 0, 0.5625, 0.9375, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.5078125, 0, 0.625, 0.9375, 0.34375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.375, 0.5078125, 0, 0.4375, 0.9375, 0.34375), BooleanOp.OR);
                break;

            case Direction.SOUTH:
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.8125, 0, 0.5625, 0.9375, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.375, 0.5078125, 0.65625, 0.4375, 0.9375, 1), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5625, 0.5078125, 0.65625, 0.625, 0.9375, 1), BooleanOp.OR);
                break;

            case Direction.EAST:
                shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0.4375, 1, 0.9375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.65625, 0.5078125, 0.5625, 1, 0.9375, 0.625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.65625, 0.5078125, 0.375, 1, 0.9375, 0.4375), BooleanOp.OR);
                break;

            case Direction.WEST:
                shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0.4375, 1, 0.9375, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.5078125, 0.375, 0.34375, 0.9375, 0.4375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0, 0.5078125, 0.5625, 0.34375, 0.9375, 0.625), BooleanOp.OR);
                break;

            default:
                break;
        }

        return shape;
    }
}
