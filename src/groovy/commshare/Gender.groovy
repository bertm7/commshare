/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE")

    Gender(String id) { this.id = id; }
    private String id

    def getId() { id }

}