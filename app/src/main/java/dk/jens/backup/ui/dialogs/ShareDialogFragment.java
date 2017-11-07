package dk.jens.backup.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.io.File;

import dk.jens.backup.HandleShares;
import dk.jens.backup.R;

public class ShareDialogFragment extends DialogFragment
{
    public ShareDialogFragment()
    {
        super();
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Bundle arguments = getArguments();
        String label = arguments.getString("label");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(label);
        builder.setMessage(R.string.shareTitle);
        if(arguments.containsKey("apk"))
        {
            builder.setNegativeButton(R.string.radioApk, (dialog, id) -> startActivity(HandleShares.constructIntentSingle(getString(R.string.shareTitle), (File) arguments.get("apk"))));
        }
        if(arguments.containsKey("data"))
        {
            builder.setNeutralButton(R.string.radioData, (dialog, id) -> startActivity(HandleShares.constructIntentSingle(getString(R.string.shareTitle), (File) arguments.get("data"))));
        }
        if(arguments.containsKey("apk") && arguments.containsKey("data"))
        {
            builder.setPositiveButton(R.string.radioBoth, (dialog, id) -> startActivity(HandleShares.constructIntentMultiple(getString(R.string.shareTitle), (File) arguments.get("apk"), (File) arguments.get("data"))));
        }
        return builder.create();
    }
}