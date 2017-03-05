package nyc.c4q.ashiquechowdhury.auxx;

import android.widget.TextView;

public class AlterToolBar {

    String toolbarName;
    TextView defaultTextView;

    public AlterToolBar(String toolbarName, TextView defaultTextView) {
        this.toolbarName = toolbarName;
        this.defaultTextView = defaultTextView;
    }


    public void changeToolBarName(){
        defaultTextView.setText(toolbarName);
    }




}
