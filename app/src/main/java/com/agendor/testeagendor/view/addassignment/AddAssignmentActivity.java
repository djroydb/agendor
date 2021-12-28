package com.agendor.testeagendor.view.addassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;


import com.agendor.testeagendor.R;
import com.agendor.testeagendor.databinding.ActivityAddAssignmentBinding;
import com.agendor.testeagendor.domain.model.Assignment;
import com.agendor.testeagendor.model.enums.AssignmentType;
import com.agendor.testeagendor.tasks.InsertAssignmentTask;
import com.agendor.testeagendor.utils.asyncbase.DelegateListener;
import com.agendor.testeagendor.utils.asyncbase.ResponseData;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;

public class AddAssignmentActivity extends AppCompatActivity {

    private ActivityAddAssignmentBinding binding;

    private AssignmentType currentType;
    private DateTime currentDate;
    private DateTime currentHour;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currentDate = DateTime.now();
        currentHour = DateTime.now();

        binding.addAssignmentButton.setOnClickListener(addButtonClickListener);
        binding.dateLayout.setOnClickListener(dateLayoutListener);
        binding.hourLayout.setOnClickListener(hourLayoutListener);

        binding.toggleGroup.setOnCheckedChangeListener(toggleGroupListener);

    }

    /**
     * Exibe o Progressbar e bloqueia os outros componentes
     * @param value boolean
     */
    private void setProgress(boolean value){
        binding.agendorProgress.setVisibility(value ? View.VISIBLE : View.GONE);
        binding.toggleGroup.setVisibility(value ? View.INVISIBLE : View.VISIBLE);
        binding.dateLayout.setClickable(!value);
        binding.hourLayout.setClickable(!value);
        binding.clientEditText.setEnabled(!value);
        binding.descriptionEditText.setEnabled(!value);
        binding.addAssignmentButton.setEnabled(!value);
    }

    /**
     * Metodo do ToggledView
     * @param view
     */
    public void onToggleClicked(View view) {
        ((RadioGroup) view.getParent()).check(view.getId());
        currentType = AssignmentType.valueOf((String) view.getTag());
    }

    /**
     * Listener do RadioGroup toggleGroup
     * Para deixar apenas 1 opção do RadioGroup checked
     */
    private RadioGroup.OnCheckedChangeListener toggleGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                final RadioButton view = (RadioButton) radioGroup.getChildAt(j);
                view.setChecked(view.getId() == i);
            }
        }
    };

    /**
     * Listener do Layout que comporta a hora e a imagem.
     */
    private View.OnClickListener hourLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            timePickerDialog = new TimePickerDialog(AddAssignmentActivity.this, R.style.MyTimePickerWidgetStyle,
                    new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    currentHour = new DateTime(2000,1,1, hourOfDay, minute);
                    DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
                    binding.hourText.setText(currentHour.toString(formatter));
                }
            },12,0, true);
            timePickerDialog.setTitle("Selecione um horário");
            timePickerDialog.show();
        }
    };

    /**
     * Listener do layout que comporta a data e a imagem.
     */
    private View.OnClickListener dateLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar dataAtual = Calendar.getInstance();
            datePickerDialog = new DatePickerDialog(AddAssignmentActivity.this, R.style.MyDatePickerDialogTheme,
                    new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    currentDate = new DateTime(year, month+1, dayOfMonth, 0, 0);
                    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMM yyyy");
                    binding.dateText.setText(currentDate.toString(formatter));
                }
            }, dataAtual.get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }
    };

    /**
     * Listener do botão add
     */
    private View.OnClickListener addButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DateTime dateTime = new DateTime(currentDate.getYear(), currentDate.getMonthOfYear(),
                    currentDate.getDayOfMonth(), currentHour.getHourOfDay(), currentHour.getMinuteOfHour());
            Assignment assignment = new Assignment(currentType, dateTime, binding.clientEditText.getText().toString(),
                    binding.descriptionEditText.getText().toString(), false);
            new InsertAssignmentTask(AddAssignmentActivity.this, assignment, new DelegateListener<Boolean>() {
                @Override
                public void onPosExecute(ResponseData<Boolean> response) {
                    if (response.isSuccessfully()){
                        setResult(100);
                        finish();
                    }else {
                        new AlertDialog.Builder(
                                AddAssignmentActivity.this,R.style.MyDatePickerDialogTheme)
                                .setMessage(response.getMessage())
                                .create().show();
                        setProgress(false);
                    }

                }

            }
            ).execute();
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(MotionEvent.ACTION_OUTSIDE == event.getAction()){
            return false;
        }
        return super.onTouchEvent(event);
    }
}