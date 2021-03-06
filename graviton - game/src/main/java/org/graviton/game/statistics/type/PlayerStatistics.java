package org.graviton.game.statistics.type;


import javafx.util.Pair;
import lombok.Data;
import org.graviton.collection.CollectionQuery;
import org.graviton.constant.Dofus;
import org.graviton.converter.Converters;
import org.graviton.game.client.player.Player;
import org.graviton.game.items.Item;
import org.graviton.game.items.Panoply;
import org.graviton.game.items.common.ItemEffect;
import org.graviton.game.items.common.ItemPosition;
import org.graviton.game.statistics.*;
import org.graviton.game.statistics.common.CharacteristicType;
import org.graviton.game.statistics.common.Statistics;
import org.graviton.network.game.protocol.ItemPacketFormatter;
import org.graviton.network.game.protocol.MessageFormatter;
import org.graviton.utils.Utils;
import org.jooq.Record;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.graviton.database.jooq.login.tables.Players.PLAYERS;

/**
 * Created by Botan on 11/11/2016 : 21:12
 */

@Data
public class PlayerStatistics extends Statistics {
    private final Player player;

    //current -> max
    private int[] pods;

    private Life life;

    private short statisticPoints, spellPoints, energy, level;
    private long experience;

    public PlayerStatistics(Player model) {
        this.player = null;
        super.initialize();

        this.level = model.getLevel();
        this.life = new Life(this, model.getLife().getSafeMaximum(), model.getLife().getSafeMaximum(), false);
    }

    public PlayerStatistics(Player player, Record record, byte prospection) {
        this.player = player;
        this.statisticPoints = record.get(PLAYERS.STAT_POINTS);
        this.spellPoints = record.get(PLAYERS.SPELL_POINTS);
        this.energy = record.get(PLAYERS.ENERGY);
        this.level = record.get(PLAYERS.LEVEL);
        this.experience = record.get(PLAYERS.EXPERIENCE);

        super.initialize();

        int vitality = record.get(PLAYERS.VITALITY);


        put(CharacteristicType.Vitality, new BaseCharacteristic(vitality));
        put(CharacteristicType.Wisdom, new BaseCharacteristic(record.get(PLAYERS.WISDOM)));
        put(CharacteristicType.Strength, new BaseCharacteristic(record.get(PLAYERS.STRENGTH)));
        put(CharacteristicType.Intelligence, new BaseCharacteristic(record.get(PLAYERS.INTELLIGENCE)));
        put(CharacteristicType.Chance, new BaseCharacteristic(record.get(PLAYERS.CHANCE)));
        put(CharacteristicType.Agility, new BaseCharacteristic(record.get(PLAYERS.AGILITY)));
        put(CharacteristicType.ActionPoints, new BaseCharacteristic((short) (level == 200 ? 8 : level >= 100 ? 7 : 6)));
        put(CharacteristicType.MovementPoints, new BaseCharacteristic((short) 3));
        put(CharacteristicType.Prospection, new BaseCharacteristic(prospection));
        put(CharacteristicType.Initiative, new Initiative(this, (short) 0));
        put(CharacteristicType.DodgeActionPoints, new Dodge(this));
        put(CharacteristicType.DodgeMovementPoints, new Dodge(this));
        put(CharacteristicType.CriticalHit, new CriticalRate(this));
        put(CharacteristicType.Summons, new BaseCharacteristic((short) 1));

        applyItemEffects();
        applyPanoplyEffect();

        refreshPods();

        this.life = new Life(this, record.get(PLAYERS.LIFE), 55 + ((level - 1) * 5), false, Utils.parseDate("yyyy~MM~dd~HH~mm", player.getAccount().getLastConnection()));
        life.add(vitality);
    }

    public PlayerStatistics(Player player, byte prospection) {
        this.player = player;
        this.statisticPoints = 0;
        this.spellPoints = 0;
        this.energy = 10000;
        this.level = 1;
        this.experience = 0;
        this.life = new Life(this, 55, 55, false);
        this.pods = new int[]{0, 1000};

        super.initialize();

        put(CharacteristicType.ActionPoints, new BaseCharacteristic((short) 6));
        put(CharacteristicType.MovementPoints, new BaseCharacteristic((short) 3));
        put(CharacteristicType.Prospection, new BaseCharacteristic(prospection));
        put(CharacteristicType.Initiative, new Initiative(this, (short) 0));
        put(CharacteristicType.DodgeActionPoints, new Dodge(this));
        put(CharacteristicType.DodgeMovementPoints, new Dodge(this));
        put(CharacteristicType.CriticalHit, new CriticalRate(this));
        put(CharacteristicType.Summons, new BaseCharacteristic((short) 1));
    }

