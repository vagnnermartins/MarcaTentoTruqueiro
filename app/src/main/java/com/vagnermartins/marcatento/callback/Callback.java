package com.vagnermartins.marcatento.callback;

/**
 * Created by vagnnermartins on 20/03/15.
 */
public interface Callback {

    public void onReturn(Exception error, Object... objects);
}
