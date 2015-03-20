package com.vagnermartins.marcatento.ui.activity;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.gc.materialdesign.widgets.SnackBar;
import com.vagnermartins.marcatento.R;
import com.vagnermartins.marcatento.adapter.GameAdapter;
import com.vagnermartins.marcatento.app.App;
import com.vagnermartins.marcatento.callback.Callback;
import com.vagnermartins.marcatento.entity.Game;
import com.vagnermartins.marcatento.enums.StatusEnum;
import com.vagnermartins.marcatento.singleton.SingletonAdapter;
import com.vagnermartins.marcatento.task.DeleteGamesAsyncTask;
import com.vagnermartins.marcatento.task.FindGamesAsyncTask;
import com.vagnermartins.marcatento.ui.helper.HistoryUIHelper;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends ActionBarActivity {

    private App app;
    private HistoryUIHelper ui;
    private List<Game> selecteds;
    private List<Game> removedCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        init();
        checkUpdate();
    }

    private void checkUpdate() {
        if(app.games == null){
            checkStatus(StatusEnum.INICIO);
        }else{
            loadValues();
        }
    }

    private void init() {
        app = (App) getApplication();
        ui = new HistoryUIHelper(getWindow().getDecorView().findViewById(android.R.id.content));
        setSupportActionBar(ui.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ui.list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        ui.list.setMultiChoiceModeListener(onMultiChouModeListener());

    }

    private void checkStatus(StatusEnum status){
        if(status == StatusEnum.INICIO){
            FindGamesAsyncTask task = new FindGamesAsyncTask(onFindGameCallback());
            task.execute();
            app.registerTask(task);
        }else if(status == StatusEnum.EXECUTANDO){
            ui.progress.setVisibility(View.VISIBLE);
        }else if(status == StatusEnum.EXECUTADO){
            ui.progress.setVisibility(View.GONE);
        }
    }

    private Callback onFindGameCallback() {
        return new Callback() {
            @Override
            public void onReturn(Exception error, Object... objects) {
                app.games = (List<Game>) objects[0];
                loadValues();
                checkStatus(StatusEnum.EXECUTADO);
            }
        };
    }

    private AbsListView.MultiChoiceModeListener onMultiChouModeListener() {
        return new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long l, boolean checked) {
                Game selected = app.games.get(position);
                if(checked){
                    selecteds.add(selected);
                }else{
                    selecteds.remove(selected);
                }
                String title = selecteds.size() > 1 ? " selecionados" : " selecionado";
                mode.setTitle(selecteds.size() + title);
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                getMenuInflater().inflate(R.menu.menu_history, menu);
                selecteds = new ArrayList<>();
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_history_delete:
                        onDeleteSelected();
                        break;
                }
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                selecteds = null;
            }
        };
    }

    private void loadValues(){
        ui.list.setAdapter(new GameAdapter(this, R.layout.item_history, app.games));
        if(app.games.isEmpty()){
            ui.message.setVisibility(View.VISIBLE);
        }else{
            ui.message.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_history_delete:
                onDeleteSelected();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onDeleteSelected() {
        if(selecteds == null){
            removedCards = new ArrayList<>(app.games);
        }else{
            removedCards = new ArrayList<>(selecteds);
        }
        app.games.removeAll(removedCards);
        SnackBar snack = new SnackBar(this, getString(R.string.history_delete_message), getString(R.string.undo), onUndoClickListener());
        snack.setOnhideListener(onHideListener());
        snack.show();
        loadValues();
        return;
    }

    private SnackBar.OnHideListener onHideListener() {
        return new SnackBar.OnHideListener() {
            @Override
            public void onHide() {
                if(removedCards != null){
                    DeleteGamesAsyncTask task = new DeleteGamesAsyncTask(removedCards);
                    task.execute();
                    app.registerTask(task);
                }
            }
        };
    }

    private View.OnClickListener onUndoClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.games.addAll(removedCards);
                loadValues();
                removedCards = null;
            }
        };
    }

}
