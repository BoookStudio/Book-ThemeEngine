
package com.book.support.laml.data;

import com.book.support.laml.ScreenContext;

public class VariableUpdater {

    private VariableUpdaterManager mVariableUpdaterManager;

    public VariableUpdater(VariableUpdaterManager m) {
        mVariableUpdaterManager = m;
    }

    public void finish() {
    }

    protected ScreenContext getContext() {
        return mVariableUpdaterManager.getContext();
    }

    public void init() {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void tick(long l) {
    }
}
