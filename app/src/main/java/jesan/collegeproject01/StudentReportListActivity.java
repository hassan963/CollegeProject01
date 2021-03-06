package jesan.collegeproject01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Hassan M.Ashraful on 5/7/2016.
 */
public class StudentReportListActivity extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    DataBaseHelper dataBaseHelper;
    Cursor cursor;
    StudentReportDataAdapter studentReportDataAdapter;
    private SharedPreferences sharedPreferences;
    TextView nameteacherTV, idteacherTV, classteacherTV, secteacherTV, subteacherTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_report_list_view);


        nameteacherTV = (TextView) findViewById(R.id.nameteacherTV);
        idteacherTV = (TextView) findViewById(R.id.idteacherTV);
        classteacherTV = (TextView) findViewById(R.id.classteacherTV);
        secteacherTV = (TextView) findViewById(R.id.secteacherTV);
        subteacherTV = (TextView) findViewById(R.id.subteacherTV);

        listView = (ListView) findViewById(R.id.report_list_view);

        studentReportDataAdapter = new StudentReportDataAdapter(getApplicationContext(), R.layout.row_list_student_report);
        listView.setAdapter(studentReportDataAdapter);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        cursor = dataBaseHelper.getStudentReport(sqLiteDatabase);
        if (cursor.moveToNext()){
            do {
                String name, id, date, roll;
                name = cursor.getString(0);
                id = cursor.getString(1);
                date = cursor.getString(2);
                roll = cursor.getString(3);

                StudentReportProvider studentReportProvider = new StudentReportProvider(name, id, date, roll);
                studentReportDataAdapter.add(studentReportProvider);

            }while (cursor.moveToNext());
        }

        sharedPreferences = getSharedPreferences("jesan.collegeproject01.SIGNUP", MODE_PRIVATE);

        LoadPreferences();


    }


    private void LoadPreferences(){
        String name = sharedPreferences.getString("NAME","");
        String id = sharedPreferences.getString("ID","");
        String teacheClass = sharedPreferences.getString("CLASS","");
        String sec = sharedPreferences.getString("SEC","");
        String sub = sharedPreferences.getString("SUB","");
        nameteacherTV.setText(name);
        idteacherTV.setText(id);
        classteacherTV.setText(teacheClass);
        secteacherTV.setText(sec);
        subteacherTV.setText(sub);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_design, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.logout){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
