package sgsMod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sgsMod.powers.ma;

public class TakenShaDamageAction extends AbstractGameAction {
    public AbstractMonster m;
    public AbstractPlayer p;
    public boolean isDone;

    public TakenShaDamageAction(AbstractPlayer p, AbstractMonster m) {
        this.m = m;
        this.p = p;
        this.isDone = false;
    }

    public void update() {
        int d = m.lastDamageTaken;
        if(d >= 40){
            CardCrawlGame.sound.play("Damage2");
        }

        if(p.hasPower(ma.POWER_ID)){
            this.addToTop(new ReducePowerAction(p, p, ma.POWER_ID, 1));
            this.addToTop(new GainBlockAction(p, d));
        }


        this.tickDuration();

    }
}