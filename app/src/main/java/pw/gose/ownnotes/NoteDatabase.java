package pw.gose.ownnotes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Log.d("NoteDatabase: ", "On create");
                            new CreateNotesAsyncTask(instance).execute();
                        }
                    })
                    .build();
            Log.d("NoteDatabase: ", "New instance");
        }
        return instance;
    }

    public static class CreateNotesAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        private CreateNotesAsyncTask(NoteDatabase db){
            this.noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... notes) {
            noteDao.insert(new Note("Title 1", "Hello World", 1));
            Log.d("NoteDatabase: ", "Adding default items");
            return null;
        }
    }
}
