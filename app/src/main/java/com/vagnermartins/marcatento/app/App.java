package com.vagnermartins.marcatento.app;

import android.app.Application;
import android.os.AsyncTask;

import com.codeslap.persistence.DatabaseSpec;
import com.codeslap.persistence.PersistenceConfig;
import com.vagnermartins.marcatento.entity.Game;
import com.vagnermartins.marcatento.singleton.SingletonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vagnnermartins on 19/03/15.
 */
public class App extends Application {

    private List<AsyncTask<?,?,?>> tasks;
    public List<Game> games;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        tasks = new ArrayList<>();
        DatabaseSpec database = PersistenceConfig.registerSpec(1);
        database.match(Game.class);
        SingletonAdapter.getInstance(getApplicationContext());
    }

    public void registerTask(AsyncTask task){
        tasks.add(task);
    }

    private void unregisterTasks(){
        for (AsyncTask task :tasks){
            if(task != null){
                task.cancel(true);
            }
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterTasks();

    }
}
