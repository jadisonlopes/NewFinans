package newproject.com.br.newfinans.util;

import android.content.SharedPreferences;

public class Preference {

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    public Preference(SharedPreferences preferences){
        this.preferences = preferences;
        this.editor = this.preferences.edit();
    }
}
