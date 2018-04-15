package com.bullseyedevs.tableview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;

import com.bullseyedevs.tableview.R;
import com.bullseyedevs.tableview.model.Club;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ClubViewHolder> implements Filterable
{
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private List<Club> clubList;
    private List<Club> filteredClubList;
    private Context context;

    public ClubAdapter(Context context, List<Club> clubList)
    {
        this.context = context;
        this.clubList = clubList;
        this.filteredClubList = clubList;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position % 2 == 0)
        {
            return TYPE_ROW_COLORFUL;
        }

        return TYPE_ROW;
    }

    @Override
    public ClubViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        if (viewType == TYPE_ROW)
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_club, viewGroup, false);
            return new ClubViewHolder(view);
        } else
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_club_colorful,
                    viewGroup, false);
            return new ClubViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ClubViewHolder holder, int position)
    {
        Club club = filteredClubList.get(position);

        holder.txtName.setText(club.name);
        holder.txtLocation.setText(club.location);
        holder.txtStadiumName.setText(club.stadiumName);
        holder.txtLeagueName.setText(club.leagueName);
        holder.txtCoachName.setText(club.coachName);
        holder.txtStarPlayerName.setText(club.starPlayerName);

        Glide.with(context).load(club.logoUrl).into(holder.imgLogo);
    }

    @Override
    public int getItemCount()
    {
        return filteredClubList.size();
    }

    public class ClubViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtName, txtLocation, txtStadiumName, txtLeagueName, txtCoachName, txtStarPlayerName;
        public ImageView imgLogo;

        public ClubViewHolder(View view)
        {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            txtLocation = view.findViewById(R.id.txtLocation);
            txtStadiumName = view.findViewById(R.id.txtStadiumName);
            txtLeagueName = view.findViewById(R.id.txtLeagueName);
            txtCoachName = view.findViewById(R.id.txtCoachName);
            txtStarPlayerName = view.findViewById(R.id.txtStarPlayerName);

            imgLogo = view.findViewById(R.id.imgLogo);
        }
    }

    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                String charString = charSequence.toString();
                if (charString.isEmpty())
                {
                    filteredClubList = clubList;
                } else
                {
                    List<Club> filteredList = new ArrayList<>();
                    for (Club club : clubList)
                    {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name
                        if (club.name.toLowerCase().contains(charString.toLowerCase()) )
                        {
                            filteredList.add(club);
                        }
                    }

                    filteredClubList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredClubList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                filteredClubList = (ArrayList<Club>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}