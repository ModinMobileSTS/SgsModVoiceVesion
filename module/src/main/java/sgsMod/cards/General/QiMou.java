package sgsMod.cards.General;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sgsMod.SgsMod;
import sgsMod.actions.unique.LineAction;
import sgsMod.actions.unique.QiMouAction;
import sgsMod.cards.Others.SgsCard;
import sgsMod.character.Mycharacter;
import sgsMod.helper.SoundController;

public class QiMou extends SgsCard {
    public static final String ID = QiMou.class.getSimpleName();
    public static final String IMG_PATH = "img/cards/QiMou.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = -1;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public QiMou() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.NONE);
        this.tags.add(AbstractCard.CardTags.General);
        this.baseMagicNumber = 7;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.addToBot(new LineAction("JiLi", SoundController.SoundType.BattleAndRate));
        this.addToBot(new QiMouAction(p, this.baseMagicNumber, this.freeToPlayOnce, this.energyOnUse));

        //Doppelganger
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new QiMou();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeMagicNumber(3);
        }
    }
}
