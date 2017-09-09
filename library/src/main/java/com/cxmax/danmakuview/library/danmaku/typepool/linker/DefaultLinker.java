package com.cxmax.danmakuview.library.danmaku.typepool.linker;

import android.support.annotation.NonNull;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public class DefaultLinker<T> implements Linker<T> {

    @Override
    public int index(@NonNull T t) {
        return 0;
    }

}
