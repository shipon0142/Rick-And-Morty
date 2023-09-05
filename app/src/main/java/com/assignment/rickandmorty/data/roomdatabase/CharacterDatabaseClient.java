package com.assignment.rickandmorty.data.roomdatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.assignment.rickandmorty.repository.model.Character;
import com.assignment.rickandmorty.repository.model.Result;

import java.util.ArrayList;
import java.util.List;


public class CharacterDatabaseClient {
    private static final String TAG = CharacterDatabaseClient.class.getName() + "Debug";
    private final Context mCtx;

    private Context getContext() {
        return mCtx;
    }

    @SuppressLint("StaticFieldLeak")
    private static CharacterDatabaseClient mInstance;
    private final CharacterDatabaseHelper characterDatabase;

    private CharacterDatabaseClient(Context mCtx) {
        this.mCtx = mCtx;
        characterDatabase = Room.databaseBuilder(mCtx, CharacterDatabaseHelper.class, "CharacterDatabaseHelper").
                fallbackToDestructiveMigration().build();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP table result");
        }
    };

    public static synchronized CharacterDatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new CharacterDatabaseClient(mCtx);
        }
        return mInstance;
    }

    public CharacterDatabaseHelper getCharacterDatabase() {
        return characterDatabase;
    }

    @Database(entities = {Result.class}, version = 3)
    public abstract static class CharacterDatabaseHelper extends RoomDatabase {
        public abstract CharacterDao characterDao();
    }

    @androidx.room.Dao
    public interface CharacterDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(ArrayList<Result> characters);

        @Query("SELECT * FROM character")
        List<Result> getAllCharacters();
    }

    public interface SavedCharacterSuccessCallback {
        public void hasSaccess(ArrayList<Result> character);
    }


    public void saveCharacterList(ArrayList<Result> character, SavedCharacterSuccessCallback savedCharacterSuccessCallback) {
        @SuppressLint("StaticFieldLeak")
        class SaveCharacters extends AsyncTask<Void, Void, Void> {
            private final ArrayList<Result> character;

            SaveCharacters(ArrayList<Result> character) {
                this.character = character;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    CharacterDatabaseClient.getInstance(getContext()).getCharacterDatabase()
                            .characterDao()
                            .insert(character);
                } catch (Exception e) {


                }

                return null;
            }

           /*
           @Override
            protected void onPostExecute() {
                super.onPostExecute(null);

                savedCharacterSuccessCallback.hasSaccess( character);
            }*/
        }
        new SaveCharacters(character).execute();
    }

    public interface RetriveCharactersCallback {
        public void onCharactersRetrieved(ArrayList<Result> characters);
    }
    public void retrieveCharacterList(RetriveCharactersCallback retriveCharactersCallback) {
        @SuppressLint("StaticFieldLeak")
        class RetrieveCharacters extends AsyncTask<Void, Void, ArrayList<Result>> {
            @Override
            protected ArrayList<Result> doInBackground(Void... voids) {
                try {
                    CharacterDao characterDao = CharacterDatabaseClient.getInstance(getContext())
                            .getCharacterDatabase()
                            .characterDao();
                    List<Result> characters = characterDao.getAllCharacters();
                    ArrayList<Result> characterArrayList = new ArrayList<>(characters);
                    return characterArrayList;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ArrayList<Result> characters) {
                super.onPostExecute(characters);
                retriveCharactersCallback.onCharactersRetrieved(characters);
            }
        }

        new RetrieveCharacters().execute();
    }

}

