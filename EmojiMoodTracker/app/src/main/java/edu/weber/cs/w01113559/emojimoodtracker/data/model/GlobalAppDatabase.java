package edu.weber.cs.w01113559.emojimoodtracker.data.model;

import android.content.Context;

public final class GlobalAppDatabase {

    private static AppDatabase sAppDatabase = null;

    /**
     * Empty constructor - We don't initialize builder because we rely on a null state to let us
     * know the Application's process was killed.
     */
    private GlobalAppDatabase() { }

    /**
     * sets the global variable using a user provided {@link AppDatabase}
     * @param database {@link AppDatabase} to set the global variable to.
     */
    public static void setAppDatabaseInstance(AppDatabase database) {
        sAppDatabase = database;
    }

    /**
     * Instantiates the global {@link AppDatabase} variable.
     * @param context {@link Context} context for the current activity.
     */
    public static AppDatabase initializeAppDatabaseInstance(Context context) {
        return sAppDatabase = new AppDatabase(context);
    }

    /**
     * Get an instance of the global App Database
     * @return {@link AppDatabase} if not set up yet, returns null.
     */
    public static AppDatabase getAppDatabaseInstance() {
        return sAppDatabase;
    }
}
