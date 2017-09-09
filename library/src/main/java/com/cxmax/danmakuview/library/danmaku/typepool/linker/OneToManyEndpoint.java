package com.cxmax.danmakuview.library.danmaku.typepool.linker;

import android.support.annotation.NonNull;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public interface OneToManyEndpoint<T> {


    void withLinker(@NonNull Linker<T> linker);

    void withClassLinker(@NonNull ClassLinker<T> classLinker);

}
