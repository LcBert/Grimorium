package com.lucab.grimorium.blocks.candelabra;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CandelabraVoxelShape {
    public static VoxelShape makeShape(Direction.Axis axis) {
        VoxelShape shape = Shapes.empty();
        switch (axis) {
            case Direction.Axis.X:
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.0625, 0.4375, 0.5625, 0.5, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0.0625, 0.75), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.375, 0.5, 0.125, 0.625, 0.625, 0.875), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.625, 0.4375, 0.5625, 0.8125, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.625, 0.1875, 0.5625, 0.875, 0.3125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.625, 0.6875, 0.5625, 0.875, 0.8125), BooleanOp.OR);
                break;

            case Direction.Axis.Z:
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.0625, 0.4375, 0.5625, 0.5, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0.0625, 0.75), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.125, 0.5, 0.375, 0.875, 0.625, 0.625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.4375, 0.625, 0.4375, 0.5625, 0.8125, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.1875, 0.625, 0.4375, 0.3125, 0.875, 0.5625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.6875, 0.625, 0.4375, 0.8125, 0.875, 0.5625), BooleanOp.OR);
                break;

            default:
                break;
        }

        return shape;
    }
}
