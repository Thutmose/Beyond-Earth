package net.mrscauthd.beyond_earth.common.blocks;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.common.blocks.entities.FlagBlockEntity;

public class FlagBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public static final VoxelShape SHAPE = Shapes.box((double)7/16, 0, (double)7/16, (double)9/16, 1, (double)9/16);

	public FlagBlock(BlockBehaviour.Properties builder) {
		super(builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, false));
	}

	@Override
	public RenderShape getRenderShape(BlockState p_49232_) {
		return RenderShape.MODEL;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		DoubleBlockHalf doubleblockhalf = stateIn.getValue(HALF);
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		if (facing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
			return facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf ? stateIn.setValue(FACING, facingState.getValue(FACING)) : Blocks.AIR.defaultBlockState();
		} else {
			return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		}
	}

	@Override
	public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
		if (!worldIn.isClientSide && player.isCreative()) {
			removeBottomHalf(worldIn, pos, state, player);
		}
		super.playerWillDestroy(worldIn, pos, state, player);
	}

	protected static void removeBottomHalf(Level world, BlockPos pos, BlockState state, Player player) {
		DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
		if (doubleblockhalf == DoubleBlockHalf.UPPER) {
			BlockPos blockpos = pos.below();
			BlockState blockstate = world.getBlockState(blockpos);
			if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
				world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
				world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
			}
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
		BlockPos blockpos = p_49820_.getClickedPos();
		Level level = p_49820_.getLevel();

		if (blockpos.getY() < level.getMaxBuildHeight() - 1 && p_49820_.getLevel().getBlockState(blockpos.above()).canBeReplaced(p_49820_)) {
			boolean flag = p_49820_.getLevel().getFluidState(p_49820_.getClickedPos()).is(Fluids.WATER);
			return this.defaultBlockState().setValue(FACING, p_49820_.getHorizontalDirection()).setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, flag);
		} else {
			return null;
		}
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state, placer, stack);
		boolean flag = worldIn.getFluidState(pos.above()).is(Fluids.WATER);
		worldIn.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER).setValue(WATERLOGGED, flag), 3);

		BlockEntity tileentity = worldIn.getBlockEntity(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));

		if (tileentity instanceof FlagBlockEntity flagtileentity) {

			CompoundTag compoundnbt = new CompoundTag();
			NbtUtils.writeGameProfile(compoundnbt, new GameProfile(placer.getUUID(), placer.getName().getString()));
			flagtileentity.getPersistentData().put("FlagOwner", compoundnbt);

			if (placer instanceof Player player) {

				flagtileentity.setOwner(player.getGameProfile());
			}
		}
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return new FlagBlockEntity(p_153215_, p_153216_);
	}

	@Override
	public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	public FlagBlock.ISkullType getSkullType() {
		return FlagBlock.Types.PLAYER;
	}

	public interface ISkullType {
	}

	public enum Types implements FlagBlock.ISkullType {
		PLAYER
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(worldIn, blockpos, Direction.UP) : blockstate.is(this);
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState p_60584_) {
		return PushReaction.DESTROY;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public long getSeed(BlockState state, BlockPos pos) {
		return Mth.getSeed(pos.getX(), pos.above(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
		p_49915_.add(HALF, FACING, WATERLOGGED);
	}
}