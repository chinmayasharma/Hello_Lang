package jackah2.hellolangfeed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ProfileEditor extends AppCompatActivity {

    private EditText nameEdit;
    private Spinner langSel, typeSel, statSel;
    private Button applyButton;

    private SharedPreferences sharedPref;

    public static final String PRER_FILE_NAME = "user_options";
    public static final String NAME = "name";
    public static final String LANGUAGE = "language";
    public static final String TYPE = "type";
    public static final String STATUS = "status";
    private static final String LANG_SEL_POS = "spinner_pos.lang";
    private static final String TYPE_SEL_POS = "spinner_pos.type";
    private static final String STAT_SEL_POS = "spinner_pos.stat";

    public static int DEF_POS = -1;
    public static String DEF_NAME = Feed.getContext().getResources().getString(R.string.default_username);
    public static String DEF_LANG = Feed.getContext().getResources().getString(R.string.default_language);
    public static String DEF_TYPE = Feed.getContext().getResources().getString(R.string.default_type);
    public static String DEF_STAT = Feed.getContext().getResources().getString(R.string.default_status);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editor);

        nameEdit = (EditText) findViewById(R.id.edit_username);

        langSel = (Spinner) findViewById(R.id.choose_language);
        typeSel = (Spinner) findViewById(R.id.choose_type);
        statSel = (Spinner) findViewById(R.id.choose_status);

        langSel.setAdapter(getAdapter(R.array.choose_languages));
        typeSel.setAdapter(getAdapter(R.array.choose_types));
        statSel.setAdapter(getAdapter(R.array.choose_statuses));

        sharedPref = this.getSharedPreferences(PRER_FILE_NAME, MODE_PRIVATE);

        String name = sharedPref.getString(NAME, DEF_NAME);
        int langSelPos = sharedPref.getInt(LANG_SEL_POS, DEF_POS);
        int typeSelPos = sharedPref.getInt(TYPE_SEL_POS, DEF_POS);
        int statSelPos = sharedPref.getInt(STAT_SEL_POS, DEF_POS);

        if (langSelPos != DEF_POS) langSel.setSelection(langSelPos);
        if (typeSelPos != DEF_POS) typeSel.setSelection(typeSelPos);
        if (statSelPos != DEF_POS) statSel.setSelection(statSelPos);

        applyButton = (Button) findViewById(R.id.apply_button);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                Language lang = Language.match((String) langSel.getSelectedItem());
                UserType type = UserType.match((String) typeSel.getSelectedItem());
                Status status = Status.match((String) statSel.getSelectedItem());

                //TODO Change user options

                sharedPref = getApplicationContext().getSharedPreferences(PRER_FILE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString(NAME, name);
                editor.putString(LANGUAGE, (String) langSel.getSelectedItem());
                editor.putString(TYPE, (String) typeSel.getSelectedItem());
                editor.putString(STATUS, (String) statSel.getSelectedItem());

                editor.putInt(LANG_SEL_POS, langSel.getSelectedItemPosition());
                editor.putInt(TYPE_SEL_POS, typeSel.getSelectedItemPosition());
                editor.putInt(STAT_SEL_POS, statSel.getSelectedItemPosition());
                editor.commit();

                startActivity(new Intent(getApplicationContext(), Feed.class));
            }
        });
    }

    private ArrayAdapter<CharSequence> getAdapter(int textArrayResId){
        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this, textArrayResId,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
