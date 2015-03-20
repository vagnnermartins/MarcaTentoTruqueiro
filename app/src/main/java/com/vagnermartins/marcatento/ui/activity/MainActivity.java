package com.vagnermartins.marcatento.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vagnermartins.marcatento.R;
import com.vagnermartins.marcatento.app.App;
import com.vagnermartins.marcatento.entity.Game;
import com.vagnermartins.marcatento.singleton.SingletonAdapter;
import com.vagnermartins.marcatento.ui.helper.MainUIHelper;


public class MainActivity extends ActionBarActivity {

    private App app;
    private MainUIHelper ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        app = (App) getApplication();
        ui = new MainUIHelper(getWindow().getDecorView().findViewById(android.R.id.content));
        setSupportActionBar(ui.toolbar);
    }

    public void onWeClick(View view){
        int atual = Integer.parseInt(ui.weScore.getText().toString());
        Button b = (Button) view;
        int soma = Integer.parseInt(b.getTag().toString()) + atual;
        if(checkScore(ui.weScore, soma)){
            showMessage(ui.weScore.getId(),
                    soma, Integer.parseInt(ui.theyScore.getText().toString()));
        }
    }

    public void onTheyClick(View view){
        int atual = Integer.parseInt(ui.theyScore.getText().toString());
        Button b = (Button) view;
        int soma = Integer.parseInt(b.getTag().toString()) + atual;
        if(checkScore(ui.theyScore, soma)){
            showMessage(ui.theyScore.getId(),
                    soma, Integer.parseInt(ui.weScore.getText().toString()));
        }
    }

    private boolean checkScore(TextView textScore, int soma){
        boolean retorno = false;
        if(soma >= 12){
            textScore.setText(String.valueOf(12));
            retorno = true;
        }else if(soma < 0)
            textScore.setText(String.valueOf(0));
        else
            textScore.setText(String.valueOf(soma));
        return retorno;
    }

    private void showMessage(int winnerId, int winnerScore, int loserScore) {
        int diferencaDePontos = winnerScore - loserScore;
        String message;
        String winner;
        if(winnerId == ui.weScore.getId()){
            winner = ui.weName.getText().toString().equals("") ? "Nós" : ui.weName.getText().toString();
            message = checkMessage(winner, loserScore, diferencaDePontos);
        }else{
            winner = ui.theyName.getText().toString().equals("") ? "Eles" : ui.theyName.getText().toString();
            message = checkMessage(winner, loserScore, diferencaDePontos);
        }
        saveHistory();
        showDialog(message);
    }

    private String checkMessage(String winner, int loserScore, int diferencaDePontos) {
        String mensagem;
        if(diferencaDePontos >= 3 && diferencaDePontos < 6){
            mensagem = String.format(getString(R.string.message_hard), winner, loserScore);
        }else if(diferencaDePontos >=6 && diferencaDePontos < 9){
            mensagem = String.format(getString(R.string.message_medium), winner, loserScore);
        }else if(diferencaDePontos >=9){
            mensagem = String.format(getString(R.string.message_easy), winner, loserScore);
        }else{
            mensagem = String.format(getString(R.string.message_beginner), winner, loserScore);
        }
        return mensagem;
    }

    private void showDialog(String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.loser);
        builder.setMessage(mensagem);
        builder.setPositiveButton(android.R.string.ok, onPositiveButton());
        builder.show();
    }

    private DialogInterface.OnClickListener onPositiveButton() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                reset();
            }
        };
    }

    private void reset() {
        ui.weScore.setText(String.valueOf("0"));
        ui.theyScore.setText(String.valueOf("0"));
    }

    private void saveHistory(){
        Game game = new Game();
        game.setWeName(ui.weName.getText().toString());
        game.setTheyName(ui.theyName.getText().toString());
        game.setWeScore(Integer.valueOf(ui.weScore.getText().toString()));
        game.setTheyScore(Integer.valueOf(ui.theyScore.getText().toString()));
        SingletonAdapter.getInstance().getAdapter().store(game);
        if(app.games != null){
            app.games.add(game);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_new_game:
                reset();
                break;
            case R.id.menu_history:
                Intent intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
