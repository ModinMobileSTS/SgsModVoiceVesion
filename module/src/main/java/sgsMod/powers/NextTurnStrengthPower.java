package sgsMod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sgsMod.SgsMod;

public class NextTurnStrengthPower extends AbstractPower {
    public static final String POWER_ID = "NextTurnStrengthPower";
    public static final String NAME ;
    public static final String[] DESCRIPTIONS ;
    public static PowerStrings powerStrings;


    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public NextTurnStrengthPower(AbstractCreature owner, int amount) {//参数：owner-能力施加对象、amount-施加能力层数。在cards的use里面用ApplyPowerAction调用进行传递。
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.img =  AssetLoader.getTexture(SgsMod.MOD_ID,"img/power_img/NextTurnStrengthPower.png");
        //以上五句不可缺少，照抄即可。记得修改this.img的图片路径。
        updateDescription();//调用该方法（第36行）的文本更新函数,更新一次文本描叙，不可缺少。

        this.type = PowerType.BUFF;
        this.isTurnBased = true;
    }

    public void updateDescription() {

        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
        //例： public static final String[] DESCRIPTIONS = {"在你回合开始时获得","点力量."};
        //   this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
        //   通过该方式更新后的文本:在你回合开始时获得amount层力量.
        //   另外一提，除变量this.amount（能力层数对应的变量）外，其他变量被赋值后需要人为调用updateDescription函数进行文本更新。
    }

    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }//可通过添加if判定this.amount来限制层数上限。

    //触发时机，以下部分触发时机仅翻译英文，具体效果未知，如有错误，请见谅。如仍无法满足DIY需求，请详参desktop的各卡牌源码或AbstractCard类的源码。
    //小tips：在以下触发时机里，需要的闪烁的，可调用flash();让能力闪一下.


    public void onAttack(final DamageInfo info, final int damageAmount, final AbstractCreature target) {//参数： info-伤害信息，damageAmount-伤害数值，target-伤害目标
    }//触发时机：当玩家攻击时。info.可调用伤害信息。

    public int onAttacked(final DamageInfo info, final int damageAmount) {//参数：info-伤害信息，damageAmount-伤害数值
        return damageAmount;//该damaeAmount为未被格挡的伤害（参考遗物鸟居）。
    }//触发时机：当玩家被攻击时，返回伤害数值，可用来修改伤害数值。info.可调用伤害信息。

    //伤害信息：info.owner (该次伤害的攻击者) info.type(该次伤害的种类，可利用info.type.调用伤害种类)
    //伤害种类:DamageInfo.DamageType.HP_LOSS (失去生命，无法被格挡，无法触发原版的【受到伤害时】的条件)  DamageInfo.DamageType.NORMAL (一般伤害，可以被格挡，能触发原版的【受到伤害时】的条件)  DamageInfo.DamageType.THORNS (荆棘伤害，可以被格挡，无法触发原版的【受到伤害时】的条件)

    public void atStartOfTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.amount), this.amount));
        this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.amount), this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }//触发时机：当玩家回合开始时触发。

    public void atEndOfTurn(final boolean isPlayer) {
    }//触发时机：当玩家回合结束时触发。

    public void onDamageAllEnemies(final int[] damage) {
    }//触发时机：当对敌人全体造成伤害时触发。（待商榷，未使用过）

    public int onHeal(final int healAmount) {
        return healAmount;
    }//触发时机：当玩家回复生命时，返回生命回复数值，可以用来修改生命回复数值。

    public void onUseCard(AbstractCard card, UseCardAction action) {
    }

    public void onPlayCard(final AbstractCard card, final AbstractMonster m) {
    }//触发时机：当一张卡被打出且卡牌效果生效前。

    public void onAfterUseCard(final AbstractCard card, final UseCardAction action) {
    }//触发时机：当一张卡被打出后进入弃牌堆/消耗堆时。

    public void onAfterCardPlayed(final AbstractCard usedCard) {
    }//触发时机：当一张卡被打出且卡牌效果生效后。

    public void atEnergyGain() {
    }//触发时机：当玩家获得能量时。

    public void onExhaust(final AbstractCard card) {
    }//触发时机：当玩家消耗卡牌时。

    public void onGainedBlock(final float blockAmount) {
    }//触发时机：当玩家获得格挡时。

    public void onRemove() {
    }//触发时机：当能力被移除时。

    public void onApplyPower(final AbstractPower power, final AbstractCreature target, final AbstractCreature source) {
        //参数：power-能力Id，target-被给予者，source-给予者
    }//触发时机：当给予能力时。

    public int onLoseHp(final int damageAmount) {
        return damageAmount;
    }//触发时机：当失去生命值时，返回伤害数值，可用来修改伤害数值。

    public void onVictory() {
    }//触发时机：当一个房间获胜时。
}
