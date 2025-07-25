package sgsMod.cards.Basic;

import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sgsMod.SgsMod;
import sgsMod.actions.unique.LimitedLineAction;
import sgsMod.character.Mycharacter;
import sgsMod.helper.SgsHelper;
import sgsMod.powers.ShaTimesPower;
import sgsMod.powers.unableusesha;


public class Sha extends CustomCard{
    public static final String ID = Sha.class.getSimpleName();
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Sha.png";
    private static final int COST = 0;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Sha() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.BASIC, CardTarget.ENEMY);
        this.tags.add(CardTags.STARTER_STRIKE);
        this.tags.add(AbstractCard.CardTags.Sha);
        this.baseDamage = SgsHelper.GeneralShaDamage;
        this.tags.add(AbstractCard.CardTags.Basic);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.addToBot(new LimitedLineAction(ID,true, true));
        SgsHelper.PreShaAction(p, m);

        float times = SgsHelper.CountShaRate(p, m);

        this.addToBot(new DamageAction(m, new DamageInfo(p, (int)(this.damage * times), this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        SgsHelper.AfterShaAction(p, m);


    }

    @Override

    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return (AbstractCard)new Sha();
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        else if(p.hasPower(unableusesha.POWER_ID)){
            return false;
        }
        else if (!p.hasPower(ShaTimesPower.POWER_ID) || p.getPower(ShaTimesPower.POWER_ID).amount <= 0) {
            //没有杀的使用次数时不能出杀
            this.cantUseMessage = cardStrings.UPGRADE_DESCRIPTION;
            return false;
        } else {
            return canUse;
        }
    }

    public void applyPowers() {
        this.baseDamage = SgsHelper.countShaDamage(this.upgraded, false);
        super.applyPowers();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeDamage(SgsHelper.ShaDamageUpgrade);
        }
    }
}
