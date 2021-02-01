package com.agendor.testeagendor.tasks;

import android.content.Context;

import com.agendor.testeagendor.controller.AssignmentController;
import com.agendor.testeagendor.model.Assignment;
import com.agendor.testeagendor.utils.asyncbase.AsyncTaskBase;
import com.agendor.testeagendor.utils.asyncbase.DelegateListener;
import com.agendor.testeagendor.utils.asyncbase.ResponseData;

import java.util.ArrayList;
import java.util.List;

public class ReadAssignmentTask extends AsyncTaskBase<Void, Void, List<Assignment>> {

    private Context context;

    public ReadAssignmentTask(Context context, DelegateListener<List<Assignment>> delegate) {
        super(context, delegate);
        this.context = context;
    }

    @Override
    protected ResponseData<List<Assignment>> doInBackground(Void... voids) {
        boolean success = false;
        String message = null;
        List<Assignment> data = new ArrayList<>();
        AssignmentController control;

        try {
            control = new AssignmentController(context);
            data = control.read();
            success = true;
        } catch (Exception e) {
            message = e.getMessage();
        }


        return new ResponseData<>(success, message, data);
    }
}
