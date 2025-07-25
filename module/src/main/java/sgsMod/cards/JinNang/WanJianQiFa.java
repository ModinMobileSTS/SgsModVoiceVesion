package sgsMod.cards.JinNang;

import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import sgsMod.SgsMod;
import sgsMod.actions.unique.LineAction;
import sgsMod.cards.Basic.Shan;
import sgsMod.character.Mycharacter;
import sgsMod.helper.SoundController;
import sgsMod.powers.hanyong;


public class WanJianQiFa extends CustomCard{
    public static final String ID = WanJianQiFa.class.getSimpleName();
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/WanJianQiFa.png";
    private static final int COST = 2;

    private final int raw_damage = 13;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public WanJianQiFa() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, sgsMod.enums.CardColorEnum.SGS_COLOR, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = raw_damage;
        this.baseMagicNumber = 1;
        this.tags.add(AbstractCard.CardTags.JinNang);
        this.isMultiDamage = true;
        this.cardsToPreview = new Shan();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作

        this.addToBot(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new LineAction(ID, SoundController.SoundType.Turn));
        this.addToBot(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

        if(!this.upgraded){
            for(AbstractCard c : p.hand.group){
                if(c.cardID.equals(Shan.ID)){
                    this.addToBot(new DiscardSpecificCardAction(c));
                    break;
                }
            }
        }


        /*for(AbstractMonster m1: AbstractDungeon.getCurrRoom().monsters.monsters){
            /*if(!(m1.intent == AbstractMonster.Intent.DEFEND || m1.intent == AbstractMonster.Intent.DEFEND_DEBUFF || m1.intent == AbstractMonster.Intent.DEFEND_BUFF)){
                this.addToBot(new DamageAction(m1, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
            }
            if(!m1.isDeadOrEscaped()){
                this.addToBot(new ApplyPowerAction(m1, p, new VulnerablePower(m1, this.baseMagicNumber, false)));
            }

        }*/

    }

    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new WanJianQiFa();
    }

    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            //upgradeDamage(4);
            //upgradeMagicNumber(1);
        }
    }

    public void applyPowers() {
        if(AbstractDungeon.player.hasPower(hanyong.POWER_ID)){
            this.baseDamage = raw_damage * (1 + AbstractDungeon.player.getPower(hanyong.POWER_ID).amount);
        }
        super.applyPowers();
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else  {
            boolean hasCard = false;
            for(AbstractCard c : p.hand.group){
                if(c.cardID.equals(Shan.ID)){
                    hasCard = true;
                    break;
                }
            }
            if(hasCard){
                return canUse;
            }
            else{
                this.cantUseMessage = "这张牌需要手牌中有闪时才能打出。";
                return false;
            }
        }
    }
}
