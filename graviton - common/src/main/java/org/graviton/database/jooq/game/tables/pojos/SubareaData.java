/*
 * This file is generated by jOOQ.
*/
package org.graviton.database.jooq.game.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SubareaData implements Serializable {

    private static final long serialVersionUID = -143226589;

    private final Integer id;
    private final Integer alignement;
    private final Integer conquistable;
    private final Integer prisme;

    public SubareaData(SubareaData value) {
        this.id = value.id;
        this.alignement = value.alignement;
        this.conquistable = value.conquistable;
        this.prisme = value.prisme;
    }

    public SubareaData(
        Integer id,
        Integer alignement,
        Integer conquistable,
        Integer prisme
    ) {
        this.id = id;
        this.alignement = alignement;
        this.conquistable = conquistable;
        this.prisme = prisme;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getAlignement() {
        return this.alignement;
    }

    public Integer getConquistable() {
        return this.conquistable;
    }

    public Integer getPrisme() {
        return this.prisme;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SubareaData (");

        sb.append(id);
        sb.append(", ").append(alignement);
        sb.append(", ").append(conquistable);
        sb.append(", ").append(prisme);

        sb.append(")");
        return sb.toString();
    }
}
