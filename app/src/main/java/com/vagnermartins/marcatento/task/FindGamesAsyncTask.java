package com.vagnermartins.marcatento.task;

import android.os.AsyncTask;

import com.codeslap.persistence.Constraint;
import com.vagnermartins.marcatento.callback.Callback;
import com.vagnermartins.marcatento.entity.Game;
import com.vagnermartins.marcatento.singleton.SingletonAdapter;

import java.util.List;

/**
 * Created by vagnnermartins on 20/03/15.
 */
public class FindGamesAsyncTask extends AsyncTask<Void, Void, List<Game>> {

    private final Callback callback;

    public FindGamesAsyncTask(Callback callback){
        this.callback = callback;
    }

    @Override
    protected List<Game> doInBackground(Void... voids) {
        return SingletonAdapter.getInstance().getAdapter().findAll(Game.class);
    }

    @Override
    protected void onPostExecute(List<Game> result) {
        super.onPostExecute(result);
        callback.onReturn(null, result);
    }
}
