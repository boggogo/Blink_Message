package koemdzhiev.com.blinkmessage.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import koemdzhiev.com.blinkmessage.adapters.MessageAdapter;
import koemdzhiev.com.blinkmessage.utils.ParseConstants;
import koemdzhiev.com.blinkmessage.R;

/**
 * Created by koemdzhiev on 27/05/2015.
 */
public class InboxFragment extends ListFragment {
    protected List<ParseObject> mMessages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setProgressBarIndeterminateVisibility(true);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_MESSAGES);
        query.whereEqualTo(ParseConstants.KEY_RECIPIENT_IDS, ParseUser.getCurrentUser().getObjectId());
        query.addAscendingOrder(ParseConstants.KEY_CREATED_AT);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> messages, ParseException e) {
                getActivity().setProgressBarIndeterminateVisibility(false);
                if(e == null){
                    //success, we found messages
                    mMessages = messages;

                    String[] friendNames = new String[mMessages.size()];

                    int i = 0;
                    for (ParseObject message : mMessages) {
                        friendNames[i] = message.getString(ParseConstants.KEY_SENDER_NAME);
                        i++;
                    }
                    if(getListView().getAdapter() == null) {
                        MessageAdapter adapter = new MessageAdapter(getListView().getContext(), mMessages);
                        setListAdapter(adapter);
                    }else{
                        //refill, this method prevents the adapter to go to the top of the list
                        ((MessageAdapter)(getListView().getAdapter())).refill(mMessages);
                    }
                }
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

       ParseObject message =  mMessages.get(position);
        String messageType = message.getString(ParseConstants.KEY_FILE_TYPE);
        ParseFile file = message.getParseFile(ParseConstants.KEY_FILE);
        Uri fileUri = Uri.parse(file.getUrl());

        if(messageType.equals(ParseConstants.TYPE_IMAGE)){
            //view the image
            Intent intent = new Intent(getActivity(),ViewImageActivity.class);
            intent.setData(fileUri);
            startActivity(intent);
        }else{
            //view video
            Intent intent = new Intent(Intent.ACTION_VIEW,fileUri);
            intent.setDataAndType(fileUri,"video/*");
            startActivity(intent);
        }

        //delete the message

        List<String> ids = message.getList(ParseConstants.KEY_RECIPIENT_IDS);
        Toast.makeText(getListView().getContext(),ids.size()+"",Toast.LENGTH_LONG).show();
        if(ids.size() == 1){
            //last recipient - delete message
            message.deleteEventually();
        }else{
            //remove recipient and save
            ids.remove(ParseUser.getCurrentUser().getObjectId());

            ArrayList<String> idsToRemove = new ArrayList<>();
            idsToRemove.add(ParseUser.getCurrentUser().getObjectId());
            message.removeAll(ParseConstants.KEY_RECIPIENT_IDS,idsToRemove);
            message.saveInBackground();

        }
    }
}
