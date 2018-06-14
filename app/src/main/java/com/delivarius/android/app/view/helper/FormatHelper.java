package com.delivarius.android.app.view.helper;

import android.support.annotation.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatHelper {

    private static final DecimalFormat moedaFormater = new DecimalFormat("'R$ ' #,##");
    private static final SimpleDateFormat formatadorDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @NonNull
    public static String formatarMoeda(BigDecimal valor){
        return moedaFormater.format(valor);
    }

    @NonNull
    public static BigDecimal round2Decimal(BigDecimal valor){
        return valor.setScale(2, RoundingMode.FLOOR);
    }

    public static String formatarDataHora(Date date){
        return formatadorDataHora.format(date);
    }

}
