package tk.jandev.donutbypass2.render;

import me.x150.renderer.render.OutlineFramebuffer;
import me.x150.renderer.render.Renderer3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.ArrayList;

public class World {
    public static ArrayList<BlockPos> toRender = new ArrayList<>();
    public static MinecraftClient mc = MinecraftClient.getInstance();
    public static void world(MatrixStack stack) {
        ArrayList<BlockPos> shit = (ArrayList<BlockPos>) toRender.clone();
            OutlineFramebuffer.useAndDraw(() -> {
                for (BlockPos pos : shit) {
                    if (mc.player.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) > 40000) continue;
                    Renderer3d.renderThroughWalls();
                    Renderer3d.renderOutline(stack, Color.CYAN, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), new Vec3d(1, 1, 1));
                }
            }, 1, Color.CYAN, Color.CYAN);
    }

}
