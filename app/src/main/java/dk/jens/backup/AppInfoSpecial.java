package dk.jens.backup;

import android.os.Parcel;
import android.os.Parcelable;

public class AppInfoSpecial extends AppInfo
        implements Parcelable
{
    public static final Parcelable.Creator<AppInfoSpecial> CREATOR = new Parcelable.Creator<AppInfoSpecial>() {
        public AppInfoSpecial createFromParcel(Parcel in) {
            return new AppInfoSpecial(in);
        }

        public AppInfoSpecial[] newArray(int size) {
            return new AppInfoSpecial[size];
        }
    };
    private String[] files;

    AppInfoSpecial(String packageName, String label, String versionName, int versionCode)
    {
        super(packageName, label, versionName, versionCode, "", "", true, true);
    }

    private AppInfoSpecial(Parcel in) {
        super(in);
        files = in.createStringArray();
    }

    public String[] getFilesList()
    {
        return files;
    }

    void setFilesList(String file) {
        files = new String[]{file};
    }

    void setFilesList(String... files) {
        this.files = files;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        super.writeToParcel(out, flags);
        out.writeStringArray(files);
    }
}
