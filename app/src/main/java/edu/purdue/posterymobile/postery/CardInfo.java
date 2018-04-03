package edu.purdue.posterymobile.postery;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by kane on 10/17/15.
 */
public class CardInfo {
    public long cid;
    public String title;
    public String desc;
    public Drawable imgDrawable;
    public String imgUrl;
    public Bitmap imgBitmap;
    public int imgId;

    protected static final String TITLE_PREFIX = "Title_";
    protected static final String DESC_PREFIX = "Desc_";
    protected static final String IMGID_PREFIX = "Imgid_";
}