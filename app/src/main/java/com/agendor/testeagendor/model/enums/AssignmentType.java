package com.agendor.testeagendor.model.enums;

import androidx.annotation.NonNull;

public enum AssignmentType {

    EMAIL, LIGACAO, PROPOSTA, REUNIAO, VISITA, OUTRO;


    public String getName(){
        return getName(this);
    }

    public static String getName(AssignmentType value){
        switch (value){
            case EMAIL: return "E-MAIL";
            case LIGACAO: return "LIGAÇÃO";
            case PROPOSTA: return "PROPOSTA";
            case REUNIAO: return "REUNIÃO";
            case VISITA: return "VISITA";
            default: return "OUTRO";
        }
    }

    public static AssignmentType getNameFromString(String value){
        switch (value){
            case "E-MAIL": return EMAIL;
            case "LIGAÇÃO": return LIGACAO;
            case "PROPOSTA": return PROPOSTA;
            case "REUNIÃO": return REUNIAO;
            case "VISITA": return VISITA;
            default: return OUTRO;
        }
    }
}
