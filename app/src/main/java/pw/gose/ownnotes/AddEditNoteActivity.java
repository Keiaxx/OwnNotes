package pw.gose.ownnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "pw.gose.ownnotes.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "pw.gose.ownnotes.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "pw.gose.ownnotes.EXTRA_PRIORITY";
    public static final String EXTRA_ID =
            "pw.gose.ownnotes.EXTRA_ID";

    private EditText title;
    private EditText description;
    private SeekBar priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.edit_text_title);
        description = findViewById(R.id.edit_text_description);
        priority = findViewById(R.id.number_priority);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            title.setText(intent.getStringExtra(EXTRA_TITLE));
            description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            priority.setProgress(intent.getIntExtra(EXTRA_PRIORITY, 0));
        } else {
            setTitle("Add Note");
        }

    }

    private void saveNote() {
        String t = title.getText().toString();
        String desc = description.getText().toString();
        int pri = priority.getProgress();

        if (t.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(this, "Title and Description cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, t);
        data.putExtra(EXTRA_DESCRIPTION, desc);
        data.putExtra(EXTRA_PRIORITY, pri);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
