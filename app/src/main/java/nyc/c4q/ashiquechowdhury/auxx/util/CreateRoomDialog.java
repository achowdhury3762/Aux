package nyc.c4q.ashiquechowdhury.auxx.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import nyc.c4q.ashiquechowdhury.auxx.R;

public class CreateRoomDialog extends DialogFragment {
    private EditText roomNameEditText;
    private SubmitRoomNameListener roomNameSubmitListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_room_name_password, null);
        roomNameEditText = (EditText) view.findViewById(R.id.room_name);
        builder.setView(view)
                .setPositiveButton(R.string.create_room_prompt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String input = String.valueOf(roomNameEditText.getText());
                        roomNameSubmitListener.onSubmitRoomName(input);
                        roomNameEditText.getText();

                    }
                });
        return builder.create();
    }

    public void setSubmitListener(SubmitRoomNameListener submitListener) {
        this.roomNameSubmitListener = submitListener;
    }

    public interface SubmitRoomNameListener {
        void onSubmitRoomName(String roomNameInput);
    }
}
