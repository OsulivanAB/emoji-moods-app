package edu.weber.cs.w01113559.emojimoodtracker.data.model;

public final class GlobalAppDatabase {

    private static AppDatabase sAppDatabase = null;

    /*
     * Empty constructor - We don't initialize builder because we rely on a null state to let us
     * know the Application's process was killed.
     */
    private GlobalAppDatabase() { }

    public static void setAppDatabaseInstance(AppDatabase database) {
        sAppDatabase = database;
    }

    public static AppDatabase getAppDatabaseInstance() {
        return sAppDatabase;
    }
}
