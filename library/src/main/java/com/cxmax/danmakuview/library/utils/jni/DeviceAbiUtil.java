package com.cxmax.danmakuview.library.utils.jni;

import android.os.Build;
import android.os.Environment;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

/**
 * @describe :the following codes are picked up from
 *            https://github.com/Bilibili/DanmakuFlameMaster/tree/master/DanmakuFlameMaster/src/main/java/tv/cjump/jni/DeviceUtils.java
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-6.
 */

public class DeviceAbiUtil {

    public static final String ABI_X86 = "x86";

    public static final String ABI_MIPS = "mips";

    static final int UNKNOWN = 0x10;
    static final int ARM = 0x11;
    static final int X86 = 0x12;
    static final int MIPS = 0x13;
    static final int ARM64 = 0x14;

    @IntDef(value = {UNKNOWN, ARM, X86, MIPS, ARM64})
    @Retention(RetentionPolicy.SOURCE)
    @interface ARCH {
    }

    @ARCH
    private static int sArch = UNKNOWN;

    private static final int EM_ARM = 40;
    private static final int EM_386 = 3;
    private static final int EM_MIPS = 8;
    private static final int EM_AARCH64 = 183;

    @ARCH public static synchronized int getMyCpuArch() {
        byte[] data = new byte[20];
        File libc = new File(Environment.getRootDirectory(), "lib/libc.so");
        if (libc.canRead()) {
            RandomAccessFile fp = null;
            try {
                fp = new RandomAccessFile(libc, "r");
                fp.readFully(data);
                int machine = (data[19] << 8) | data[18];
                switch (machine) {
                    case EM_ARM:
                        sArch = ARM;
                        break;
                    case EM_386:
                        sArch = X86;
                        break;
                    case EM_MIPS:
                        sArch = MIPS;
                        break;
                    case EM_AARCH64:
                        sArch = ARM64;
                        break;
                    default:
                        Log.e("NativeBitmapFactory", "libc.so is unknown arch: " + Integer.toHexString(machine));
                        break;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fp != null) {
                    try {
                        fp.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sArch;
    }

    public static String get_CPU_ABI() {
        return Build.CPU_ABI;
    }

    public static String get_CPU_ABI2() {
        try {
            Field field = Build.class.getDeclaredField("CPU_ABI2");
            if (field == null)
                return null;

            Object fieldValue = field.get(null);
            if (!(fieldValue instanceof String)) {
                return null;
            }

            return (String) fieldValue;
        } catch (Exception e) {

        }

        return null;
    }

    public static boolean supportABI(String requestAbi) {
        String abi = get_CPU_ABI();
        if (!TextUtils.isEmpty(abi) && abi.equalsIgnoreCase(requestAbi))
            return true;

        String abi2 = get_CPU_ABI2();
        return !TextUtils.isEmpty(abi2) && abi.equalsIgnoreCase(requestAbi);

    }

    public static boolean supportX86() {
        return supportABI(ABI_X86);
    }

    public static boolean supportMips() {
        return supportABI(ABI_MIPS);
    }

    public static boolean isARMSimulatedByX86() {
        int arch = getMyCpuArch();
        return !supportX86() && X86 == arch;
    }

    public static boolean isMiBox2Device() {
        String manufacturer = Build.MANUFACTURER;
        String productName = Build.PRODUCT;
        return manufacturer.equalsIgnoreCase("Xiaomi")
                && productName.equalsIgnoreCase("dredd");
    }

    public static boolean isMagicBoxDevice() {
        String manufacturer = Build.MANUFACTURER;
        String productName = Build.PRODUCT;
        return manufacturer.equalsIgnoreCase("MagicBox")
                && productName.equalsIgnoreCase("MagicBox");
    }

    public static boolean isProblemBoxDevice() {
        return isMiBox2Device() || isMagicBoxDevice();
    }

    public static boolean isRealARMArch() {
        int arch = getMyCpuArch();
        return (supportABI("armeabi-v7a") || supportABI("armeabi")) && ARM == arch;
    }

    public static boolean isRealX86Arch() {
        int arch = getMyCpuArch();
        return supportABI(ABI_X86) || X86 == arch;
    }
}
