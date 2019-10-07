package com.dr7.salesmanmanager;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LocaleAppUtils {
    private static Locale locale;

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale localeIn) {
        locale = localeIn;
        if(locale != null) {
            Locale.setDefault(locale);
        }
    }
    public static void setConfigChange(Context ctx){
        if(locale != null){
            Locale.setDefault(locale);

            Configuration configuration = ctx.getResources().getConfiguration();
            DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
            configuration.locale=locale;

            ctx.getResources().updateConfiguration(configuration, displayMetrics);
        }
    }
}