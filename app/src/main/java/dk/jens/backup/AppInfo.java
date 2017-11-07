package dk.jens.backup;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class AppInfo
        implements Comparable<AppInfo>, Parcelable
{
    public static final int MODE_APK = 1;
    public static final int MODE_DATA = 2;
    public static final int MODE_BOTH = 3;
    public static final Parcelable.Creator<AppInfo> CREATOR = new Parcelable.Creator<AppInfo>() {
        public AppInfo createFromParcel(Parcel in) {
            return new AppInfo(in);
        }

        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };
    static final int MODE_UNSET = 0;
    public Bitmap icon;
    private LogFile logInfo;
    private String label, packageName, versionName, sourceDir, dataDir;
    private int versionCode, backupMode;
    private boolean system, installed, checked, disabled;
    public AppInfo(String packageName, String label, String versionName, int versionCode, String sourceDir, String dataDir, boolean system, boolean installed)
    {
        this.label = label;
        this.packageName = packageName;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.sourceDir = sourceDir;
        this.dataDir = dataDir;
        this.system = system;
        this.installed = installed;
        this.backupMode = MODE_UNSET;
    }

    protected AppInfo(Parcel in) {
        logInfo = in.readParcelable(getClass().getClassLoader());
        label = in.readString();
        packageName = in.readString();
        versionName = in.readString();
        sourceDir = in.readString();
        dataDir = in.readString();
        versionCode = in.readInt();
        backupMode = in.readInt();
        boolean[] bools = new boolean[4];
        in.readBooleanArray(bools);
        system = bools[0];
        installed = bools[1];
        checked = bools[2];
        icon = in.readParcelable(getClass().getClassLoader());
    }

    public String getPackageName()
    {
        return packageName;
    }

    public String getLabel()
    {
        return label;
    }

    public String getVersionName()
    {
        return versionName;
    }

    public int getVersionCode()
    {
        return versionCode;
    }

    public String getSourceDir()
    {
        return sourceDir;
    }

    public String getDataDir()
    {
        return dataDir;
    }

    public int getBackupMode()
    {
        return backupMode;
    }

    void setBackupMode(int modeToAdd)
    {
        // add only if both values are different and neither is MODE_BOTH
        if(backupMode == MODE_BOTH || modeToAdd == MODE_BOTH)
            backupMode = MODE_BOTH;
        else if(modeToAdd != backupMode)
            backupMode += modeToAdd;
    }

    public LogFile getLogInfo() {
        return logInfo;
    }

    void setLogInfo(LogFile newLogInfo) {
        logInfo = newLogInfo;
        backupMode = logInfo.getBackupMode();
    }

    public boolean isChecked()
    {
        return checked;
    }

    void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    void setDisabled()
    {
        this.disabled = true;
    }

    public boolean isDisabled()
    {
        return disabled;
    }

    public boolean isSystem()
    {
        return system;
    }

    public boolean isInstalled()
    {
        return installed;
    }

    // list of single files used by special backups - only for compatibility now
    public String[] getFilesList()
    {
        return null;
    }

    // should ideally be removed once proper polymorphism is implemented
    public boolean isSpecial()
    {
        return false;
    }

    public int compareTo(@NonNull AppInfo appInfo)
    {
        return label.compareToIgnoreCase(appInfo.getLabel());
    }

    public String toString()
    {
        return label + " : " + packageName;
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeParcelable(logInfo, flags);
        out.writeString(label);
        out.writeString(packageName);
        out.writeString(versionName);
        out.writeString(sourceDir);
        out.writeString(dataDir);
        out.writeInt(versionCode);
        out.writeInt(backupMode);
        out.writeBooleanArray(new boolean[] {system, installed, checked});
        out.writeParcelable(icon, flags);
    }
}
