package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.Create;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoupraw.mcmod.createsdelight.registry.MyModInitializer;
@Mixin(value = Create.class,remap = false)
public class MixinCreate {
    /**
     * stupid fabric loader made loading more complex
     */
    @Inject(method = "onInitialize",at = @At("TAIL"),remap = false)
    private void afterInitialize(CallbackInfo ci){
        MyModInitializer.initializeAfterCreate();
    }
}
