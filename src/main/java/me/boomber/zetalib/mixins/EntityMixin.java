package me.boomber.zetalib.mixins;

import me.boomber.zetalib.impl.EntityMixinImpl;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V"), method = "readNbt")
    private void onLoadNbtData(NbtCompound nbt, CallbackInfo ci) {
        new EntityMixinImpl((Entity) (Object) this).onLoadNbtData(nbt);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V"), method = "writeNbt")
    private void onSaveNbtData(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        new EntityMixinImpl((Entity) (Object) this).onSaveNbtData(nbt);
    }
}
