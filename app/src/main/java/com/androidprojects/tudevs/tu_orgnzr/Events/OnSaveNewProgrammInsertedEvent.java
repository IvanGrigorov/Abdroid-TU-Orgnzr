package com.androidprojects.tudevs.tu_orgnzr.Events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.androidprojects.tudevs.tu_orgnzr.Contracts.ProgrammSQLContract;
import com.androidprojects.tudevs.tu_orgnzr.DataObjects.ProgrammRow;
import com.androidprojects.tudevs.tu_orgnzr.Display_Notes_Activity;
import com.androidprojects.tudevs.tu_orgnzr.Programm_Set_Activity;
import com.androidprojects.tudevs.tu_orgnzr.R;
import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.ImportNewSubjectHelper;

/**
 * Created by Ivan Grigorov on 23/04/2016.
 * Event Triggered when clicking to save new Programm
 */
public class OnSaveNewProgrammInsertedEvent extends AbstractEvent implements View.OnClickListener {

    // In the table container is stored the structure and the information of the programm
    TableLayout container;

    public OnSaveNewProgrammInsertedEvent(Context context, Activity activity, TableLayout container) {
        super(context, activity);
        this.container = container;
    }

    @Override
    public void onClick(View v) {

        // Get all rows of the programm table
        int count = this.container.getChildCount();

        // Get which day of the programm should be changed
        Spinner day_of_week = (Spinner) this.activity.findViewById(R.id.Day_Of_Week_Spinner);

        // Collect the information for each row(hour block) and add it as new input to the Database(Programm Table)
        for (int i=0; i < count - 1; i++) {
            View tableRow = this.container.getChildAt(i);
            View time = ((TableRow) tableRow).getChildAt(0);
            View spinner = ((TableRow) tableRow).getChildAt(1);
            View building_spinner = ((TableRow) tableRow).getChildAt(3);
            View lecturer = ((TableRow) tableRow).getChildAt(2);
            ImportNewSubjectHelper newSubjectHelper = new ImportNewSubjectHelper(v.getContext());
            newSubjectHelper.getContentValues().put(ProgrammSQLContract.SubjectTable.LECTURE_NAME_COLUMN, ((Spinner) spinner).getSelectedItem().toString());
            newSubjectHelper.getContentValues().put(ProgrammSQLContract.SubjectTable.DAY_OF_WEEK_COLUMN, ((Spinner) day_of_week).getSelectedItem().toString());
            newSubjectHelper.getContentValues().put(ProgrammSQLContract.SubjectTable.STARTS_AT_COLUMN, ((TextView) time).getText().toString());
            newSubjectHelper.getContentValues().put(ProgrammSQLContract.SubjectTable.ENDS_AT_COLUMN, ((TextView) time).getText().toString());
            newSubjectHelper.getContentValues().put(ProgrammSQLContract.SubjectTable.BUILDING_COLUMN, ((Spinner) building_spinner).getSelectedItem().toString());
            Log.d("SQL", newSubjectHelper.toString());
            newSubjectHelper.InsertValues();

            Intent intent = new Intent(this.context, Programm_Set_Activity.class);
            this.context.startActivity(intent);
            this.activity.finish();
        }
    }
}
