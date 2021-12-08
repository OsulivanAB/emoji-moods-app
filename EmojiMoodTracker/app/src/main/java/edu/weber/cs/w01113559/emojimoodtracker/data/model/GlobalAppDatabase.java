package edu.weber.cs.w01113559.emojimoodtracker.data.model;

public final class GlobalAppDatabase {

    private static AppDatabase sAppDatabase = null;

    public static AppDatabase getsAppDatabase() {
        return sAppDatabase;
    }

    public static void setsAppDatabase(AppDatabase sAppDatabase) {
        GlobalAppDatabase.sAppDatabase = sAppDatabase;
    }

    /**
     * Empty constructor - We don't initialize builder because we rely on a null state to let us
     * know the Application's process was killed.
     */
    private GlobalAppDatabase() {
    }

    /**
     * Instantiates the global {@link AppDatabase} variable.
     */
    public static AppDatabase initializeAppDatabaseInstance() {
        return sAppDatabase = new AppDatabase();
    }

    /**
     * Get an instance of the global App Database
     * @return {@link AppDatabase} if not set up yet, returns null.
     */
    public static AppDatabase getAppDatabaseInstance() {
        return sAppDatabase;
    }
}
