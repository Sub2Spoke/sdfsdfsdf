package tk.jandev.donutbypass2.client;

import me.x150.renderer.event.RenderEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientBlockEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import tk.jandev.donutbypass2.WorldReader;
import tk.jandev.donutbypass2.render.World;


public class Donutbypass2Client implements ClientModInitializer {
    public boolean cobble = true;
    static MinecraftClient mc = MinecraftClient.getInstance();
    long currentTick = 0;
    public static Donutbypass2Client INSTANCE;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("togglecobble").executes(context -> {
            cobble = !cobble;
            return 1;
        })));

        RenderEvents.WORLD.register(World::world);

        ClientBlockEntityEvents.BLOCK_ENTITY_LOAD.register((blockEntity, world) -> {
            if (blockEntity.getType() == BlockEntityType.MOB_SPAWNER || blockEntity.getType() == BlockEntityType.SHULKER_BOX) {
                if (blockEntity.getPos().getY() > -8 || !isSpawnerBlock(blockEntity.getPos())) return;
                if (!World.toRender.contains(blockEntity.getPos())) {
                    World.toRender.add(blockEntity.getPos());
                }
            }
        });
    }

    public static boolean isSpawnerBlock(BlockPos pos) {
        if (getBlock(pos.add(0, -1, 0)) == Blocks.MOSSY_COBBLESTONE) return false;
        if (getBlock(pos.add(0, -1, 0)) == Blocks.COBBLESTONE) return false;
        if (getBlock(pos.add(0, 0, 0)) == Blocks.COBWEB) return false;
        if (getBlock(pos.add(1, 0, 0)) == Blocks.COBWEB) return false;
        if (getBlock(pos.add(0, 1, 0)) == Blocks.COBWEB) return false;
        if (getBlock(pos.add(1, 1, 0)) == Blocks.COBWEB) return false;
        if (getBlock(pos.add(1, 1, 1)) == Blocks.COBWEB) return false;
        if (getBlock(pos.add(1, 1, 0)) == Blocks.COBWEB) return false;
        if (getBlock(pos.add(0, -1, 0)) == Blocks.COBWEB) return false;
        if (getBlock(pos.add(1, -1, 1)) == Blocks.COBWEB) return false;

        return true;
    }

    public static Block getBlock(BlockPos pos) {
        return mc.world.getBlockState(pos).getBlock();
    }
}
