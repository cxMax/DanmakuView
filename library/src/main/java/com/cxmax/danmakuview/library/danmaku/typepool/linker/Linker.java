package com.cxmax.danmakuview.library.danmaku.typepool.linker;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public interface Linker<T> {

    @IntRange(from = 0) int index(@NonNull T t);
}
