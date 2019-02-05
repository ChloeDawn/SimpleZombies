/*
 * Copyright (C) 2018 InsomniaKitten
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.insomniakitten.simplezombies.mixin;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.mob.ZombieEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ZombieEntity.class)
final class ZombieEntityMixin {
  private ZombieEntityMixin() {}

  @ModifyArg(
    method = "initAttributes",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/entity/attribute/EntityAttributeInstance;setBaseValue(D)V"
    ),
    slice = @Slice(
      from = @At(
        value = "FIELD",
        target = "Lnet/minecraft/entity/mob/ZombieEntity;SPAWN_REINFORCEMENTS:Lnet/minecraft/entity/attribute/EntityAttribute;"
      )
    )
  )
  private double preventReinforcements(final double baseValue) {
    return 0.0;
  }

/*  @ModifyArg(
    method = "prepareEntityData",
    at = @At(
      value = "NEW",
      target = "net/minecraft/entity/mob/ZombieEntity$class_1644"
    )
  )
  private boolean preventChildZombies(final boolean chance) {
    return false;
  }*/

  @Deprecated // fixme https://github.com/FabricMC/Mixin/issues/5
  @SuppressWarnings("UnresolvedMixinReference")
  @ModifyVariable(
    method = "prepareEntityData",
    at = @At(
      value = "JUMP",
      opcode = Opcodes.IFEQ,
      shift = Shift.BEFORE
    ),
    slice = @Slice(
      from = @At(value = "NEW", target = "net/minecraft/entity/mob/ZombieEntity$class_1644"),
      to = @At(value = "FIELD", target = "Lnet/minecraft/entity/mob/ZombieEntity$class_1644;field_7439:Z")
    )
  )
  private EntityData preventChildZombies(final EntityData data) {
    if (data instanceof ZombieEntity.class_1644) {
      ((ZombieEntityDataAccessor) data).setIsChild(false);
    }
    return data;
  }
}
