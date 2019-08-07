package jackah2.hellolangfeed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class FilterChooser extends AppCompatActivity{

    private Spinner languageSel, typeSel, statusSel;

    private SharedPreferences sharedPref;

    private static final String LANG_SEL_POS = "spinner_pos.lang";
    private static final String TYPE_SEL_POS = "spinner_pos.type";
    private static final String STAT_SEL_POS = "spinner_pos.stat";
    private static final String PRER_FILE_NAME = "filter_spinner_options";

    private static final int DEF_POS = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_chooser);

        languageSel = (Spinner) findViewById(R.id.language_select_spinner);
        typeSel = (Spinner) findViewById(R.id.user_type_select_spinner);
        statusSel = (Spinner) findViewById(R.id.status_select_spinner);

        Button clearFilters = (Button) findViewById(R.id.clear_filters_button);
        Button applyFilters = (Button) findViewById(R.id.apply_filters_button);

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
                Language lang = Language.match((String) languageSel.getSelectedItem());
                UserType type = UserType.match((String) typeSel.getSelectedItem());
                Status status = Status.match((String) statusSel.getSelectedItem());

                Feed.getUserFilter().setLanguage(lang);
                Feed.getUserFilter().setType(type);
                Feed.getUserFilter().setStatus(status);

                ((Feed) Feed.getContext()).updateFilter();

                sharedPref = getApplicationContext().getSharedPreferences(PRER_FILE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putInt(LANG_SEL_POS, languageSel.getSelectedItemPosition());
                editor.putInt(TYPE_SEL_POS, typeSel.getSelectedItemPosition());
                editor.putInt(STAT_SEL_POS, statusSel.getSelectedItemPosition());
                editor.apply();

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
