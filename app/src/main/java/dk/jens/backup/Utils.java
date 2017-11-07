package dk.jens.backup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.widget.Toast;

import java.io.File;

import dk.jens.backup.ui.HandleMessages;

public class Utils
{
    static void showErrors(final Activity activity)
    {
        activity.runOnUiThread(() -> {
            String errors = ShellCommands.getErrors();
            if (errors.length() > 0) {
                new AlertDialog.Builder(activity)
                        .setTitle(R.string.errorDialogTitle)
                        .setMessage(errors)
                        .setPositiveButton(R.string.dialogOK, null)
                        .show();
                ShellCommands.clearErrors();
            }
        });
    }

    static File createBackupDir(final Activity activity, final String path)
    {
        FileCreationHelper fileCreator = new FileCreationHelper();
        File backupDir;
        if(path.trim().length() > 0)
        {
            backupDir = fileCreator.createBackupFolder(path);
            if(fileCreator.isFallenBack())
            {
                activity.runOnUiThread(() -> Toast.makeText(activity, activity.getString(R.string.mkfileError) + " " + path + " - " + activity.getString(R.string.fallbackToDefault) + ": " + FileCreationHelper.getDefaultBackupDirPath(), Toast.LENGTH_LONG).show());
            }
        }
        else
        {
            backupDir = fileCreator.createBackupFolder(FileCreationHelper.getDefaultBackupDirPath());
        }
        if(backupDir == null)
        {
            showWarning(activity, activity.getString(R.string.mkfileError) + " " + FileCreationHelper.getDefaultBackupDirPath(), activity.getString(R.string.backupFolderError));
        }
        return backupDir;
    }

    static void showWarning(final Activity activity, final String title, final String message)
    {
        activity.runOnUiThread(() -> new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(R.string.dialogOK, (dialog, id) -> {
                })
                .setCancelable(false)
                .show());
    }

    public static void showConfirmDialog(Activity activity, String message, final Command confirmCommand)
    {
        new AlertDialog.Builder(activity)
                .setTitle("")
                .setMessage(message)
                .setPositiveButton(R.string.dialogOK, (dialog, id) -> confirmCommand.execute())
                .setNegativeButton(R.string.dialogCancel, (dialog, id) -> {
                })
                .show();
    }

    static void reloadWithParentStack(Activity activity)
    {
        Intent intent = activity.getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.finish();
        activity.overridePendingTransition(0, 0);
        TaskStackBuilder.create(activity)
                .addNextIntentWithParentStack(intent)
                .startActivities();
    }

    static void reShowMessage(HandleMessages handleMessages, long tid)
    {
        // since messages are progressdialogs and not dialogfragments they need to be set again manually
        if(tid != -1)
            for(Thread t : Thread.getAllStackTraces().keySet())
                if(t.getId() == tid && t.isAlive())
                    handleMessages.reShowMessage();
    }

    static String getName(String path)
    {
        if(path.endsWith(File.separator))
            path = path.substring(0, path.length() - 1);
        return path.substring(path.lastIndexOf(File.separator) + 1);
    }
    public interface Command
    {
        void execute();
    }
}
