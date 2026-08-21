package polari_stars.wild_wind.the_wild_update.registry.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.neoforged.neoforge.common.Tags;
import org.jspecify.annotations.Nullable;

import java.util.Set;

interface SpawnPlacementTypes {
    SpawnPlacementType MUDCRAB = new SpawnPlacementType() {
        /// 地面
        public static final Set<Block> GROUND = Set.of(
                Blocks.MUD,
                Blocks.MANGROVE_ROOTS,
                Blocks.MUDDY_MANGROVE_ROOTS);
        /// 水底
        public static final Set<Block> UNDERWATER = Set.of(
                Blocks.SAND,
                Blocks.GRAVEL,
                Blocks.CLAY,
                Blocks.DIRT,
                Blocks.MUD,
                Blocks.MUDDY_MANGROVE_ROOTS);

        @Override
        public boolean isSpawnPositionOk(LevelReader level, BlockPos blockPos, @Nullable EntityType<?> type) {
            if (type == null || !level.getWorldBorder().isWithinBounds(blockPos)) {
                return false;
            }

            BlockPos above = blockPos.above();
            BlockPos below = blockPos.below();
            BlockState aboveBlockState = level.getBlockState(above);
            BlockState belowBlockState = level.getBlockState(below);
            if (level.getFluidState(blockPos).is(FluidTags.WATER)) {
                if (aboveBlockState.isRedstoneConductor(level, above)) {
                    return false;
                }
                return UNDERWATER.stream().anyMatch(belowBlockState::is);
            }

            return belowBlockState.isValidSpawn(level, below, type)
                    && this.isValidEmptySpawnBlock(level, blockPos, type)
                    && this.isValidEmptySpawnBlock(level, above, type)
                    && GROUND.stream().anyMatch(belowBlockState::is);
        }

        private boolean isValidEmptySpawnBlock(LevelReader level, BlockPos blockPos, EntityType<?> type) {
            BlockState blockState = level.getBlockState(blockPos);
            return NaturalSpawner.isValidEmptySpawnBlock(level, blockPos, blockState, blockState.getFluidState(), type);
        }

        @Override
        public BlockPos adjustSpawnPosition(LevelReader level, BlockPos candidate) {
            BlockPos below = candidate.below();
            return level.getBlockState(below).isPathfindable(PathComputationType.LAND) ? below : candidate;
        }
    };
}
