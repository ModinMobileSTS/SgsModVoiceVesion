package sgsMod.cards.General;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import sgsMod.SgsMod;
import sgsMod.cards.Others.SgsCard;
import sgsMod.character.Mycharacter;
import sgsMod.powers.NextTurnStrengthPower;

public class LuoYi extends SgsCard {
    public static final String ID = LuoYi.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/XuChu.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public LuoYi() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.tags.add(AbstractCard.CardTags.General);
        this.baseMagicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.addToBot(new SFXAction(ID));
        this.addToBot(new ApplyPowerAction(p, p, new DrawReductionPower(p, 1)));
        this.addToBot(new ApplyPowerAction(p, p, new NextTurnStrengthPower(p, this.baseMagicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new LuoYi();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}
