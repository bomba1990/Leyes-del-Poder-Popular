package com.publisnet.leyesconcejoscomunales.activity;

import android.content.Intent;
import android.net.Uri;

import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.model.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.danielstone.materialaboutlibrary.model.MaterialAboutTitleItem;
import com.publisnet.leyesconcejoscomunales.R;

/**
 * Created by mariano on 03/01/17.
 */

public class AboutActivity extends MaterialAboutActivity {
    @Override
    protected MaterialAboutList getMaterialAboutList() {
        MaterialAboutList.Builder builder = new MaterialAboutList.Builder();

        MaterialAboutCard.Builder appCardBuilder = new MaterialAboutCard.Builder();
        appCardBuilder.addItem(new MaterialAboutTitleItem.Builder()
                .text(R.string.app_name)
                .icon(R.mipmap.ic_launcher)
                .build());

        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.version)
                .subText("1.1.5")
                .icon(R.drawable.ic_menu_about)
                .build());

        builder.addCard(appCardBuilder.build());

        MaterialAboutCard.Builder authorCardBuilder = new MaterialAboutCard.Builder();
        authorCardBuilder.title(R.string.autor);

        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.desarrollador)
                .subText(R.string.desarrollador_direccion)
                .icon(R.drawable.ic_person)
                .build());
        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.correo)
                .subText(R.string.email)
                .icon(R.drawable.ic_gmail)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto",getString(R.string.email), null));
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));
                    }
                })
                .build());

        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Sigueme en twitter")
                .subText("@sosinformatico")
                .icon(R.drawable.ic_twitter)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + getString(R.string.twitter_account))));
                        }catch (Exception e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + getString(R.string.twitter_account))));
                        }
                    }
                })
                .build());





        builder.addCard(authorCardBuilder.build());
        return builder.build();
    }

    @Override
    protected CharSequence getActivityTitle() {
        return getString(R.string.acerca_de);
    }
}
