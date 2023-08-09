package tk.jandev.donutbypass2;


import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShapes;
import tk.jandev.donutbypass2.client.Donutbypass2Client;
import tk.jandev.donutbypass2.mixin.WorldRendererMixin;
import tk.jandev.donutbypass2.render.World;

import java.util.ArrayList;

public class WorldReader implements Runnable{
    MinecraftClient mc = net.minecraft.client.MinecraftClient.getInstance();
    public BlockState getBlock(BlockPos pos) {
        return mc.world.getBlockState(pos);
    }

    public ArrayList<BlockPos> scanForWrongDeepSlate(int searchRadius) {
        int startX = mc.player.getBlockX();
        int startZ = mc.player.getBlockZ();
        ArrayList<BlockPos> successFullStates = new ArrayList<>();
        for (int x = startX - (searchRadius / 2); x < startX + (searchRadius / 2); x++) {
            for (int z = startZ - (searchRadius / 2); z < startZ + (searchRadius / 2); z++) {
                for (int y = -64; y < -10; y++) {
                    BlockState locationPos = getBlock(new BlockPos(x, y, z));
                    if (locationPos.getBlock() == Blocks.DEEPSLATE) {
                        if (!isYAxis(locationPos)) successFullStates.add(new BlockPos(x, y, z));
                    }
                    if (locationPos.getBlock() == Blocks.COBBLED_DEEPSLATE && Donutbypass2Client.INSTANCE.cobble) successFullStates.add(new BlockPos(x, y, z));
                }
            }
        }

        return successFullStates;
    }

    public boolean isYAxis(BlockState state) {
        return (state.get(Properties.AXIS) == Direction.Axis.Y);
    }

    @Override
    public void run() {
        //World.toRender = scanForWrongDeepSlate(128);
    }

    public void start() {
        Thread thread = new Thread(this, "worldReader");
        thread.start();
    }
}
