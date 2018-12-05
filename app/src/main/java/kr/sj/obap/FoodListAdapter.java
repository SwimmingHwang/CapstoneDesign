package kr.sj.obap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodListAdapter extends BaseAdapter {

    private ArrayList<FoodLikeItem> mItem =new ArrayList<>();


    @Override
    public int getCount() {
        return mItem.size();
    }

    @Override
    public Object getItem(int position) {
        return mItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_foodlikeitem, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_foodlikeimage) ;
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_foodlikename) ;

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        FoodLikeItem myItem = (FoodLikeItem)getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        iv_img.setImageDrawable(myItem.getImage());
        tv_name.setText(myItem.getName());

        holder.ratingBar.setOnRatingBarChangeListener(onRatingChangedListener(holder, position));
        holder.ratingBar.setTag(position);
        holder.ratingBar.setRating(((FoodLikeItem) getItem(position)).getRate());

        //((FoodLikeItem) getItem(myItem.getfId())).setRate(rb_rate.getRating());

        return convertView;
    }

    private RatingBar.OnRatingBarChangeListener onRatingChangedListener(final ViewHolder holder, final int position){
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                FoodLikeItem item = (FoodLikeItem)getItem(position);
                item.setRate(v);
            }
        };

    }

    private static class ViewHolder {
        //http://www.devexchanges.info/2015/11/rating-bar-in-listview-android.html
        private RatingBar ratingBar;

        public ViewHolder(View view) {
            ratingBar = (RatingBar) view.findViewById(R.id.rb_foodlikerate);
        }
    }

    public void addItem(Drawable img, String name,int id) {

        FoodLikeItem item = new FoodLikeItem();

        /* MyItem에 아이템을 setting한다. */
        item.setImage(img);
        item.setName(name);
        /* mItems에 MyItem을 추가한다. */
        mItem.add(item);

    }
}
