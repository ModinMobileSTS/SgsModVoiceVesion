package sgsMod.relics;

import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sgsMod.SgsMod;

public class QingGangJian extends CustomRelic {
    public static final String ID = "QingGangJian";
    private static final String IMG = "img/relics/QingGangJian.png";
    private static final String IMG_OTL = "img/relics/outline/QingGangJian.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public QingGangJian() {
        super(ID,  AssetLoader.getTexture(SgsMod.MOD_ID,IMG),  AssetLoader.getTexture(SgsMod.MOD_ID,IMG_OTL), RelicTier.COMMON, LandingSound.HEAVY);
    }


    public void atTurnStart() {
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new QingGangJian();
    }
}
