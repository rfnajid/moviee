package com.nnn.moviee.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.nnn.moviee.utils.StackRemoteViewsFactory;

/**
 * Created by ridhaaaaazis on 16/03/18.
 */

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}