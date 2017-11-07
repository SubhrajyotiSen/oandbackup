package dk.jens.backup;

import android.provider.BaseColumns;

public final class BlacklistContract {
    static final String CREATE_DB = String.format(
            "create table %s(%s INTEGER PRIMARY KEY, %s TEXT, %s INTEGER)",
            BlacklistEntry.TABLE_NAME, BlacklistEntry._ID,
            BlacklistEntry.COLUMN_PACKAGENAME, BlacklistEntry.COLUMN_BLACKLISTID);

    private BlacklistContract(){}

    public static class BlacklistEntry implements BaseColumns {
        public static final String TABLE_NAME = "blacklists";
        public static final String COLUMN_PACKAGENAME = "packagename";
        public static final String COLUMN_BLACKLISTID = "blacklistId";
    }
}
