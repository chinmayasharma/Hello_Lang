package jackah2.hellolangfeed;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class FilterChooser extends AppCompatActivity{

    private TextView languageText, typeText, statusText;
    private Spinner languageSel, typeSel, statusSel;

    //private final SharedPreferences sharedPref = getSharedPreferences(PRER_FILE_NAME, MODE_PRIVATE);
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private static final String LANG_SEL_POS = "lang_sel_pos";
    private static final String TYPE_SEL_POS = "type_sel_pos";
    private static final String STAT_SEL_POS = "stats_sel_pos";
    //private static final String PRER_FILE_NAME = "spinner_options";

    private static final int DEF_POS = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_chooser);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

        languageText = (TextView) findViewById(R.id.language_select_text);
        typeText = (TextView) findViewById(R.id.user_type_select_text);
        statusText = (TextView) findViewById(R.id.status_select_text);

        languageSel = (Spinner) findViewById(R.id.language_select_spinner);
        typeSel = (Spinner) findViewById(R.id.user_type_select_spinner);
        statusSel = (Spinner) findViewById(R.id.status_select_spinner);

        int langSelPos = sharedPref.getInt(LANG_SEL_POS, DEF_POS);
        int typeSelPos = sharedPref.getInt(TYPE_SEL_POS, DEF_POS);
        int statSelPos = sharedPref.getInt(STAT_SEL_POS, DEF_POS);

        if (langSelPos != DEF_POS) languageSel.setSelection(langSelPos);
        if (typeSelPos != DEF_POS) typeSel.setSelection(typeSelPos);
        if (statSelPos != DEF_POS) statusSel.setSelection(statSelPos);

        languageText.setText(R.string.language_selector);
        typeText.setText(R.string.type_selector);
        statusText.setText(R.string.status_selector);

        languageSel.setAdapter(getAdapter(R.array.languages));
        typeSel.setAdapter(getAdapter(R.array.types));
        statusSel.setAdapter(getAdapter(R.array.statuses));



        languageSel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                Feed.getUserFilter().setLanguage(Language.match(item));

                ((Feed) Feed.getContext()).updateFilter();
                editor.putInt(LANG_SEL_POS, languageSel.getSelectedItemPosition());
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        typeSel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                Feed.getUserFilter().setType(UserType.match(item));

                ((Feed) Feed.getContext()).updateFilter();
                editor.putInt(TYPE_SEL_POS, typeSel.getSelectedItemPosition());
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        statusSel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                Feed.getUserFilter().setStatus(Status.match(item));

                ((Feed) Feed.getContext()).updateFilter();
                editor.putInt(STAT_SEL_POS, statusSel.getSelectedItemPosition());
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
