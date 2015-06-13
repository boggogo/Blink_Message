package koemdzhiev.com.blinkmessage.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

import koemdzhiev.com.blinkmessage.R;
import koemdzhiev.com.blinkmessage.utils.ParseConstants;

/**
 * Created by koemdzhiev on 03/06/2015.
 */
public class MessageAdapter extends ArrayAdapter<ParseObject> {
    protected Context mContext;
    protected List<ParseObject> mMessages;

    public MessageAdapter(Context context, List<ParseObject> messages) {
        super(context, R.layout.message_item,messages);
        mContext = context;
        mMessages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.messageIcon);
            holder.nameLabel = (TextView) convertView.findViewById(R.id.senderLabel);
            holder.createdAtLabel = (TextView) convertView.findViewById(R.id.createdAtLabel);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject message = mMessages.get(position);
        Date createdAt = message.getCreatedAt();

        Long now = new Date().getTime();
        String convertedData = DateUtils.getRelativeTimeSpanString(
                createdAt.getTime(),
                now,
                DateUtils.SECOND_IN_MILLIS).toString();

        if(message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)){
            holder.iconImageView.setImageResource(R.mipmap.ic_picture);
        }else{
            holder.iconImageView.setImageResource(R.mipmap.ic_video);
        }
        holder.createdAtLabel.setText(convertedData);
        //Log.i(MessageAdapter.class.getSimpleName(),message.getCreatedAt().toString());
        holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));

       return convertView;
    }

    private static class ViewHolder{
        ImageView iconImageView;
        TextView nameLabel;
        TextView createdAtLabel;
    }
    //refill, this method prevents the adapter to go to the top of the list
    public void refill(List<ParseObject> messages){
        messages.clear();
        messages.addAll(messages);
        notifyDataSetChanged();
    }
}
