package com.example.android.mynewsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Adapters provide a binding from an app-specific data set to views that are displayed
 * within a {@link RecyclerView}.</p>
 */

public class TechNewsAdapter extends RecyclerView.Adapter<TechNewsAdapter.ViewHolder> {
    public ArrayList<News> mLatestNewsArray = new ArrayList<>();
    private final TechNewsFragment.OnListFragmentInteractionListener mListener;
    static Context mContext;

    /**
     * This is a public constructor used to create an object of the  adapter class
     * {@link TechNewsAdapter}
     */
    public TechNewsAdapter(ArrayList<News> latestNewsArray,
                           TechNewsFragment.OnListFragmentInteractionListener listener, Context context) {
        mLatestNewsArray = latestNewsArray;
        mListener = listener;
        mContext = context;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("onCreateHolder", " Tech");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tech_news_list, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link android.widget.ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.     *
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * should update the contents of the {@link ViewHolder#itemView} to reflect the item at
     * the given position. this differs from the previous in that it can perform partial bind.
     * <p>
     * Partial bind vs full bind:
     * <p>
     * The payloads parameter is a merge list from {@link #notifyItemChanged(int, Object)} or
     * {@link #notifyItemRangeChanged(int, int, Object)}.  If the payloads list is not empty,
     * the ViewHolder is currently bound to old data and Adapter may run an efficient partial
     * update using the payload info.  If the payload is empty,  Adapter must run a full bind.
     * Adapter should not assume that the payload passed in notify methods will be received by
     * onBindViewHolder().  For example when the view is not attached to the screen, the
     * payload in notifyItemChange() will be simply dropped.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     * @param payloads A non-null list of merged payloads. Can be empty list if requires full
     *                 update.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.urlTitle.setText(mLatestNewsArray.get(position).getWebTitle());
        holder.sectionNameView.setText(mLatestNewsArray.get(position).getSectionName());
        holder.publicationDate.setText(mLatestNewsArray.get(position).getmWebPublicationDate());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    /* Notify the active callbacks interface (the activity, if the
                     *fragment is attached to one) that an item has been selected.
                     */
                    mListener.onListFragmentInteraction(holder.mItem);
                    String url = mLatestNewsArray.get(position).getWebUrl();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    mContext.startActivity(i);
                }
            }
        });
         /*Alternate the color of each item in the list
         *
         * {@link .setBackgroundColor} Sets the background color for this view.
         * @param color the color of the background
         * {@link color.rgb } represents a color with integer values for red, green and blue
         */
        Color color = new Color();
        if (position % 2 == 0) {
            holder.techLayout.setBackgroundColor(color.rgb(251, 233, 231));
        } else {
            holder.techLayout.setBackgroundColor(color.rgb(255, 235, 238));
        }
        if (position == 1) {
            holder.sectionNameView.setBackgroundColor(color.rgb(00, 137, 123));
        } else if (position % 2 == 0 & position % 8 != 0) {
            if (position % 4 != 0 & position % 2 == 0) {
                holder.sectionNameView.setBackgroundColor(color.rgb(245, 00, 87));
            } else
                holder.sectionNameView.setBackgroundColor(color.rgb(48, 79, 254));
        } else if (position % 3 == 0) {
            holder.sectionNameView.setBackgroundColor(color.rgb(142, 36, 170));
        } else if (position % 5 == 0) {
            holder.sectionNameView.setBackgroundColor(color.rgb(38, 108, 124));
        } else if (position % 7 == 0) {
            holder.sectionNameView.setBackgroundColor(color.rgb(76, 175, 80));
        } else if (position % 9 == 0) {
            holder.sectionNameView.setBackgroundColor(color.rgb(178, 255, 89));
        } else
            holder.sectionNameView.setBackgroundColor(color.rgb(255, 87, 34));
    }

    @Override
    public int getItemCount() {
        return mLatestNewsArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView urlTitle;
        private final Button sectionNameView;
        private final TextView publicationDate;
        private final LinearLayout techLayout;
        private News mItem;
        private View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            urlTitle = (TextView) view.findViewById(R.id.tech_heading);
            sectionNameView = (Button) view.findViewById(R.id.tech_section_name);
            techLayout = (LinearLayout) view.findViewById(R.id.tech_layout);
            publicationDate = (TextView) view.findViewById(R.id.tech_publication_date);
        }
    }
}