
        package com.mobilecommerce.notepadapplication;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;



public class AdapterForNote extends ArrayAdapter<Note> {

    public AdapterForNote(Context context, ArrayList<Note> notes){

        super(context, 0, notes);
    }

    public static class ViewHolder {
        static TextView title;
        static TextView note;
        static ImageView noteIcon;
        static ImageButton colorIcon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Get the data item for this position
        Note note = getItem(position);

        //creating new viewHolder
        ViewHolder viewHolder;

        //Check if an existing view is being used, otherwise inflate a new view from custom row layout
        if(convertView == null) {

            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);

            viewHolder.title = (TextView) convertView.findViewById(R.id.noteTitle);
            viewHolder.note = (TextView) convertView.findViewById(R.id.noteBody);
            viewHolder.noteIcon = (ImageView) convertView.findViewById(R.id.noteImage);
            viewHolder.colorIcon = (ImageButton)convertView.findViewById(R.id.colorPicker);

            convertView.setTag(viewHolder);
        }

        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //Fill each new referenced view with data associated with the note it's referencing
        ViewHolder.title.setText(note.getTitle());
        ViewHolder.noteIcon.setImageResource(note.getAssociatedDrawbleCategory());
        ViewHolder.note.setText(note.getDescription());
        ViewHolder.colorIcon.setBackgroundColor(note.getAssociatedDrawbleColorCategory());

        //we will have a return item here as getView is not void
        return convertView;
    }


}