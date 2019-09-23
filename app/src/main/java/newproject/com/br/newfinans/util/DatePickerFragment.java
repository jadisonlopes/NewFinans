package newproject.com.br.newfinans.util;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jadison on 19/02/2018.
 */

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private EditText editText;
    private Date date;

    public DatePickerFragment(View view, Date date){
        this.editText = (EditText)view;
        this.date = date;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c;
        c = Calendar.getInstance();
        if (date!=null)
            c.setTime(date);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this,year,month,day);

        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        editText.setText(Util.PreencheZeros(String.valueOf(day),2)+"/"+Util.PreencheZeros(String.valueOf(month+1),2)+"/"+Util.PreencheZeros(String.valueOf(year),4));
    }

}