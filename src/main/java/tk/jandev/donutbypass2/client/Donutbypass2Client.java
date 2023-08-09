package tk.jandev.donutbypass2.client;

import me.x150.renderer.event.RenderEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientBlockEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import tk.jandev.donutbypass2.WorldReader;
import tk.jandev.donutbypass2.render.World;


public class Donutbypass2Client implements ClientModInitializer {
    public boolean cobble = true;
    MinecraftClient mc = MinecraftClient.getInstance();
    long currentTick = 0;
    public static Donutbypass2Client INSTANCE;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (mc.world == null) return;
            currentTick++;
            if (currentTick % 100 == 0) {
                //WorldReader reader = new WorldReader();
                //reader.start();
            }
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("togglecobble").executes(context -> {
            cobble = !cobble;
            return 1;
        })));

        RenderEvents.WORLD.register(World::world);

        ClientBlockEntityEvents.BLOCK_ENTITY_LOAD.register((blockEntity, world) -> {
            if (blockEntity.getType() == BlockEntityType.MOB_SPAWNER) {
                if (!World.toRender.contains(blockEntity.getPos())) {
                    World.toRender.add(blockEntity.getPos());
                }

            }
        });
    }
}
