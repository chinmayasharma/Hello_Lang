package jackah2.hellolangfeed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class FilterChooser extends AppCompatActivity{

    private TextView languageText, typeText, statusText;
    private Spinner languageSel, typeSel, statusSel;
    private Button clearFilters, applyFilters;

    private SharedPreferences sharedPref;

    private static final String LANG_SEL_POS = "spinner_pos.lang";
    private static final String TYPE_SEL_POS = "spinner_pos.type";
    private static final String STAT_SEL_POS = "spinner_pos.stat";
    private static final String PRER_FILE_NAME = "spinner_options";

    private static final int DEF_POS = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_chooser);

        languageText = (TextView) findViewById(R.id.language_select_text);
        typeText = (TextView) findViewById(R.id.user_type_select_text);
        statusText = (TextView) findViewById(R.id.status_select_text);

        languageSel = (Spinner) findViewById(R.id.language_select_spinner);
        typeSel = (Spinner) findViewById(R.id.user_type_select_spinner);
        statusSel = (Spinner) findViewById(R.id.status_select_spinner);

        clearFilters = (Button) findViewById(R.id.clear_filters_button);
        applyFilters = (Button) findViewById(R.id.apply_filters_button);

        languageText.setText(R.string.language_selector);
        typeText.setText(R.string.type_selector);
        statusText.setText(R.string.status_selector);

        languageSel.setAdapter(getAdapter(R.array.languages));
        typeSel.setAdapter(getAdapter(R.array.types));
        statusSel.setAdapter(getAdapter(R.array.statuses));

        sharedPref = this.getSharedPreferences(PRER_FILE_NAME, MODE_PRIVATE);

        final int langSelPos = sharedPref.getInt(LANG_SEL_POS, DEF_POS);
        int typeSelPos = sharedPref.getInt(TYPE_SEL_POS, DEF_POS);
        int statSelPos = sharedPref.getInt(STAT_SEL_POS, DEF_POS);

        if (langSelPos != DEF_POS) languageSel.setSelection(langSelPos);
        if (typeSelPos != DEF_POS) typeSel.setSelection(typeSelPos);
        if (statSelPos != DEF_POS) statusSel.setSelection(statSelPos);


        languageSel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                Feed.getUserFilter().setLanguage(Language.match(item));

                ((Feed) Feed.getContext()).updateFilter();

                sharedPref = getApplicationContext().getSharedPreferences(PRER_FILE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putInt(LANG_SEL_POS, position);
                editor.commit();
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

                sharedPref = getApplicationContext().getSharedPreferences(PRER_FILE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putInt(TYPE_SEL_POS, position);
                editor.commit();
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

                sharedPref = getApplicationContext().getSharedPreferences(PRER_FILE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putInt(STAT_SEL_POS, position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        clearFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languageSel.setSelection(0);
                typeSel.setSelection(0);
                statusSel.setSelection(0);

                Feed.getUserFilter().clear();
                ((Feed) Feed.getContext()).updateFilter();
            }
        });

        applyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
