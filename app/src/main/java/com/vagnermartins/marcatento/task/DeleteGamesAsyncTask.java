package com.vagnermartins.marcatento.task;

import android.os.AsyncTask;

import com.vagnermartins.marcatento.entity.Game;
import com.vagnermartins.marcatento.singleton.SingletonAdapter;

import java.util.List;

/**
 * Created by vagnnermartins on 20/03/15.
 */
public class DeleteGamesAsyncTask extends AsyncTask<Void, Void, Void> {

    private final List<Game> games;

    public DeleteGamesAsyncTask(List<Game> games){
        this.games = games;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (Game game : games){
            SingletonAdapter.getInstance().getAdapter().delete(game);
        }
        return null;
    }
}
