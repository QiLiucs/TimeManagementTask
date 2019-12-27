package com.example.kiki.timemanagementtask;

import android.widget.EditText;

public class Validation {
    static boolean checkEmpty(EditText editText){
        String s = editText.getText().toString();
        if(s.trim().length() == 0){
            editText.requestFocus();
            editText.setError("can't be empty");
            return true;
        }
        return false;
    }

    static boolean checkLaterThan(EditText startDateEdit, EditText ddlEdit){
        if(checkEmpty(startDateEdit) || checkEmpty(ddlEdit))
            return false;
        if(startDateEdit.getText().toString().compareTo(ddlEdit.getText().toString()) > 0){
            startDateEdit.setError("should be earlier than deadline");
            startDateEdit.requestFocus();
            return false;
        }
        return true;

    }

    public static boolean isValid(EditText... editTexts){
        for(EditText editText : editTexts){
            if(Validation.checkEmpty(editText))
                return false;
        }
        int size = editTexts.length;
        if(!Validation.checkLaterThan(editTexts[size-2], editTexts[size-1])){
            return false;
        }
        return true;
    }

}
