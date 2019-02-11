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

import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
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

  @SuppressWarnings("UnqualifiedMemberReference")
  @ModifyArg(
    method = "prepareEntityData",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/entity/mob/ZombieEntity$class_1644;<init>"
    )
  )
  private boolean preventChildZombies(final boolean chance) {
    return false;
  }
}
