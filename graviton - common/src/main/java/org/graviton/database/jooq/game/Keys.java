/*
 * This file is generated by jOOQ.
*/
package org.graviton.database.jooq.game;


import javax.annotation.Generated;

import org.graviton.database.jooq.game.tables.GuildMembers;
import org.graviton.database.jooq.game.tables.Guilds;
import org.graviton.database.jooq.game.tables.HousesData;
import org.graviton.database.jooq.game.tables.Maps;
import org.graviton.database.jooq.game.tables.Merchant;
import org.graviton.database.jooq.game.tables.MountparkData;
import org.graviton.database.jooq.game.tables.SellpointItems;
import org.graviton.database.jooq.game.tables.SubareaData;
import org.graviton.database.jooq.game.tables.Trunks;
import org.graviton.database.jooq.game.tables.records.GuildMembersRecord;
import org.graviton.database.jooq.game.tables.records.GuildsRecord;
import org.graviton.database.jooq.game.tables.records.HousesDataRecord;
import org.graviton.database.jooq.game.tables.records.MapsRecord;
import org.graviton.database.jooq.game.tables.records.MerchantRecord;
import org.graviton.database.jooq.game.tables.records.MountparkDataRecord;
import org.graviton.database.jooq.game.tables.records.SellpointItemsRecord;
import org.graviton.database.jooq.game.tables.records.SubareaDataRecord;
import org.graviton.database.jooq.game.tables.records.TrunksRecord;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>game</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<SellpointItemsRecord, Integer> IDENTITY_SELLPOINT_ITEMS = Identities0.IDENTITY_SELLPOINT_ITEMS;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<GuildsRecord> KEY_GUILDS_PRIMARY = UniqueKeys0.KEY_GUILDS_PRIMARY;
    public static final UniqueKey<GuildMembersRecord> KEY_GUILD_MEMBERS_ID = UniqueKeys0.KEY_GUILD_MEMBERS_ID;
    public static final UniqueKey<HousesDataRecord> KEY_HOUSES_DATA_PRIMARY = UniqueKeys0.KEY_HOUSES_DATA_PRIMARY;
    public static final UniqueKey<MapsRecord> KEY_MAPS_PRIMARY = UniqueKeys0.KEY_MAPS_PRIMARY;
    public static final UniqueKey<MerchantRecord> KEY_MERCHANT_PRIMARY = UniqueKeys0.KEY_MERCHANT_PRIMARY;
    public static final UniqueKey<MountparkDataRecord> KEY_MOUNTPARK_DATA_PRIMARY = UniqueKeys0.KEY_MOUNTPARK_DATA_PRIMARY;
    public static final UniqueKey<SellpointItemsRecord> KEY_SELLPOINT_ITEMS_PRIMARY = UniqueKeys0.KEY_SELLPOINT_ITEMS_PRIMARY;
    public static final UniqueKey<SubareaDataRecord> KEY_SUBAREA_DATA_PRIMARY = UniqueKeys0.KEY_SUBAREA_DATA_PRIMARY;
    public static final UniqueKey<TrunksRecord> KEY_TRUNKS_PRIMARY = UniqueKeys0.KEY_TRUNKS_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<SellpointItemsRecord, Integer> IDENTITY_SELLPOINT_ITEMS = createIdentity(SellpointItems.SELLPOINT_ITEMS, SellpointItems.SELLPOINT_ITEMS.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<GuildsRecord> KEY_GUILDS_PRIMARY = createUniqueKey(Guilds.GUILDS, "KEY_guilds_PRIMARY", Guilds.GUILDS.ID);
        public static final UniqueKey<GuildMembersRecord> KEY_GUILD_MEMBERS_ID = createUniqueKey(GuildMembers.GUILD_MEMBERS, "KEY_guild_members_id", GuildMembers.GUILD_MEMBERS.ID);
        public static final UniqueKey<HousesDataRecord> KEY_HOUSES_DATA_PRIMARY = createUniqueKey(HousesData.HOUSES_DATA, "KEY_houses_data_PRIMARY", HousesData.HOUSES_DATA.ID);
        public static final UniqueKey<MapsRecord> KEY_MAPS_PRIMARY = createUniqueKey(Maps.MAPS, "KEY_maps_PRIMARY", Maps.MAPS.ID);
        public static final UniqueKey<MerchantRecord> KEY_MERCHANT_PRIMARY = createUniqueKey(Merchant.MERCHANT, "KEY_merchant_PRIMARY", Merchant.MERCHANT.ID);
        public static final UniqueKey<MountparkDataRecord> KEY_MOUNTPARK_DATA_PRIMARY = createUniqueKey(MountparkData.MOUNTPARK_DATA, "KEY_mountpark_data_PRIMARY", MountparkData.MOUNTPARK_DATA.MAP);
        public static final UniqueKey<SellpointItemsRecord> KEY_SELLPOINT_ITEMS_PRIMARY = createUniqueKey(SellpointItems.SELLPOINT_ITEMS, "KEY_sellpoint_items_PRIMARY", SellpointItems.SELLPOINT_ITEMS.ID);
        public static final UniqueKey<SubareaDataRecord> KEY_SUBAREA_DATA_PRIMARY = createUniqueKey(SubareaData.SUBAREA_DATA, "KEY_subarea_data_PRIMARY", SubareaData.SUBAREA_DATA.ID);
        public static final UniqueKey<TrunksRecord> KEY_TRUNKS_PRIMARY = createUniqueKey(Trunks.TRUNKS, "KEY_trunks_PRIMARY", Trunks.TRUNKS.ID);
    }
}
