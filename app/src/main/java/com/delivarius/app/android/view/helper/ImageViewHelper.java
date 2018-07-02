package com.delivarius.app.android.view.helper;

import android.widget.ImageView;

import com.delivarius.app.R;


public class ImageViewHelper {

    public static void setImageView(ImageView imageView, String image){

        switch (image) {
            case "agua_mineral_hydrate_20l.png" :
                imageView.setImageResource(R.drawable.agua_mineral_hydrate_20l);
                break;
            case "carvao_cariocao.png" :
                imageView.setImageResource(R.drawable.carvao_cariocao);
                break;
            case "cerveja_itaipava_lata_350ml.png" :
                imageView.setImageResource(R.drawable.cerveja_itaipava_lata_350ml);
                break;
            case "coca_cola_2l.png" :
                imageView.setImageResource(R.drawable.coca_cola_2l);
                break;
            case "pao_forma_integral_pullman_550g.png" :
                imageView.setImageResource(R.drawable.pao_forma_integral_pullman_550g);
                break;
            case "agua_mineral_fontes_debelem_20l.png" :
                imageView.setImageResource(R.drawable.agua_mineral_fontes_debelem_20l);
                break;
            case "agua_mineral_indaia_20l.png" :
                imageView.setImageResource(R.drawable.agua_mineral_indaia_20l);
                break;
            case "agua_mineral_inga_20l.png" :
                imageView.setImageResource(R.drawable.agua_mineral_inga_20l);
                break;
            case "agua_mineral_santa_maria_20l.png" :
                imageView.setImageResource(R.drawable.agua_mineral_santa_maria_20l);
                break;
            case "agua_mineral_sarandi_20l.png" :
                imageView.setImageResource(R.drawable.agua_mineral_sarandi_20l);
                break;
            case "cerveja_antartica_lata_350ml.png" :
                imageView.setImageResource(R.drawable.cerveja_antartica_lata_350ml);
                break;
            case "cerveja_brahma_lata_350ml.png" :
                imageView.setImageResource(R.drawable.cerveja_brahma_lata_350ml);
                break;
            case "cerveja_budweiser_lata_350ml.png" :
                imageView.setImageResource(R.drawable.cerveja_budweiser_lata_350ml);
                break;
            case "cerveja_devassa_lata_269ml.png" :
                imageView.setImageResource(R.drawable.cerveja_devassa_lata_269ml);
                break;
            case "cerveja_eisenbahn_lata_350ml.png" :
                imageView.setImageResource(R.drawable.cerveja_eisenbahn_lata_350ml);
                break;
            case "cerveja_heineken_lata_350ml.png" :
                imageView.setImageResource(R.drawable.cerveja_heineken_lata_350ml);
                break;
            case "cerveja_skol_lata_350ml.png" :
                imageView.setImageResource(R.drawable.cerveja_skol_lata_350ml);
                break;
            case "gelo_qualita_5kg.png" :
                imageView.setImageResource(R.drawable.gelo_qualita_5kg);
                break;
            case "emporio_conveniencia_24h.png" :
                imageView.setImageResource(R.drawable.emporio_conveniencia_24h);
                break;
            case "conveniencia_gelo_e_gela_24h.png" :
                imageView.setImageResource(R.drawable.conveniencia_gelo_e_gela_24h);
                break;
            case "super_conveniencia_horizonte.png" :
                imageView.setImageResource(R.drawable.super_conveniencia_horizonte);
                break;
            default:
                imageView.setImageResource(R.drawable.ic_missing_image_small);
                break;
        }

    }

}
