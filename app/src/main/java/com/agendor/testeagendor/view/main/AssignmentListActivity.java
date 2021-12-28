package com.agendor.testeagendor.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.agendor.testeagendor.databinding.ActivityAssignmentListBinding;
import com.agendor.testeagendor.domain.model.Assignment;
import com.agendor.testeagendor.domain.model.IconToolbar;
import com.agendor.testeagendor.model.enums.AssignmentType;
import com.agendor.testeagendor.utils.adapters.AssignmentItemRecycleAdapter;
import com.agendor.testeagendor.utils.components.CustomIconToolbar;
import com.agendor.testeagendor.view.addassignment.AddAssignmentActivity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Robson Freitas 28/01/2021
 */
public class AssignmentListActivity extends AppCompatActivity {

    private ActivityAssignmentListBinding binding;
    private AssignmentListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityAssignmentListBinding.inflate(getLayoutInflater());
        this.setContentView(this.binding.getRoot());
        this.viewModel = new ViewModelProvider(this).get(AssignmentListViewModel.class);

        this.setSupportActionBar(binding.toolbar);

        this.binding.toolbarLayout.setTitle("Timeline");

        this.setListeners();
        this.readAssignment();
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == 100){
                        viewModel.refreshAssignments();
                    }
                }
            });

    private void setListeners(){
        binding.addButton.setOnClickListener(v -> {
            binding.addButton.setClickable(false);
            Intent intent = new Intent(this, AddAssignmentActivity.class);
            activityResultLauncher.launch(intent);
        });
    }

    /**
     * Busca os dados no banco e inicia a distibuição aos seus componentes.
     */
    private void readAssignment(){
        setProgress(true);
        viewModel.getAssignments().observe(this, assignments -> {
            List<Assignment> l = getAssignmentInPeriod(assignments);
            Collections.sort(l);
            setValues(l);
        });
    }

    /**
     * Adiciona os dados em seus componentes.
     * @param list List<Assignment>
     */
    private void setValues(List<Assignment> list){
        addToolbarIcons(list);
        AssignmentItemRecycleAdapter adapter = new AssignmentItemRecycleAdapter(list);
        binding.assignmentRecycleList.setAdapter(adapter);
        setProgress(false);
    }

    private void addToolbarIcons(List<Assignment> list){
        List<IconToolbar> l = assignmentTypeHashMap(list);
        binding.linearToolbar.removeAllViews();
        for (IconToolbar icon : l){
            LinearLayout customIconToolbar = new CustomIconToolbar(AssignmentListActivity.this, icon);
            binding.linearToolbar.addView(customIconToolbar);
        }
    }

    /**
     * Exibe o Progressbar e bloqueia os outros componentes
     * @param value boolean
     */
    private void setProgress(boolean value){
        binding.listProgress.setVisibility(value ? View.VISIBLE : View.GONE);
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
        binding.addButton.setClickable(true);
    }

    @Override
    protected void onDestroy() {
        viewModel.getAssignments().removeObservers(this);
        super.onDestroy();
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
        HashMap<AssignmentType, Integer> list = new HashMap<>();
        List<IconToolbar> iconToolbarList = new ArrayList<>();
        for (int i = 0; i < assignmentList.size(); i++){
            Assignment l = assignmentList.get(i);
            if(list.containsKey(assignmentList.get(i).getType())){
                list.put(l.getType(), ((Integer) list.get(l.getType()))+1);

            }else{
                list.put(l.getType(), 1);
            }
        }

        for (AssignmentType type : list.keySet()){
            iconToolbarList.add(new IconToolbar(type, list.get(type)));
        }

        return iconToolbarList;
    }
}