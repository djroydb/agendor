package com.agendor.testeagendor.tasks;

import android.content.Context;

import com.agendor.testeagendor.controller.AssignmentController;
import com.agendor.testeagendor.domain.model.Assignment;
import com.agendor.testeagendor.utils.asyncbase.AsyncTaskBase;
import com.agendor.testeagendor.utils.asyncbase.DelegateListener;
import com.agendor.testeagendor.utils.asyncbase.ResponseData;

public class InsertAssignmentTask  extends AsyncTaskBase<Void, Void, Boolean> {

    private Context context;
    private Assignment assignment;

    public InsertAssignmentTask(Context context, Assignment assignment, DelegateListener<Boolean> delegate) {
        super(context, delegate);
        this.context = context;
        this.assignment = assignment;
    }

    @Override
    protected ResponseData<Boolean> doInBackground(Void... voids) {
        boolean success = false;
        String message = null;
        AssignmentController control;

        try {
            control = new AssignmentController(context);
            success = control.insert(assignment.getType(), assignment.getDate(), assignment.getClient(), assignment.getDescription(), assignment.isDone());
        } catch (Exception e) {
            message = e.getMessage();
        }


        return new ResponseData<>(success, message, success);
    }
}