    private void applyItemEffects() {
        player.getInventory().values().stream().filter(item -> item.getPosition().equipped()).forEach(this::applyItemEffects);
    }

    public void applyItemEffects(Item item) {
        item.getStatistics().forEach(((itemEffect, value) -> {
            ItemPosition position = item.getPosition();
            Pair<CharacteristicType, Boolean> values = itemEffect.convert();
            boolean equipped = position.equipped();

            if (values.getKey() != null) {
                if (values.getValue())
                    get(values.getKey()).addEquipment(!equipped ? (short) -value : value);
                else
                    get(values.getKey()).addEquipment(equipped ? (short) -value : value);
            }

            if (values.getKey() == CharacteristicType.Life || values.getKey() == CharacteristicType.Vitality)
                life.add(equipped ? value : -value);
        }));
    }

    private void applyPanoplyEffect() {
        player.getInventory().getEquippedItems().stream().filter(item -> item.getTemplate().getPanoply() != null).
                collect(Collectors.toList()).forEach(item -> applyPanoplyEffect(item.getTemplate().getPanoply(), true, false));
    }

    public void applyPanoplyEffect(Panoply panoply, boolean equippedItem, boolean send) {
        Collection<Item> equippedItems = player.getInventory().getEquippedItems();

        byte equipped = panoply.getEquippedObject(player.getInventory().getEquippedItems().stream().filter(item -> item.getTemplate().getPanoply() != null).collect(Collectors.toList()));

        Map<ItemEffect, Short> effects = panoply.effects(equipped);

        clearPanoplyEffect(panoply, (byte) (equippedItem ? equipped - 1 : equipped + 1));

        if (effects != null) {
            effects.forEach(((itemEffect, value) -> {
                Pair<CharacteristicType, Boolean> values = itemEffect.convert();
                get(values.getKey()).addEquipment(value);
            }));
        }

        if (send)
            player.send(ItemPacketFormatter.panoplyMessage(panoply, effects, equipped, CollectionQuery.from(equippedItems).transform(Converters.ITEM_TO_ID).computeList(new ArrayList<>())));
    }

    private void clearPanoplyEffect(Panoply panoply, byte equipped) {
        if (equipped >= 2)
            panoply.effects(equipped).forEach((effect, value) -> {
                Pair<CharacteristicType, Boolean> values = effect.convert();
                get(values.getKey()).addEquipment((short) -value);
            });
    }

    public short getProspection() {
        return (short) (get(CharacteristicType.Prospection).total() + get(CharacteristicType.Chance).total() / 10);
    }

    public int[] refreshPods() {
        return this.pods = new int[]{this.player.getInventory().values().stream().mapToInt(item -> item.getQuantity() * item.getTemplate().getPods()).sum(), getMaxPods()};
    }

    private int getMaxPods() {
        return (1000 + (get(CharacteristicType.Strength).total() * 5) + get(CharacteristicType.Pods).total());
    }

    public void upLevel() {
        this.spellPoints++;
        this.statisticPoints += 5;
        this.level++;
        this.experience = player.getEntityFactory().getExperience(level).getPlayer();

        this.life.addMaximum(5);
        this.life.regenMax();

        if (level % 100 == 0)
            get(CharacteristicType.ActionPoints).addBase(1);
    }

    public void addExperience(long experience) {
        this.experience += experience;

        while (this.level < Dofus.MAX_LEVEL && this.experience > player.getEntityFactory().getExperience(this.level).getNext().getPlayer())
            player.upLevel(true);
    }

    public void addEnergy(short energy) {
        if (energy < 0)
            player.send(MessageFormatter.looseEnergyMessage((short) (energy * -1)));

        this.energy = (short) Utils.limit(this.energy + energy, Dofus.MAX_ENERGY);

        if (this.energy == 0) {
            //fantome etc etc
        }
    }

    public void addSpellPoints(short spellPoints) {
        this.spellPoints += spellPoints;
    }

    @Override
    public Statistics copy() {
        return null;
    }
}
