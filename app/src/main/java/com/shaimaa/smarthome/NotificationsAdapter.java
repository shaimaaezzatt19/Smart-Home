package com.shaimaa.smarthome;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationHolder> {

    private Context context;
    private List<NotificationModel> notificationsList;

    public NotificationsAdapter(Context context, List<NotificationModel> notificationsList) {
        this.context = context;
        this.notificationsList = notificationsList;
    }

    @Override
    public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotificationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationHolder holder, int position) {
        NotificationModel notification = notificationsList.get(position);

        holder.notificationTV.setText(notification.temperature + "°C");
        holder.dateTV.setText(notification.date);

        int temperature = Integer.parseInt(notification.temperature);

    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    protected class NotificationHolder extends RecyclerView.ViewHolder {

        ImageView notificationIV;
        TextView notificationTV, dateTV;

        public NotificationHolder(View itemView) {
            super(itemView);
            notificationIV = (ImageView) itemView.findViewById(R.id.notification_item_image);
            notificationTV = (TextView) itemView.findViewById(R.id.notification_item_text);
            dateTV = (TextView) itemView.findViewById(R.id.notification_item_date);
        }
    }
}
