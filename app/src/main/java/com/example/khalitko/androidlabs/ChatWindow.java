package com.example.khalitko.androidlabs;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
//    private ListView listView;
    private static ArrayList<String> chatMessages;
    private ChatAdapter messageAdapter;
    private static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        Log.i(ACTIVITY_NAME, "In onCreate");
        chatMessages = new ArrayList<>();
        messageAdapter = new ChatAdapter(this);
        ListView listView = findViewById(R.id.chatView);
        listView.setAdapter(messageAdapter);

        ChatDatabaseHelper helper = new ChatDatabaseHelper(this);
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ChatDatabaseHelper.TABLE_NAME,
                new String[]{});
        cursor.moveToFirst();

        int messageIndex = cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
        while (!cursor.isAfterLast()) {
            String string = cursor.getString(messageIndex);
            chatMessages.add(string);
            Log.i(ACTIVITY_NAME, "Sql Message:" + string);
            cursor.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "Cursor Column Count: " + cursor.getColumnCount());
        Log.i(ACTIVITY_NAME, "Column names:" + Arrays.toString(cursor.getColumnNames()));
        cursor.close();
    }


    protected void onSendClick(View view){
        Log.i(ACTIVITY_NAME, "Send was pressed");
        EditText editText = findViewById(R.id.editText);
        chatMessages.add(editText.getText().toString());
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChatDatabaseHelper.KEY_MESSAGE, editText.getText().toString());
        db.insert(ChatDatabaseHelper.TABLE_NAME, null, contentValues);
        messageAdapter.notifyDataSetChanged();
        editText.setText("");

    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context context){

            super(context,0);
        }


        public int getCount() {

            return chatMessages.size();
        }


        public String getItem(int position){
            Log.i(ACTIVITY_NAME, "In getItem");
            return chatMessages.get(position);
        }

        @Override
        public long getItemId(int position){

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;
            if (position % 2 == 0){
                System.out.println("Here" + position);
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                System.out.println("There" + position);
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}