package gov.vitality.syshud.mixin;

import gov.vitality.syshud.client.hud.backend.HUDConstraints;
import gov.vitality.syshud.client.screen.ModConfigScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {



    private final Text btnTooltip = Text.literal("You can also access the config using ModMenu if the mod is installed.");

    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Unique
    private final String os_name = System.getProperty("os.name");

    @Inject(at = @At("TAIL"), method = "initWidgets()V")
    private void onInitWidgets(CallbackInfo ci) {
        addSHOptionsBtn();
    }

    @Unique
    private void addSHOptionsBtn() {
        int i = HUDConstraints.vstack.Companion.bottom();
        int j = HUDConstraints.hstack.Companion.leading();
        int paddingX = 0;
        int paddingY = 0;

        if (!os_name.contains("Mac OS X")) {
            paddingY = 10;
        }



        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.config"), (button) -> {
            assert client != null;
            client.setScreen(ModConfigScreen.INSTANCE.getConfigScreen(this).build());
        }).tooltip(Tooltip.of(btnTooltip)).dimensions(j - paddingX, i - paddingY, 98, 20).build());


    }

}
