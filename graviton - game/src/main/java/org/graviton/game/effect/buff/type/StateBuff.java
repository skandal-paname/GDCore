package org.graviton.game.effect.buff.type;

import org.graviton.game.effect.buff.Buff;
import org.graviton.game.effect.state.State;
import org.graviton.game.fight.Fighter;
import org.graviton.game.spell.SpellEffect;
import org.graviton.network.game.protocol.FightPacketFormatter;

/**
 * Created by Botan on 02/01/2017. 00:28
 */
public class StateBuff extends Buff {
    private final SpellEffect effect;
    private final State state;

    public StateBuff(Fighter fighter, SpellEffect spellEffect, short remainingTurns) {
        super(fighter, remainingTurns);
        this.effect = spellEffect;
        this.state = State.get((byte) effect.getThird());
        fighter.getBuffManager().addState(state);
    }

    @Override
    public void destroy() {
        fighter.getFight().send(FightPacketFormatter.actionMessage((short) effect.getType().value(), fighter.getId(), fighter.getId(), effect.getThird(), 0));
        fighter.getBuffManager().removeState(state);
    }

    @Override
    public void clear() {

    }

    @Override
    public void check() {

    }
}
