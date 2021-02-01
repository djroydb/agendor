package com.agendor.testeagendor.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.agendor.testeagendor.model.Assignment;
import com.agendor.testeagendor.model.IconToolbar;
import com.agendor.testeagendor.model.enums.AssignmentType;
import com.agendor.testeagendor.tasks.ReadAssignmentTask;
import com.agendor.testeagendor.utils.adapters.AssignmentItemRecycleAdapter;
import com.agendor.testeagendor.utils.asyncbase.DelegateListener;
import com.agendor.testeagendor.utils.asyncbase.ResponseData;
import com.agendor.testeagendor.utils.components.CustomIconToolbar;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.agendor.testeagendor.R;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Robson Freitas 28/01/2021
 */
public class AssignmentListActivity extends AppCompatActivity {

    private HashMap<AssignmentType, Integer> list;
    private LinearLayout customIconToolbar;

    private FloatingActionButton addButton;
    private ProgressBar progress;
    private LinearLayout linearToolbar;
    private Button button;

    private RecyclerView recyclerView;
    private AssignmentItemRecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.progress = findViewById(R.id.list_progress);
        this.recyclerView = findViewById(R.id.assignment_recycle_list);
        this.linearToolbar = findViewById(R.id.linear_toolbar);

        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("Timeline");

        addButton = (FloatingActionButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButton.setClickable(false);
                startActivityForResult(new Intent(AssignmentListActivity.this, AgendorActivity.class), 100);
            }
        });

        this.readAssignment();
    }


    /**
     * Busca os dados no banco e inicia a distibuição aos seus componentes.
     */
    private void readAssignment(){
        setProgress(true);
        new ReadAssignmentTask(this, new DelegateListener<List<Assignment>>() {
            @Override
            public void onPosExecute(ResponseData<List<Assignment>> response) {
                if(response.isSuccessfully()){
                    List<Assignment> l = getAssignmentInPeriod(response.getData());
                    Collections.sort(l);
                    setValues(l);
                }else{
                    new AlertDialog.Builder(
                            AssignmentListActivity. this,R.style.MyDatePickerDialogTheme)
                            .setMessage(response.getMessage())
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    setProgress(false);
                                }
                            })
                            .create().show();
                }
            }
        }).execute();
    }

    /**
     * Adiciona os dados em seus componentes.
     * @param list List<Assignment>
     */
    private void setValues(List<Assignment> list){
        addToolbarIcons(list);
        adapter = new AssignmentItemRecycleAdapter(list);
        recyclerView.setAdapter(adapter);
        setProgress(false);
    }

    private void addToolbarIcons(List<Assignment> list){
        List<IconToolbar> l = assignmentTypeHashMap(list);
        linearToolbar.removeAllViews();
        for (IconToolbar icon : l){
            customIconToolbar = new CustomIconToolbar(AssignmentListActivity.this, icon);
            linearToolbar.addView(customIconToolbar);
        }
    }

    /**
     * Exibe o Progressbar e bloqueia os outros componentes
     * @param value boolean
     */
    private void setProgress(boolean value){
        progress.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        addButton.setClickable(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            readAssignment();
        }
    }

    /**
     * verifica se a data esta dentro do periodo informado em dias
     * @param newDate DateTime - Data a ser verificada
     * @param daysPeriod int - Periodo em dias a partir da data atual
     * @return boolean
     */
    private boolean verifyIsInPeriod(DateTime newDate, int daysPeriod){
        DateTime atualDate = DateTime.now();
        DateTime lastDate = atualDate.plusDays(daysPeriod);
        if(newDate.isAfter(lastDate))
            return false;
        return true;
    }

    /**
     * Filtra a lista para ter apenas Assignments de um determinado periodo.
     * @param list List<Assignment>
     * @return List<Assignment>
     */
    private List<Assignment> getAssignmentInPeriod(List<Assignment> list){
        List<Assignment> newList = new ArrayList<>();
        for (Assignment assignment : list){
            if (verifyIsInPeriod(assignment.getDate(), 7)){
                newList.add(assignment);
            }
        }
        return newList;
    }

    private List<IconToolbar> assignmentTypeHashMap(List<Assignment> assignmentList){
        list = new HashMap<>();
        List<IconToolbar> iconToolbarList = new ArrayList<>();
        for (int i = 0; i < assignmentList.size(); i++){
            Assignment l = assignmentList.get(i);
            if(list.containsKey(assignmentList.get(i).getType())){
                list.put(l.getType(), ((Integer)list.get(l.getType()))+1);

            }else{
                list.put(l.getType(), 1);
            }
        }

        for (AssignmentType type : list.keySet()){
            iconToolbarList.add(new IconToolbar(type,list.get(type)));
        }

        return iconToolbarList;
    }
}