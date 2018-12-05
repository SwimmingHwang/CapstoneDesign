package kr.sj.obap;

import android.graphics.drawable.Drawable;
import android.widget.RatingBar;

public class FoodLikeItem {

    private Drawable image;
    private String name;
    private float rate;

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable icon) {
        this.image = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }


}
