package edu.purdue.posterymobile.postery;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.purdue.posterymobile.postery.utility.Utility;

/**
 * Created by kane on 10/17/15.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<CardInfo> cardList;

    public CardAdapter(List<CardInfo> cardList) {
        this.cardList = cardList;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {
        CardInfo ci = cardList.get(i);
        cardViewHolder.vTitle.setText(ci.title);
        cardViewHolder.vDesc.setText(ci.desc);
//        cardViewHolder.vImage.setImageResource(ci.imgId);
//        cardViewHolder.vImage.setImageDrawable(ci.imgDrawable);
        new DownloadImageTask().execute(ci.imgUrl, cardViewHolder);
    }

    class DownloadImageTask extends AsyncTask<Object, String, Bitmap> {
        private CardViewHolder holder;
        @Override
        protected Bitmap doInBackground(Object... objs) {
            String url = (String) objs[0];
            this.holder = (CardViewHolder) objs[1];

            return Utility.LoadBitmapFromWebOperations(url);
        }

        @Override
        protected void onPostExecute(final Bitmap bitmap) {
            this.holder.vImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardview_row, viewGroup, false);

        return new CardViewHolder(itemView);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        protected ImageView vImage;
        protected TextView vTitle;
        protected TextView vDesc;

        public CardViewHolder(View v) {
            super(v);
            vImage = (ImageView) v.findViewById(R.id.card_image);
            vTitle = (TextView) v.findViewById(R.id.card_title);
            vDesc = (TextView) v.findViewById(R.id.card_desc);
        }
    }
}

